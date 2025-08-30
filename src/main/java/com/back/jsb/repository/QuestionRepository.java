package com.back.jsb.repository;

import com.back.jsb.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {

    Question findByTitle(String title);

    Question findByTitleAndContent(String title, String content);

    List<Question> findByTitleLike(String title);

    List<Question> findByTitleContaining(String keyword);

    List<Question> findByContentContaining(String keyword);
}
