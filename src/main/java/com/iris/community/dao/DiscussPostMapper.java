package com.iris.community.dao;

import com.iris.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    //个人主页的查询所有帖子 为了分页 offset是起始的，limit是每页多少个
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    //查询帖子条数 @Param用于给帖子起别名 动态拼条件 且方法只有一个条件 参数需要取别名
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost(DiscussPost discussPost);

    DiscussPost selectByDiscussPostId(int id);

    int updateCommentCount(int id, int commentCount);



}
