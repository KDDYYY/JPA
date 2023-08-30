package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Member;
import project.repository.MemberRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    @Transactional
    public Long join(Member member){
            emailCheck(member);
            memberRepository.save(member);
            return member.getId();
    }

    //회원 이름 반환
    public String nameReturn(String email){
        return (memberRepository.findByEmail(email)).getName();
    }


    //중복 회원 검증
    private void emailCheck(Member member) {
      List<Member> findMembers = memberRepository.dupliCate(member.getEmail());
      if(!findMembers.isEmpty()){
          throw new IllegalStateException("이미 존재하는 아이디 입니다.");
      }
    }

    //로그인
    public Member login(Member member){
        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember == null){
            //조회 결과가 없다
            return null;
        }else{
            //조회 결과가 있다.
            if(member.getPw().equals(findMember.getPw())){
                //비밀번호 일치
                return findMember;
            } else{
                //비밀번호 불일치(로그인 실패)
                return null;
            }
        }
    }



    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

}
