package upgrad.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import upgrad.model.Category;
import upgrad.model.Post;
import upgrad.model.User;
import upgrad.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping("/posts")
    public String getUserPosts(Model model){
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts",posts);
        return "posts";
    }

    @RequestMapping("/posts/newpost")
    public String newPost(){
        return "/posts/create";
    }

    @RequestMapping(value = "/posts/create",method = RequestMethod.POST)
    public String createPost(Post post, HttpSession session){

        User user = (User) session.getAttribute("loggeduser");
        post.setUser(user);
        if(post.getJavaBlog()!=null){
            Category javaBlogCategory = new Category();
            javaBlogCategory.setCategory(post.getJavaBlog());
            post.getCategories().add(javaBlogCategory);
        }

        if(post.getSpringBlog()!=null){
            Category springBlogCategory = new Category();
            springBlogCategory.setCategory(post.getSpringBlog());
            post.getCategories().add(springBlogCategory);
        }
        postService.createPost(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/editPost",method = RequestMethod.GET)
    public String editPost(@RequestParam("postId") Integer postId, Model model){
        Post post = postService.getPost(postId);
        model.addAttribute("post", post);
        return "posts/edit";
    }

    @RequestMapping(value = "/editPost",method = RequestMethod.PUT)
    public String edit(@RequestParam(name = "postId") Integer postId, Post post, HttpSession session){
        post.setId(postId);
        User user = (User) session.getAttribute("loggeduser");
        post.setUser(user);
        postService.updatePost(post);
        return "redirect:/posts";
    }

    @RequestMapping(value = "/deletePost", method = RequestMethod.DELETE)
    public String deletePost(@RequestParam(name = "postId") Integer postId){
        postService.deletePost(postId);
        return "redirect:/posts";
    }
}
