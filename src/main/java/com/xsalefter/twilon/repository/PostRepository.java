package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.Post;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface PostRepository extends CassandraRepository<Post, String> {

    Slice<Post> findByText(String text, Pageable pageable);
}
