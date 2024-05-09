package com.example.nmr.repository;

import com.example.nmr.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Integer> {
    Optional<User> findByEmailAndPasswordAndRole(String email, String password, String role);
}
