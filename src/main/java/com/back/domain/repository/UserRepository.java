package com.back.domain.repository;

import com.back.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Question, Integer> {

}