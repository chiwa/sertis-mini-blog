package com.sertis.miniblog.api.repository.inf;


import com.sertis.miniblog.api.model.blog.Blog;
import com.sertis.miniblog.api.model.user.User;

import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();
    Blog findById(Integer id);
    Blog save(Blog blog);

}