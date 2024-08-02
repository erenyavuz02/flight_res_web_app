package com.hitit.project.microservices.user_app.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hitit.project.microservices.user_app.entity.User;



@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(String username);

    @SuppressWarnings("null")
    Optional<User> findById(@NonNull Long id);

    void deleteByUsername(String username);
}
