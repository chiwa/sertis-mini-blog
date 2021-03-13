package com.sertis.miniblog.api.repository.impl;

import com.sertis.miniblog.api.model.category.Category;
import com.sertis.miniblog.api.model.user.User;
import com.sertis.miniblog.api.repository.inf.CategoryRepository;
import com.sertis.miniblog.api.repository.inf.CategoryService;
import com.sertis.miniblog.api.repository.inf.UserRepository;
import com.sertis.miniblog.api.repository.inf.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service(value = "categoryService")
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
	private CategoryRepository categoryRepository;

	@Override
	public List<Category> getAllCategory() {
		return (List<Category>) categoryRepository.findAll();
	}
}
