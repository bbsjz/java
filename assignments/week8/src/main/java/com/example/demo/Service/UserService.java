package com.example.demo.Service;

import com.example.demo.Dao.UserJPARepository;
import com.example.demo.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserJPARepository userJPARepository;

    @Cacheable(cacheNames = "user",key = "#name",condition = "#name!=null")
    public User getUser(String name)
    {
        return userJPARepository.getReferenceById(name);
    }

    @CacheEvict(cacheNames = "user",key="#name")
    public void deleteUser(String name)
    {
        userJPARepository.deleteById(name);
    }

    public void addUser(User user)
    {
        userJPARepository.save(user);
    }

    @CacheEvict(cacheNames = "user",key="#name")
    public void updateUser(String name,User user)
    {
        userJPARepository.deleteById(name);
        userJPARepository.save(user);
    }
}
