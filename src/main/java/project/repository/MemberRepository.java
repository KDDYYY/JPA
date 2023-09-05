package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
import project.domain.Member;

import java.util.List;

@Repository
@Transactional
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    //등록
    public void save(Member member){
        em.persist(member);
    }

    //조회
    public Member findById(Long id){
        return em.find(Member.class, id);

    }


    //로그인
    public Member findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }


    //아이디 중복
    public List<Member> dupliCate(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    //사용자가 쓴 게시글 목록
    public List<Board> memberBoard(Long id){
        return em.createQuery("select b from Board b where b.member.id =: id", Board.class)
                .setParameter("id", id)
                .getResultList();
    }


}


