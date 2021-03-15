package com.sertis.miniblog.api.repository.inf;


import com.sertis.miniblog.api.model.user.User;

public interface UserService {

    User findByUserName(String username);

    User save(User user);

    void deleteById(int id);

}
