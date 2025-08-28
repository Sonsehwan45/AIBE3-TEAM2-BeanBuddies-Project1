package com.back.domain.repository;

import com.back.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findByTitleContaining(String keyword);
    List<Question> findByContentContaining(String keyword);
}