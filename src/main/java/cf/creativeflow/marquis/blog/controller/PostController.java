package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.Post;
import cf.creativeflow.marquis.blog.exceptions.PostNotFoundException;
import cf.creativeflow.marquis.blog.repository.PostRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;

    PostController(PostRepository postRepository, ObjectMapper objectMapper){
        this.postRepository = postRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public List<Post> getAllPosts(){

        return postRepository.findAll();
    }

    @GetMapping("/{id}")
    public Post getPostById(@PathVariable Long id){

        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @PostMapping
    public Post createPost(@RequestBody Post post){

        Date creationDate = new Date();
        post.setCreationDate(creationDate);
        post.setLastChangeDate(creationDate);
        System.out.println(post);

        return postRepository.save(post);
    }

    @PutMapping("/{id}")
    public Post fullUpdatePostById(@PathVariable Long id, @RequestBody Post modifiedPost){

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
        BeanUtils.copyProperties(modifiedPost, post, "id");
        post.setLastChangeDate(new Date());

        return postRepository.save(post);
    }

    @PatchMapping("/{id}")
    public Post partialUpdatePostById(@PathVariable Long id){

        return postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id){

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(id));

        postRepository.delete(post);
    }

}
