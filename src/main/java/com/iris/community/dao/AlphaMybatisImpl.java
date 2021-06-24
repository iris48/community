package com.iris.community.dao;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class AlphaMybatisImpl implements AlphaDao {
    @Override
    public String select() {
        return "mybatis";
    }
}
