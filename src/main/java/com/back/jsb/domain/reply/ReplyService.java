package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.Answer;
import com.back.jsb.domain.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReplyService {

    private final ReplyRepository replyRepository;

    @Transactional
    public void registerReply(Answer answer, ReplyRegisterForm replyForm, User user) {
        answer.addReply(replyForm.content(), user);
    }

    @Transactional
    public void deleteReply(Answer answer, Long id, User user) {
        AnswerReply reply = findById(id);
        if (!reply.getAuthor().getUsername().equals(user.getUsername())) {
            throw new SecurityException("");
        }

        answer.removeReply(id);
    }

    private AnswerReply findById(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reply with id " + id + " not found"));
    }
}
