package com.iris.community.dao;

import com.iris.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

@Mapper
@Deprecated
public interface LoginTicketMapper {
    //劣势 sql复杂的时候看起来并不舒适

    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertLoginTicket(LoginTicket loginTicket);


    //查询的时候要依据ticket来查询 客户端用cookie存储 发给服务器 服务器就知道是那个用户在登陆
    @Select({
            "select id, user_id, ticket, status, expired ",
            "from login_ticket where ticket = #{ticket}"
    })
    LoginTicket selectByTicket(String ticket);

    //更改状态
    @Update({
            "update login_ticket set status=#{status} ",
            "where ticket=#{ticket}"
    })
    int updateStatus(String ticket, int status);



}
