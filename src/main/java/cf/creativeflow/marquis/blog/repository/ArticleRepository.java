package cf.creativeflow.marquis.blog.repository;

import cf.creativeflow.marquis.blog.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

}
