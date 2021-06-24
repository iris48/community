package com.iris.community;

import com.iris.community.entity.DiscussPost;
import com.iris.community.service.DiscussPostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class CaffeineTests {
    @Autowired
    private DiscussPostService discussPostService;
    @Test
    public void initDataForTest(){
        for (int i = 0; i < 180000; i++) {
            DiscussPost post = new DiscussPost();
            post.setUserId(333);
            post.setTitle("时代在召唤");
            post.setContent("这是最好的时代，也是最坏的时代");
            post.setCreateTime(new Date());
            post.setScore(Math.random() * 2000);
            discussPostService.addDiscussPost(post);

        }
    }
    @Test
    public void testCache(){
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,1));
        System.out.println(discussPostService.findDiscussPosts(0,0,10,0));

    }
}
