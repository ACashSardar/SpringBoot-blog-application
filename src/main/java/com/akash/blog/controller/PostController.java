package com.akash.blog.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.akash.blog.config.AppConstants;
import com.akash.blog.entity.Category;
import com.akash.blog.entity.Post;
import com.akash.blog.entity.User;
import com.akash.blog.payload.PostResponse;
import com.akash.blog.service.CategoryService;
import com.akash.blog.service.FileService;
import com.akash.blog.service.PostService;
import com.akash.blog.service.UserService;

@RestController
public class PostController {
	
	@Value("${project.image}")
	private String path;
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CategoryService categoryService;
	
    @GetMapping("/page/{pageNumber}")
    public ModelAndView getAllPosts(@PathVariable("pageNumber") Integer pageNumber){
    	ModelAndView mv=new ModelAndView();
    	PostResponse postResponse= postService.getAllPost(pageNumber,AppConstants.PAGE_SIZE);
    	List<Post> posts=postResponse.getContent();
    	List<Category> categories=categoryService.getAllCategories();
    	mv.addObject("categories", categories);
    	mv.addObject("pagination",true);
    	mv.addObject("posts", posts);
    	mv.addObject("pageNo", postResponse.getPageNumber());
    	mv.addObject("totalPages", postResponse.getTotalPages());
    	mv.addObject("isLast", postResponse.isLastPage());
    	mv.setViewName("home");
        return mv;
    }
    
    @GetMapping("/posts/{id}")
    public ModelAndView getPostById(@PathVariable("id") Integer id){
    	Post post= postService.getById(id);
    	ModelAndView mv=new ModelAndView();
    	mv.addObject("post", post);
    	mv.addObject("editable", false);
    	mv.setViewName("post");
        return mv;
    }
    
    @GetMapping("/posts/edit/{id}")
    public ModelAndView editPost(@PathVariable("id") Integer id) {
    	Post post= postService.getById(id);
    	ModelAndView mv=new ModelAndView();
    	mv.addObject("post", post);
    	mv.addObject("editable", true);
    	mv.setViewName("post");
        return mv;
    }
    
    @PostMapping("/posts/update/{id}")
    public ModelAndView updatePost(
    		@PathVariable("id") Integer id,
    		@RequestParam("title") String title,
    		@RequestParam("body") String body
    ) {
    	Post post= postService.getById(id);
    	ModelAndView mv=new ModelAndView();
    	if(!title.equals("") && !body.equals(""))
    		postService.updatePost(id, title, body);
    	mv.addObject("post", post);
    	mv.addObject("editable", false);
    	mv.setViewName("post");
        return mv;
    }
    
    
    @GetMapping("/posts/new")
    public ModelAndView getNewPostPage(Principal principal) {
		ModelAndView mv=new ModelAndView();
		User user= userService.findUserByEmail(principal.getName());
		Post post=new Post();
		post.setUser(user);
    	List<Category> categories=categoryService.getAllCategories();
    	Collections.reverse(categories);
    	mv.addObject("categories", categories);
    	mv.addObject("post", post);
    	mv.setViewName("newPost");
		return mv;
    }
    
    @PostMapping("/posts/new")
    public ModelAndView saveNewPost(
    		@ModelAttribute Post post,
    		@RequestParam("image") MultipartFile image,
    		@RequestParam("catg") String catg,
    		@RequestParam("newCatg") String newCatg ) throws IOException {
    	
    	String fileName=fileService.uploadImage(path,image);
    	post.setImageName(fileName);
    	if(!newCatg.equals("")) catg=newCatg;
    	Category category=categoryService.getByCategoryName(catg);
    	if(category==null) {
    		category=new Category();
    		category.setCategoryName(catg);
    		categoryService.createCategory(category);
    	}
    	post.setCategory(category);
    	
    	postService.savePost(post);

		return new ModelAndView("redirect:/posts/"+post.getId());
    }
    
	@GetMapping(value="/posts/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
	public void serveImage(@PathVariable("imageName") String imageName,HttpServletResponse response) throws IOException {
		InputStream resource=fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	@GetMapping("/posts/delete/{id}")
	public ModelAndView deletePost(@PathVariable("id") Integer id) throws FileNotFoundException {
		System.out.println("request received");
		Post post=postService.getById(id);
		String filename=post.getImageName();
		if(fileService.deleteImage(path, filename))
			postService.deletePost(id);
		return new ModelAndView("redirect:/");
	}
	
	@GetMapping("/posts/search")
	public ModelAndView searchByKeywords(@RequestParam("keyword") String keyword) {
    	ModelAndView mv=new ModelAndView();
    	List<Post> posts=postService.searchByKeywords(keyword);
    	List<Category> categories=categoryService.getAllCategories();
    	mv.addObject("categories", categories);
    	mv.addObject("posts", posts);
    	mv.setViewName("home");
        return mv;
	}
	
	@GetMapping("/posts/category/{categoryId}")
	public ModelAndView getPostByCategory(@PathVariable("categoryId") Integer categoryId) {
    	ModelAndView mv=new ModelAndView();
    	List<Post> posts=postService.getPostByCategory(categoryId);
    	List<Category> categories=categoryService.getAllCategories();
    	mv.addObject("categories", categories);
    	mv.addObject("posts", posts);
    	mv.setViewName("home");
        return mv;
	}
	
	@GetMapping("/posts/profile/{userId}")
	public ModelAndView getUserProfile(@PathVariable("userId") Integer userId) {
    	ModelAndView mv=new ModelAndView();
    	User user=userService.getUserById(userId);
    	List<Post> posts=postService.getPostByUser(userId);
    	List<Category> categories=categoryService.getAllCategories();
    	mv.addObject("categories", categories);
    	mv.addObject("posts", posts);
    	mv.addObject("userProfile", user);
    	mv.setViewName("home");
        return mv;
	}
	
}
