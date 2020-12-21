package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.PostByUsernamePostingDate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

public interface PostByUsernamePostingDateRepository
extends CassandraRepository<PostByUsernamePostingDate, String> {

    @Query("select * from post_by_username_posting_date where username = :username")
    Slice<PostByUsernamePostingDate> findPostByUsername(@Param("username") String username, Pageable pageable);

    Slice<PostByUsernamePostingDate> findByPost(String post, Pageable pageable);
}
