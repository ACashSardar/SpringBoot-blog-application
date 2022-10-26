package com.akash.blog.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.akash.blog.entity.Category;
import com.akash.blog.entity.Post;
import com.akash.blog.entity.User;
import com.akash.blog.payload.PostResponse;
import com.akash.blog.repository.PostRepository;
import com.akash.blog.repository.UserRepository;
import com.akash.blog.service.PostService;
import com.akash.blog.repository.CategoryRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Post getById(Integer id) {
		Post post= postRepository.findById(id).get();
		return post;
	}

	@Override
	public PostResponse getAllPost(int pageNumber, int pageSize) {
		Pageable pageable=PageRequest.of(pageNumber, pageSize);
		Page<Post> pagePost=postRepository.findAll(pageable);
		List<Post> posts=pagePost.getContent();
		PostResponse postResponse=new PostResponse();
		postResponse.setContent(posts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setLastPage(pagePost.isLast());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		return postResponse;
	}

	@Override
	public Post savePost(Post post) {
		if(post.getId()==null)
			post.setCreatedAt(LocalDateTime.now());
		return postRepository.save(post);
	}

	@Override
	public void deletePost(Integer id) {
		postRepository.deleteById(id);
	}

	@Override
	public List<Post> searchByKeywords(String keyword) {
		List<Post> titles=postRepository.findByTitleContaining(keyword);
		List<Post> bodies=postRepository.findByBodyContaining(keyword);
		Set<Post> uniques=new HashSet<>();
		titles.forEach(elem->uniques.add(elem));
		bodies.forEach(elem->uniques.add(elem));
        List<Post> result = new ArrayList<>();
        for (Post p : uniques)
            result.add(p);
		return result;
	}

	@Override
	public List<Post> getPostByCategory(Integer categoryId) {
		Category category=categoryRepository.findById(categoryId).get();
		List<Post> posts=postRepository.findByCategory(category);
		return posts;
	}

	@Override
	public List<Post> getPostByUser(Integer userId) {
		User user=userRepository.findById(userId).get();
		List<Post> posts=postRepository.findByUser(user);
		return posts;
	}

	@Override
	public Post updatePost(Integer id, String title, String body) {
		Post updatedPost=postRepository.findById(id).get();
		updatedPost.setTitle(title);
		updatedPost.setBody(body);
		postRepository.save(updatedPost);
		return updatedPost;
	}
	
	

}
