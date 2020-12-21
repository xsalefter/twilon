package com.xsalefter.twilon.repository;

import com.xsalefter.twilon.entity.post.Post;
import com.xsalefter.twilon.entity.post.PostByUsernamePostingDate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

/**
 * Aggregate repository operations for {@link com.xsalefter.twilon.entity.post.Post} entity.
 */
@Repository
public class PostPersistence {

    private final PostRepository postRepo;
    private final PostByUsernamePostingDateRepository byUsernamePostingDateRepo;

    public PostPersistence(PostRepository postRepo, PostByUsernamePostingDateRepository byUsernamePostingDateRepo) {
        this.postRepo = postRepo;
        this.byUsernamePostingDateRepo = byUsernamePostingDateRepo;
    }

    public Post save(Post post) {
        Post saved = postRepo.save(post);
        byUsernamePostingDateRepo.save(new PostByUsernamePostingDate(post.getUsername(), post.getPostingDate(), post.getText()));
        return saved;
    }

    public Slice<PostByUsernamePostingDate> findByUsername(String username, Pageable page) {
        return byUsernamePostingDateRepo.findPostByUsername(username, page);
    }

    public Slice<Post> findByPostText(String text, Pageable pageable) {
        return postRepo.findByText(text, pageable);
    }
}
