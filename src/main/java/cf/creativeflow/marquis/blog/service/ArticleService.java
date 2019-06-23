package cf.creativeflow.marquis.blog.service;

import cf.creativeflow.marquis.blog.domain.Article;
import cf.creativeflow.marquis.blog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface ArticleService {

    void checkExistenceArticleById(Long id);

    List<Article> getLastPublishedArticles();

    Article loadArticleById(Long id);

    void createNewArticle(Article article);

    Article editArticleById(Long id, Article newVersionOfArticle);

    //todo: need to refactor the method below
    Article partialUpdateArticleById(Long id, Article newVersionOfArticle);

    void removeArticleById(Long id);

}
