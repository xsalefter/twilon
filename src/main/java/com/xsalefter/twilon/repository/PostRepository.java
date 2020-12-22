package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface PostRepository extends CassandraRepository<Post, String> {
}
