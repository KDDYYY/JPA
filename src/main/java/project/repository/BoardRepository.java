package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;

import java.util.List;

@Repository
@Transactional
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Board board){
        em.persist(board);
    }

    public Board findById(Long id){
        return em.find(Board.class, id);
    }


    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }
}
