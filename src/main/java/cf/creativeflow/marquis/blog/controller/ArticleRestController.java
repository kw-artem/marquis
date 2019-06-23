package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.Article;
import cf.creativeflow.marquis.blog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class ArticleRestController {

    private final ArticleService articleService;

    @Autowired
    ArticleRestController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<List<Article>> getAllArticles() {

        return new ResponseEntity<>(articleService.getLastPublishedArticles(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") Long id) {

        return new ResponseEntity<>(articleService.loadArticleById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createArticle(@RequestBody Article article) {

        articleService.createNewArticle(article);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Article> fullUpdateArticleById(@PathVariable("id") Long id, @RequestBody Article newVersionOfArticle) {

        return new ResponseEntity<>(articleService.editArticleById(id, newVersionOfArticle), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Article> partialUpdateArticleById(@PathVariable("id") Long id, @RequestBody Article newVersionOfArticle) {

        return new ResponseEntity<>(articleService.partialUpdateArticleById(id, newVersionOfArticle), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable("id") Long id) {

        articleService.removeArticleById(id);
    }

}
