package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.Post;
import com.xsalefter.twilon.entity.post.PostByUsernamePostingDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.CassandraInvalidQueryException;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;

import java.util.Date;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PostPersistenceIntTest {

    @Autowired
    private PostPersistence postPersistence;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostByUsernamePostingDateRepository byUsernamePostingDateRepository;

    private Post newPost(String username, String text) {
        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setPostingDate(new Date());
        post.setUsername(username);
        post.setText(text);
        return post;
    }

    private Post newPost(Date date) {
        Post post = new Post();
        post.setId(UUID.randomUUID().toString());
        post.setPostingDate(date);
        post.setUsername("findbydate");
        post.setText("find by date");
        return post;
    }

    @BeforeEach
    void beforeEach() {
        postRepository.deleteAll();
        byUsernamePostingDateRepository.deleteAll();
    }

    @Test
    void whenInsertNewPostThenOk() {
        Post post = postPersistence.save(newPost("someuser", "Hello World"));
        assertThat(post.getId()).isNotNull();
        assertThat(postRepository.count()).isEqualTo(1L);
        assertThat(byUsernamePostingDateRepository.count()).isEqualTo(1L);
    }

    @Test
    void whenFindByUsernameThenOk() {
        postPersistence.save(newPost("a", "abc"));
        postPersistence.save(newPost("a", "efg"));
        postPersistence.save(newPost("b", "xyz"));

        Slice<PostByUsernamePostingDate> posts = postPersistence.findByUsername("a", CassandraPageRequest.first(20));

        assertThat(posts.toList().size()).isEqualTo(2);
    }

    @Test
    void whenFindByPostingDateThenOk() {
        Date postingDate = new Date();
        postPersistence.save(newPost(postingDate));
        postPersistence.save(newPost(postingDate));
        postPersistence.save(newPost("anotheruser", "another text"));

        Slice<PostByUsernamePostingDate> posts = postPersistence.findByPostingDate(postingDate, CassandraPageRequest.first(20));

        assertThat(posts.toList().size()).isEqualTo(2);

        posts.forEach(post -> {
            assertThat(post.getPrimaryKey()).isNotNull();
            assertThat(post.getPrimaryKey().getUsername()).isEqualTo("findbydate");
            assertThat(post.getPrimaryKey().getPostingDate()).isEqualTo(postingDate);
            assertThat(post.getPost()).isEqualTo("find by date");
        });
    }

    @Test
    void whenFindByPostTextThenUnpredictablePerformanceException() {
        postPersistence.save(newPost("somename", "Hi.. This is my first post"));

        assertThatThrownBy(() -> {
            Slice<PostByUsernamePostingDate> posts = postPersistence.findByPostText("Hi.. This is my first post", CassandraPageRequest.first(20));
            assertThat(posts).isNull();
        }).isInstanceOf(CassandraInvalidQueryException.class)
        .hasMessageContaining("might involve data filtering and thus may have unpredictable performance");
    }
}
