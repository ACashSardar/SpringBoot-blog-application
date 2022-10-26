package com.akash.blog.service;

import java.util.List;
import com.akash.blog.entity.Post;
import com.akash.blog.payload.PostResponse;

public interface PostService {

	Post getById(Integer id);
	PostResponse getAllPost(int pageNumber, int pageSize);
	Post savePost(Post post);
	void deletePost(Integer id);
	Post updatePost(Integer id, String title, String body);
	List<Post> searchByKeywords(String keyword);
	List<Post> getPostByCategory(Integer categoryId);
	List<Post> getPostByUser(Integer userId);
}
