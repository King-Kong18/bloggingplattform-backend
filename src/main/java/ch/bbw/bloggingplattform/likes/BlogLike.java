package ch.bbw.bloggingplattform.likes;

import ch.bbw.bloggingplattform.blogs.Blog;
import ch.bbw.bloggingplattform.user.BlogUser;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BlogLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private BlogUser user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    public BlogLike() {
    }

    public BlogLike(BlogUser user, Blog blog) {
        this.user = user;
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public BlogUser getUser() {
        return user;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setUser(BlogUser user) {
        this.user = user;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

}
