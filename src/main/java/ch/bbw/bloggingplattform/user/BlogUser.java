package ch.bbw.bloggingplattform.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ch.bbw.bloggingplattform.blogs.Blog;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BlogUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Blog> blogs = new ArrayList<>();

    @ManyToMany(mappedBy = "likedByUsers")
    @JsonIgnore
    private List<Blog> likedBlogs = new ArrayList<>();

    public BlogUser(String username, String password) {
        this.username = username;
        this.password = password;
        this.blogs = new ArrayList<>();
        this.likedBlogs = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public List<Blog> getLikedBlogs() {
        return likedBlogs;
    }

    public void setLikedBlogs(List<Blog> likedBlogs) {
        this.likedBlogs = likedBlogs;
    }

}
