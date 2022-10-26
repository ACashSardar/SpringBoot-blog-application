package com.akash.blog.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.akash.blog.entity.Category;
import com.akash.blog.entity.User;
import com.akash.blog.service.CategoryService;
import com.akash.blog.service.RoleService;
import com.akash.blog.service.UserService;
import com.akash.blog.config.AppConstants;

@RestController
public class AdminController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleService roleService;
	
	@Autowired
	CategoryService categoryService;
	
	@GetMapping("/adminlogin")
	public ModelAndView getAdminLoginPage() {
		return new ModelAndView("adminlogin");
	}
	
	@PostMapping("/adminlogin")
	public ModelAndView authenticateAdmin(Principal principal, @RequestParam("adminPassword") String adminPassword) {
		if(adminPassword.equals(AppConstants.ADMIN_PASSWORD)) {
			User user=userService.findUserByEmail(principal.getName().toString());
			if(!user.getRoles().contains(roleService.getById(1))) {
				user.getRoles().add(roleService.getById(1));
				userService.saveUser(user,false);
			}
			return new ModelAndView("redirect:/dashboard/users");
		}
		return new ModelAndView("adminlogin","message","Invalid admin password!");
	}
	
	@GetMapping("/dashboard/users")
	public ModelAndView getDashboard(Principal principal) {
		User user=userService.findUserByEmail(principal.getName().toString());
		if(!user.getRoles().contains(roleService.getById(1))) {
			return new ModelAndView("redirect:/adminlogin");
		}
		
		ModelAndView mv=new ModelAndView();
		List<User> users=userService.getAllUsers();
		List<Category> categories=categoryService.getAllCategories();
		mv.addObject("users", users);
		mv.addObject("categories", categories);
		mv.setViewName("adminDashboard");
		return mv;
	}
	
	@GetMapping("/users/delete/{userId}")
	public ModelAndView deleteUser(@PathVariable("userId") Integer userId) {
		userService.deleteUser(userId);
		return new ModelAndView("redirect:/dashboard/users");
	}
	
	@GetMapping("/categories/delete/{categoryId}")
	public ModelAndView deleteCategory(@PathVariable("categoryId") Integer categoryId) {
		categoryService.deleteCategory(categoryId);
		return new ModelAndView("redirect:/dashboard/users");
	}
}
