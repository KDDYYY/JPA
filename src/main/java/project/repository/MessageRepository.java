package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Message;

import java.util.List;

@Repository
@Transactional
public class MessageRepository {

    @PersistenceContext
    private EntityManager em;

    public void saveMessage(Message message){
        em.persist(message);
    }

    //해당 멤버의 메세지 목록
    public List<Message> findSendMessage(Long id){
        return em.createQuery("select m from Message m where m.sendMember.id =: id")
                .setParameter("id", id)
                .getResultList();
    }

    public List<Message> findReceiveMessage(Long id){
        return em.createQuery("select m from Message m where m.receiveMember.id =: id")
                .setParameter("id", id)
                .getResultList();
    }


}
