package com.artem.crudchad.controllers;

import com.artem.crudchad.dao.Posts;
import com.artem.crudchad.repository.PostRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class PostsController {
    final PostRepository postRepository;

    public PostsController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/posts")
    public ResponseEntity<List<Posts>> getAllPosts(@RequestParam(required = false) String title) {
        try {
            List<Posts> Posts = new ArrayList<Posts>();

            if (title == null)
                postRepository.findAll().forEach(Posts::add);
            else
                postRepository.findByTitleContainingIgnoreCase(title).forEach(Posts::add);

            if (Posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(Posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<Posts> getPostsById(@PathVariable("id") long id) {
        Optional<Posts> PostsData = postRepository.findById(id);

        if (PostsData.isPresent()) {
            return new ResponseEntity<>(PostsData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/posts")
    public ResponseEntity<Posts> createPosts(@RequestBody Posts posts) {
        try {
            Posts _posts = postRepository.save(new Posts(posts.getTitle(), posts.getDescription(), false));
            return new ResponseEntity<>(_posts, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity<Posts> updatePosts(@PathVariable("id") long id, @RequestBody Posts posts) {
        Optional<Posts> PostsData = postRepository.findById(id);

        if (PostsData.isPresent()) {
            Posts _posts = PostsData.get();
            _posts.setTitle(posts.getTitle());
            _posts.setDescription(posts.getDescription());
            _posts.setPublished(posts.isPublished());
            return new ResponseEntity<>(postRepository.save(_posts), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<HttpStatus> deletePosts(@PathVariable("id") long id) {
        try {
            postRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/posts")
    public ResponseEntity<HttpStatus> deleteAllPosts() {
        try {
            postRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/posts/published")
    public ResponseEntity<List<Posts>> findByPublished() {
        try {
            List<Posts> Posts = postRepository.findByPublished(true);

            if (Posts.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(Posts, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
