package com.iris.community.actuator;

import com.iris.community.util.CommunityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@Endpoint(id = "database")
public class DatabaseEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseEndpoint.class);

    //尝试获取连接 一种是通过连接池，另外一种是通过manager
    @Autowired
    private DataSource dataSource;

    @ReadOperation//通过get请求访问
    public String checkConnection(){
        try (
                Connection conn = dataSource.getConnection();
                ){
            return CommunityUtil.getJSONString(0,"获取连接成功");
        } catch (SQLException e) {
            return CommunityUtil.getJSONString(1,"获取连接失败");

        }

    }
}
