package com.akash.blog.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 1000)
	private String title;
	@Column(length = 10000)
	private String body;
	private String imageName;
	private LocalDateTime createdAt;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id",nullable =false)
	private User user;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id",nullable =false)
	private Category category;
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Comment> comments=new ArrayList<>();
	
	@OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
	private List<Like> likes=new ArrayList<>();
	
}
