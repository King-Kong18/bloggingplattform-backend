package ch.bbw.bloggingplattform.likes;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlogLikeRepository extends JpaRepository<BlogLike, Long> {
    Optional<BlogLike> findByUserIdAndBlogId(Long userId, Long blogId);

    void deleteByUserIdAndBlogId(Long userId, Long blogId);
}
