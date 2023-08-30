package project.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.domain.Board;
import project.domain.Member;
import project.repository.BoardRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public void saveBoard(Board board, Member member){
        board.setMember(member);
        boardRepository.save(board);
        member.getBoards().add(board);
    }

    public Board findOne(Long id){
        Board findBoard = boardRepository.findById(id);
        return findBoard;
    }

    public List<Board> findBoards(){
        return boardRepository.findAll();
    }


}
