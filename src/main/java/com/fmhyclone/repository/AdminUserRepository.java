package com.fmhyclone.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.fmhyclone.entity.AdminUser;
import java.util.Optional;


public interface AdminUserRepository extends JpaRepository<AdminUser, Long>{
    Optional<AdminUser> findByUsername(String username);
}
