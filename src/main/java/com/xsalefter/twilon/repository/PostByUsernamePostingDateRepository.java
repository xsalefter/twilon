package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.PostByUsernamePostingDate;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface PostByUsernamePostingDateRepository
extends CassandraRepository<PostByUsernamePostingDate, String> {

    @Query("select * from post_by_username_posting_date where username = :username")
    Slice<PostByUsernamePostingDate> findByUsername(@Param("username") String username, Pageable pageable);

    @Query("select * from post_by_username_posting_date where posting_date = :postingDate")
    Slice<PostByUsernamePostingDate> findByPostingDate(Date postingDate, Pageable pageable);

    Slice<PostByUsernamePostingDate> findByPost(String post, Pageable pageable);
}
