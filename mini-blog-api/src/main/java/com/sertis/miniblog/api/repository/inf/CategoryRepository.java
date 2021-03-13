package com.sertis.miniblog.api.repository.inf;

import com.sertis.miniblog.api.model.category.Category;
import com.sertis.miniblog.api.model.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {

}
