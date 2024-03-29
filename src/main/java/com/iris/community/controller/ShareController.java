package com.iris.community.controller;

import com.iris.community.entity.Event;
import com.iris.community.event.EventProducer;
import com.iris.community.util.CommunityConstant;
import com.iris.community.util.CommunityUtil;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ShareController implements CommunityConstant {
    private static final Logger logger = LoggerFactory.getLogger(ShareController.class);

    //分享事件 异步的方式
    @Autowired
    private EventProducer eventProducer;



    @Value("${community.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${wk.image.storage}")
    private String wkImageStorage;



    @RequestMapping(path = "/share", method = RequestMethod.GET)
    @ResponseBody
    public String share(String htmlUrl){
        //生成文件名
        String fileName = CommunityUtil.generateUUID();

        //异步生成长图
        Event event = new Event()
                .setTopic(TOPIC_SHARE)
                .setData("htmlUrl", htmlUrl)
                .setData("fileName", fileName)
                .setData("suffix", ".png");
        eventProducer.fireEvent(event);

        Map<String, Object> map = new HashMap<>();
        map.put("shareUrl", domain + contextPath + "/share/image/" + fileName);

        //返回访问路径
        return CommunityUtil.getJSONString(0, null, map);

    }
    //获取长图
    @RequestMapping(path = "/share/image/{fileName}", method = RequestMethod.GET)
    public void getShareImage(@PathVariable("fileName") String fileName, HttpServletResponse response){

        if(StringUtils.isBlank(fileName))
            throw new IllegalArgumentException("文件名不能为空");
        response.setContentType("image/png");
        File file = new File(wkImageStorage + "/" + fileName + ".png");
        try {
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int b = 0;
            while((b = fileInputStream.read(buffer)) != -1){
                outputStream.write(buffer, 0 , b);

            }
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("获取长图失败：" + e.getMessage());

        }
    }





}
