package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
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

    //회원 id 반환
    public Long idReturn(String email){
        return (memberRepository.findByEmail(email)).getId();
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

        if(member == null){
            //조회 결과가 없다
            return null;
        }else{
            //조회 결과가 있다.
            if(member.getPw().equals(member.getPw())){
                //비밀번호 일치
                return member;
            } else{
                //비밀번호 불일치(로그인 실패)
                return null;
            }
        }
    }

    //사용자가 쓴 게시물 목록
    public List<Board> memberBoard(Long id){
        return memberRepository.memberBoard(id);
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    public Member findByEmail(String email){
        return memberRepository.findByEmail(email);
    }

}
