package com.iris.community;

import com.iris.community.dao.DiscussPostMapper;
import com.iris.community.dao.elasticsearch.DiscussPostRepository;
import com.iris.community.entity.DiscussPost;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class ElasticsearchTests {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

//    @Autowired
//    public ElasticsearchOperations elasticsearchOperations;

    @Test
    public void testInsert(){
        discussPostRepository.save(discussPostMapper.selectByDiscussPostId(33));
        discussPostRepository.save(discussPostMapper.selectByDiscussPostId(34));
        discussPostRepository.save(discussPostMapper.selectByDiscussPostId(35));


    }
    @Test
    public void testInsertList(){
        //discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(105,0,5));
        discussPostRepository.saveAll(discussPostMapper.selectDiscussPosts(0,0,10,0));
    }

    @Test
    public void testUpdate(){
        DiscussPost post = discussPostMapper.selectByDiscussPostId(33);
        post.setContent("all in all you are so smart that the world shines for you ya");
        discussPostRepository.save(post);
    }
    @Test
    public void testDelete(){
        //discussPostRepository.deleteById(43);
        //删除所有数据
        discussPostRepository.deleteAll();
    }
    @Test
    public void testSearchByRepository(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("offer","title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();


//        在之后的版本中discussPostRepository.search方法被废弃，要用如下的方法来实现，
//        discussPostRepository.search方法底层获取得到了高亮显示的值 但是没有返回
//        SearchHits<DiscussPost> res =  elasticsearchOperations.search(searchQuery, DiscussPost.class);
        Page<DiscussPost> page = discussPostRepository.search(searchQuery);

        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        //返回当前的页号
        System.out.println(page.getNumber());
        System.out.println(page.getSize());

        for(DiscussPost post : page){
            System.out.println(post);
        }


    }
    @Test
    public void testSearchByTemplate(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("offer","title", "content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0, 5))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        Page<DiscussPost> page = elasticsearchTemplate.queryForPage(searchQuery, DiscussPost.class, new SearchResultMapper(){

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                SearchHits hits = searchResponse.getHits();
                if(hits.getTotalHits() <= 0){
                    return null;
                }
                List<DiscussPost> list = new ArrayList<>();
                for(SearchHit hit : hits){
                    DiscussPost post = new DiscussPost();
                    String id = hit.getSourceAsMap().get("id").toString();
                    post.setId(Integer.valueOf(id));
                    String userId = hit.getSourceAsMap().get("userId").toString();
                    post.setUserId(Integer.valueOf(userId));
                    String title = hit.getSourceAsMap().get("title").toString();
                    post.setTitle(title);
                    String content = hit.getSourceAsMap().get("content").toString();
                    post.setContent(content);
                    String status = hit.getSourceAsMap().get("status").toString();
                    post.setStatus(Integer.valueOf(status));
                    String createTime = hit.getSourceAsMap().get("createTime").toString();
                    post.setCreateTime(new Date(Long.valueOf(createTime)));
                    String commentCount = hit.getSourceAsMap().get("commentCount").toString();
                    post.setCommentCount(Integer.valueOf(commentCount));

                    //处理高亮显示的内容
                    HighlightField titleField = hit.getHighlightFields().get("title");
                    if(titleField != null){
                        //只显示了一处高亮，这个设计是不是不够完善？
                        post.setTitle(titleField.getFragments()[0].toString());
                    }

                    HighlightField contentField = hit.getHighlightFields().get("content");
                    if(titleField != null){
                        post.setContent(contentField.getFragments()[0].toString());
                    }
                    list.add(post);

                }
                return new AggregatedPageImpl(list, pageable,
                        hits.getTotalHits(), searchResponse.getAggregations(),
                        searchResponse.getScrollId(), hits.getMaxScore());
            }
        });

        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());
        System.out.println(page.getNumber());
        System.out.println(page.getSize());

        for(DiscussPost post : page){
            System.out.println(post);
        }

    }


}
