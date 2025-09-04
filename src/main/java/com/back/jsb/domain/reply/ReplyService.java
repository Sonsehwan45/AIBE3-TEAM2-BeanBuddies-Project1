package com.back.jsb.domain.reply;

import com.back.jsb.domain.answer.Answer;
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
    public void registerReply(Answer answer, ReplyRegisterForm replyForm, String username) {
        answer.addReply(replyForm.content(), username);
    }

    @Transactional
    public void deleteReply(Answer answer, Long id, String username) {
        AnswerReply reply = findById(id);
        if (!reply.getAuthor().equals(username)) {
            throw new SecurityException("");
        }

        answer.removeReply(id);
    }

    private AnswerReply findById(Long id) {
        return replyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reply with id " + id + " not found"));
    }
}
