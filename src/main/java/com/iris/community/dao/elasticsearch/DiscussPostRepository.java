package com.iris.community.dao.elasticsearch;

import com.iris.community.entity.DiscussPost;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository//spring提供的针对数据访问的接口
//声明实体类和key的类型
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {

}
