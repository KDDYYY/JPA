package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Member;
import project.domain.Message;
import project.repository.MessageRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;

    @Transactional
    public void saveMessage(Message message, Member sendMember, Member receiveMember){
        messageRepository.saveMessage(message);

        sendMember.getSentMessages().add(message);
        receiveMember.getReceivedMessages().add(message);
    }

    public List<Message> findSendMessage(Long id){
        return messageRepository.findSendMessage(id);
    }

    public List<Message> findReceiveMessage(Long id){
        return messageRepository.findReceiveMessage(id);
    }

}
