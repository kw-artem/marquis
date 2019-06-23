package cf.creativeflow.marquis.blog.service.impl;

import cf.creativeflow.marquis.blog.domain.Article;
import cf.creativeflow.marquis.blog.exception.ArticleNotFoundException;
import cf.creativeflow.marquis.blog.repository.ArticleRepository;
import cf.creativeflow.marquis.blog.service.ArticleService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ArticleServiceImpl(ArticleRepository articleRepository, ObjectMapper objectMapper) {
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
    }


    @Override
    public void checkExistenceArticleById(Long id) {
        articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException(id));
    }

    @Override
    public List<Article> getLastPublishedArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article loadArticleById(Long id) {
        checkExistenceArticleById(id);
        return articleRepository.getOne(id);
    }

    @Override
    public void createNewArticle(Article article) {

        Date creationDate = new Date();
        article.setCreationDate(creationDate);
        article.setLastChangeDate(creationDate);

        //todo: Author

        articleRepository.save(article);
    }

    @Override
    public Article editArticleById(Long id, Article newVersionOfArticle) {

        checkExistenceArticleById(id);
        Article article = articleRepository.getOne(id);

        //todo: version

        BeanUtils.copyProperties(newVersionOfArticle, article, new String[]{"id", "creationDate"});
        article.setLastChangeDate(new Date());

        return articleRepository.save(article);
    }

    @Override
    public void removeArticleById(Long id) {

        checkExistenceArticleById(id);
        articleRepository.deleteById(id);
    }

    @Override
    public Article partialUpdateArticleById(Long id, Article newVersionOfArticle) {

        List<String> nullFields = new ArrayList<>();

        checkExistenceArticleById(id);
        Article article = articleRepository.getOne(id);

        Map<String, Object> fields = objectMapper.convertValue(newVersionOfArticle, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> field : fields.entrySet()) {
            if (field.getValue() == null) nullFields.add(field.getKey());
        }

        BeanUtils.copyProperties(newVersionOfArticle, article, nullFields.toArray(new String[nullFields.size()]));
        article.setLastChangeDate(new Date());

        return articleRepository.save(article);
    }
}
