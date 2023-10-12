package project.service;

import static org.junit.jupiter.api.Assertions.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Member;
import project.repository.MemberRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional //테스트 끝나면 롤백 TEST 케이스만
public class MemberServiceTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    public void 회원중복테스트() throws Exception{

        Member member = new Member();
        
        member.setName("kim");
        member.setEmail("1");
        member.setPw(3L);

        em.persist(member);

        Member member1 = new Member();
        member1.setName("kim");
        member1.setEmail("1");
        member1.setPw(3L);

        IllegalStateException e = assertThrows(IllegalStateException.class, () ->
                memberService.join(member1));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 아이디 입니다.");



    }

}