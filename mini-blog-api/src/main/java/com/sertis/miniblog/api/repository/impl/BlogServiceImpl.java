package com.sertis.miniblog.api.repository.impl;

import com.sertis.miniblog.api.model.blog.Blog;
import com.sertis.miniblog.api.repository.inf.BlogRepository;
import com.sertis.miniblog.api.repository.inf.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "BlogService")
@Transactional
public class BlogServiceImpl implements BlogService {

    @Autowired
	private BlogRepository blogRepository;

	@Override
	public List<Blog> getAllBlogs()  {
		return (List<Blog>) blogRepository.findAll();
	}
}
