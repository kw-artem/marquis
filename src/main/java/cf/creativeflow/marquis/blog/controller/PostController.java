package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.Post;
import cf.creativeflow.marquis.blog.exceptions.PostNotFoundException;
import cf.creativeflow.marquis.blog.repository.PostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanInfoFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    PostController(PostRepository postRepository, ObjectMapper objectMapper){
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<Post> getAllPosts(){

        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable("id") Post post){

        return post;
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){

        Date creationDate = new Date();
        post.setCreationDate(creationDate);
        post.setLastChangeDate(creationDate);

        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post fullUpdatePostById(@PathVariable("id") Post post, @RequestBody Post modifiedPost){

        BeanUtils.copyProperties(modifiedPost, post, "id", "creationDate");
        post.setLastChangeDate(new Date());


        return postRepository.save(post);
    }

    @PatchMapping("/{id}")
    public Post partialUpdatePostById(@PathVariable("id") Post post){

        return post;
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable("id") Post post){

        postRepository.delete(post);
    }

}
