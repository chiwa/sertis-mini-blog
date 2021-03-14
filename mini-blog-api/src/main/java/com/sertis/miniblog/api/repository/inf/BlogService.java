package com.sertis.miniblog.api.repository.inf;


import com.sertis.miniblog.api.model.blog.Blog;
import java.util.List;

public interface BlogService {

    List<Blog> getAllBlogs();

}
