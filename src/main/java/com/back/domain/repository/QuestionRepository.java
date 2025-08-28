package com.back.domain.repository;

import com.back.domain.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    @Query("SELECT q FROM Question q WHERE q.title LIKE %:kw% OR q.content LIKE %:kw%")
    List<Question> findAllByKeyword(@Param("kw") String kw);
}