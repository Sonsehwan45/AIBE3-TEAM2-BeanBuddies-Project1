package com.back.jsb.repository;

import com.back.jsb.entity.SiteUser;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SiteUser,Integer> {

    Optional<SiteUser> findByUsername(String username);

}