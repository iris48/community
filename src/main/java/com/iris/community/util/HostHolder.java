package com.iris.community.util;

import com.iris.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * 持有用户信息，代替session对象
 */
@Component
public class HostHolder {
    //以线程为key存取值
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
