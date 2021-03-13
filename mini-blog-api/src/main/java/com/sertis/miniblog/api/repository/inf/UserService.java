package com.sertis.miniblog.api.repository.inf;


public interface UserService {

    com.sertis.miniblog.api.model.user.User findByUserName(String username);
    com.sertis.miniblog.api.model.user.User save(com.sertis.miniblog.api.model.user.User user);
    void deleteById(int id);
    com.sertis.miniblog.api.model.user.User findById(int id);

}
