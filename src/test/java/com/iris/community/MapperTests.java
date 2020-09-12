package com.iris.community;

import com.iris.community.dao.DiscussPostMapper;
import com.iris.community.dao.LoginTicketMapper;
import com.iris.community.dao.UserMapper;
import com.iris.community.entity.DiscussPost;
import com.iris.community.entity.LoginTicket;
import com.iris.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    public void testSelectUser(){
        User user = userMapper.selectById(101);
        System.out.println(user);

        user = userMapper.selectByName("李三1号");
        System.out.println(user);

        user = userMapper.selectByEmail("test1@qq.com");
        System.out.println(user);
    }

    @Test
    public void testInsertUser(){
        User user = new User();
        user.setUsername("李三1号");
        user.setPassword("5067890");
        user.setSalt("890");
        user.setEmail("test1@qq.com");

        int rows = userMapper.insertUser(user);
        System.out.println(rows);
        System.out.println(user.getId());

    }
    @Test
    public void testUpdateUser(){
        int rows = userMapper.updateStatus(101,1);
        System.out.println(rows);


        rows = userMapper.updateHeader(101,"http://www.baidu.com");
        System.out.println(rows);

        rows = userMapper.updatePassWord(101,"778899");
        System.out.println(rows);

    }
    @Test
    public void testSelectPosts(){
        List<DiscussPost> list = discussPostMapper.selectDiscussPosts(101,0,10);
        for(DiscussPost post : list){
            System.out.println(post);
        }
        int rows = discussPostMapper.selectDiscussPostRows(0);
        System.out.println(rows);
    }

    @Test
    public void testInsertLoginTicket(){
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(101);
        loginTicket.setTicket("abc");
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    public void testSelectLoginTicket(){

        LoginTicket loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

        loginTicketMapper.updateStatus("abc",1);
        loginTicket = loginTicketMapper.selectByTicket("abc");
        System.out.println(loginTicket);

    }
}
