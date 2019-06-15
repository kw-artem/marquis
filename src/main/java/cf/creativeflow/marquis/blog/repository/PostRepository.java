package cf.creativeflow.marquis.blog.repository;

import cf.creativeflow.marquis.blog.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository <Post, Long> {

}
