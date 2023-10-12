package project;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
import project.domain.Favorite;
import project.domain.Member;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void dbInit() {

            Member member = new Member("1","1",1L);
            Board board = new Board("롤", "사이온", LocalDateTime.now());
            board.setMember(member);

            Favorite favorite = new Favorite(member, board);

            member.getFavorites().add(favorite);

            em.persist(member);
            em.persist(board);
            em.persist(favorite);
        }

    }
}