package com.medcheck.db.repository;

import com.medcheck.db.entities.Role;
import com.medcheck.db.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where concat(u.firstName, ' ' ,u.lastName) = :name")
    Optional<User> findByEmail(String name);
    @Query("select case when count(u)>0 then true else false end from User u where u.email like :email")
    boolean existsByEmail(@Param(value = "email") String email);

    @Query("select u.role from User u where concat(u.firstName, ' ' ,u.lastName) = :name")
    Role findRoleByUserEmail(String name);

}