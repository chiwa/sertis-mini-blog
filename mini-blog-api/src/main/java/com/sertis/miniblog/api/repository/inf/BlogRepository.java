package com.sertis.miniblog.api.repository.inf;

import com.sertis.miniblog.api.model.blog.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Integer> {

}
