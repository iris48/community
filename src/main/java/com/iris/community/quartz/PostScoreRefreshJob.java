package com.iris.community.quartz;

import com.iris.community.entity.DiscussPost;
import com.iris.community.service.CommentService;
import com.iris.community.service.DiscussPostService;
import com.iris.community.service.ElasticSearchService;
import com.iris.community.service.LikeService;
import com.iris.community.util.CommunityConstant;
import com.iris.community.util.RedisKeyUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundSetOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostScoreRefreshJob implements Job, CommunityConstant{

    private static final Logger logger = LoggerFactory.getLogger(PostScoreRefreshJob.class);
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    @Autowired
    private RedisTemplate redisTemplate;

    //牛客纪元
    private static final Date epoch;

    static {
        try {
            epoch = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                    .parse("2014-08-01 00:00:00");
        } catch (ParseException e) {
            //此处抛出异常而不是打印堆栈信息
            throw new RuntimeException("初始化牛客纪元失败!", e);
        }
    }

//    static {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        try {
//            epoch = sdf.parse("2014-08-01 00:00:00");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }finally{
//            epoch = null;
//        }
//    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String redisKey = RedisKeyUtil.getPostScoreKey();
        BoundSetOperations operations = redisTemplate.boundSetOps(redisKey);
        if(operations.size() == 0){
            logger.info("【任务取消】没有需要刷新的帖子");
            return;
        }
        logger.info("【任务开始】正在刷新帖子分数");
        while(operations.size() > 0){
            this.refresh((Integer)operations.pop());
        }
        logger.info("【任务结束】帖子分数刷新完毕");

    }
    public void refresh(int postId){
        DiscussPost post = discussPostService.findDiscussPostById(postId);
        if(post == null){
            logger.error("该帖子不存在： id =" + postId);
            return;
        }
        //是否加精
        boolean wonderful = post.getStatus() == 1;

        //评论数据
        int commentCount = post.getCommentCount();

        //点赞数量
        long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, postId);

        //计算权重
        double w = (wonderful ? 75 : 0) + commentCount * 10 + likeCount * 2;
        //分数=帖子权重+距离天数
        double score = Math.log10(Math.max(w,1))
                + (post.getCreateTime().getTime() - epoch.getTime())/(1000 * 3600 * 24);

        //更新帖子分数
        discussPostService.updateScore(postId, score);
        post.setScore(score);
        //同步搜索数据
        elasticSearchService.save(post);


    }
}
