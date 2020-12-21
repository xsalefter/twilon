package com.xsalefter.twilon.entity.post;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.*;

import java.util.Date;

@Table("post_by_username_posting_date")
public class PostByUsernamePostingDate {

    @PrimaryKey
    private Key primaryKey;

    @Column("post")
    private String post;

    // Needed by spring for reflection
    PostByUsernamePostingDate() {}

    public PostByUsernamePostingDate(String username, Date postingDate, String post) {
        primaryKey = new Key(username, postingDate);
        this.post = post;
    }

    public Key getPrimaryKey() {
        return primaryKey;
    }

    public String getPost() {
        return post;
    }

    @PrimaryKeyClass
    public static class Key {

        @PrimaryKeyColumn(name = "username", type = PrimaryKeyType.PARTITIONED)
        private final String username;

        @PrimaryKeyColumn(name = "posting_date", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
        private final Date postingDate;

        public Key(String username, Date postingDate) {
            this.username = username;
            this.postingDate = postingDate;
        }

        public String getUsername() {
            return username;
        }

        public Date getPostingDate() {
            return postingDate;
        }
    }
}
