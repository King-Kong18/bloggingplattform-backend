package ch.bbw.bloggingplattform.user;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<BlogUser, Long> {
    Optional<BlogUser> findByUsername(String username);
}
