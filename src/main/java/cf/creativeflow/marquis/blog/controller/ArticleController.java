package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.Article;
import cf.creativeflow.marquis.blog.repository.ArticleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class ArticleController {

    private final ArticleRepository articleRepository;
    private final ObjectMapper objectMapper;

    ArticleController(ArticleRepository articleRepository, ObjectMapper objectMapper) {
        this.articleRepository = articleRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Article> getAllArticles() {

        return articleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable("id") Article article) {

        return article;
    }

    @PostMapping
    public Article createArticle(@RequestBody Article article) {

        Date creationDate = new Date();
        article.setCreationDate(creationDate);
        article.setLastChangeDate(creationDate);

        return articleRepository.save(article);
    }

    @PutMapping("/{id}")
    public Article fullUpdateArticleById(@PathVariable("id") Article article, @RequestBody Article modifiedArticle) {

        BeanUtils.copyProperties(modifiedArticle, article, new String[]{"id", "creationDate"});
        article.setLastChangeDate(new Date());


        return articleRepository.save(article);
    }

    @PatchMapping("/{id}")
    public Article partialUpdateArticleById(@PathVariable("id") Article article, @RequestBody Article modifiedArticle) {

        List<String> nullFields = new ArrayList<>();

        Map<String, Object> fields = objectMapper.convertValue(modifiedArticle, new TypeReference<Map<String, Object>>() {
        });
        for (Map.Entry<String, Object> field : fields.entrySet()){
            if(field.getValue() == null) nullFields.add(field.getKey());
        }

        BeanUtils.copyProperties(modifiedArticle, article, nullFields.toArray(new String[nullFields.size()]));
        article.setLastChangeDate(new Date());

        return articleRepository.save(article);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Article article) {

        articleRepository.delete(article);
    }

}
