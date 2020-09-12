package com.iris.community.controller;

import com.iris.community.service.DiscussPostService;
import com.iris.community.service.LikeService;
import com.iris.community.service.UserService;
import com.iris.community.entity.DiscussPost;
import com.iris.community.entity.Page;
import com.iris.community.entity.User;
import com.iris.community.util.CommunityConstant;
import com.iris.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.*;

@Controller
public class HomeController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    @RequestMapping(path="/index",method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){

        //方法调用之前 SpringMVC自动实例化Model Page 还会将Page注入给Model DS自动把配置装到model里面
        //在thymeleaf中可以直接访问Page对象中的数据。

        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");


        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(list != null){

            for(DiscussPost post : list){
                Map<String,Object> map = new HashMap();
                map.put("post",post);
                User user =userService.findUserById(post.getUserId());
                map.put("user",user);
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST,post.getId());
                map.put("likeCount",likeCount);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);

        return "/index";
    }

    @RequestMapping(path="/indexs",method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String,Object>>  getIndexPages(Model model, Page page){

        //方法调用之前 SpringMVC自动实例化Model Page 还会将Page注入给Model DS自动把配置装到model里面
        //在thymeleaf中可以直接访问Page对象中的数据。

        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/index");


        List<DiscussPost> list = discussPostService.findDiscussPosts(0,page.getOffset(),page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if(list != null){

            for(DiscussPost post : list){
                Map<String,Object> map = new HashMap();
                map.put("post",post);
                User user =userService.findUserById(post.getUserId());
                map.put("user",user);
                discussPosts.add(map);
            }

        }
        //model.addAttribute("discussPosts",discussPosts);
        Map<String,Object> map = new HashMap();
        map.put("page",page);
        discussPosts.add(map);
        return discussPosts;
    }
    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public String getErrorPage() {
        return "/error/500";
    }



}
