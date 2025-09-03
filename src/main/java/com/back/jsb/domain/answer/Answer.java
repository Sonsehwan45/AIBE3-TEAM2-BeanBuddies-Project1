package com.back.jsb.domain.answer;

import com.back.jsb.domain.question.Question;
import com.back.jsb.domain.reply.AnswerReply;
import com.back.jsb.domain.user.User;
import com.back.jsb.global.jpa.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Answer extends BaseEntity {
    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User author;

    @ManyToOne
    private Question question;

    @OneToMany(mappedBy = "answer", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<AnswerReply> replies = new ArrayList<>();

    // soft delete를 위한 속성(혹은 LocalDateTime deletedAt을 사용하고 null을 통해 확인)
    private boolean isDeleted = false;

    public Answer(AnswerForm form, User author, Question question) {
        this.author = author;
        this.content = form.getContent();
        this.question = question;
    }

    public void modify(AnswerForm form) {
        setContent(form.getContent());
    }

    public AnswerReply addReply(String replyContent, String username) {
        AnswerReply reply = new AnswerReply(replyContent, this, username);
        replies.add(reply);
        return reply;
    }

    public void removeReply(AnswerReply reply) {
        replies.remove(reply);
    }
}