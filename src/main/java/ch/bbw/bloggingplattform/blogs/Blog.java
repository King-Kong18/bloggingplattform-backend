package ch.bbw.bloggingplattform.blogs;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import ch.bbw.bloggingplattform.comments.Comment;
import ch.bbw.bloggingplattform.user.BlogUser;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private int likes = 0;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private BlogUser autor;

    @ManyToMany
    @JoinTable(name = "blog_likes", joinColumns = @JoinColumn(name = "blog_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<BlogUser> likedByUsers = new ArrayList<>();

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    public Blog(String title, String content, BlogUser autor) {
        this.title = title;
        this.content = content;
        this.autor = autor;
    }

    public void incrementLikes() {
        this.likes++;
    }

    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}
