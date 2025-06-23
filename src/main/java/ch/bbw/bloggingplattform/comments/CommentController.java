package ch.bbw.bloggingplattform.comments;

import ch.bbw.bloggingplattform.blogs.Blog;
import ch.bbw.bloggingplattform.blogs.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@CrossOrigin(origins = "http://localhost:5173")

public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BlogRepository blogRepository;

    @PostMapping("/{blogId}")
    public ResponseEntity<Comment> addComment(@PathVariable Long blogId, @RequestBody CommentDto dto) {
        Optional<Blog> blogOptional = blogRepository.findById(blogId);

        if (blogOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Blog blog = blogOptional.get();

        Comment comment = new Comment();
        comment.setContent(dto.getContent());
        comment.setAuthor(dto.getAuthor());
        comment.setBlog(blog);
        comment.setCreatedAt(LocalDateTime.now());

        Comment saved = commentRepository.save(comment);

        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{blogId}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return ResponseEntity.ok(comments);
    }
}
