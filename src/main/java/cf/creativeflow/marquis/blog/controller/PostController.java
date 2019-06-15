package cf.creativeflow.marquis.blog.controller;

import cf.creativeflow.marquis.blog.domain.Post;
import cf.creativeflow.marquis.blog.exceptions.PostNotFoundException;
import cf.creativeflow.marquis.blog.repository.PostRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostRepository postRepository;

    PostController(PostRepository postRepository){
        this.postRepository = postRepository;
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
        return postRepository.save(post);
    }

}
