package project.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.domain.*;
import project.domain.Board;
import project.domain.Favorite;
import project.domain.Image;
import project.domain.Reply;

import java.util.List;

@Repository
@Transactional
public class BoardRepository {

    @PersistenceContext
    private EntityManager em;

    //게시물 등록
    public void saveBoard(Board board){
        em.persist(board);
    }

    //파일 등록
    public void saveFile(Image image){
        em.persist(image);
    }

    //좋아요 등록 -------
    public void saveBoardLike(BoardLike boardLike) {
        em.persist(boardLike);
    }

    // 게시물에 대한 특정 사용자의 좋아요 삭제
    public void removeBoardLike(Member member, Board board) {
        em.createQuery("DELETE FROM BoardLike bl WHERE bl.member = :member AND bl.board = :board")
                .setParameter("member", member)
                .setParameter("board", board)
                .executeUpdate();
    }

    public BoardLike findBoardLike(Member member, Board board){
        return em.createQuery("select bl from BoardLike bl where bl.member =:member AND bl.board = : board", BoardLike.class)
                .setParameter("member", member)
                .setParameter("board", board)
                .getSingleResult();
    }


    // 게시물에 대한 특정 사용자의 좋아요 여부 확인
    public Long countBoardLikesByMemberAndBoard(Member member, Board board) {
        return em.createQuery(
                        "SELECT COUNT(bl) FROM BoardLike bl WHERE bl.member = :member AND bl.board = :board", Long.class)
                .setParameter("member", member)
                .setParameter("board", board)
                .getSingleResult();
    }

    //게시물에 대한 좋아요 개수
    public long countLikesForBoard(Board board) {
        return em.createQuery(
                        "SELECT COUNT(bl) FROM BoardLike bl WHERE bl.board = :board", Long.class)
                .setParameter("board", board)
                .getSingleResult();
    }


    // 게시물 수정---------------------
    public void modifyBoard(Board board) {
            // 업데이트된 엔티티를 merge
            em.merge(board);
    }

    //이미지 삭제
    public void deleteImage(Board board){
        em.createQuery("delete from Image i where i.board.id =: id")
                .setParameter("id", board.getId())
                .executeUpdate();
    }


    public Board saveBoardReturn(Board board){
        em.persist(board);
        return board;
    }

    //즐겨 찾기 기능
    public void addFavorite(Favorite favorite){
        em.persist(favorite);
    } 

    //즐겨 찾기 목록
    public List<Board> favoriteList(Long id){
        return em.createQuery("select f.board from Favorite f where f.member.id =: id", Board.class)
                .setParameter("id", id)
                .getResultList();
    }

    //댓글 등록
    public void saveReply(Reply reply){
        em.persist(reply);
    }


    //게시물 조회
    public Board findByBoardId(Long id){
        return em.find(Board.class, id);
    }

    //게시물 안의 이미지
    public List<Image> findImagesByBoardId(Long id){
        return em.createQuery("select i from Image i where i.board.id = :id", Image.class)
                .setParameter("id", id)
                .getResultList();

    }


    //게시물 리스트
    public List<Board> findAll(){
        return em.createQuery("select b from Board b", Board.class)
                .getResultList();
    }

    //페이지로 리스트 조회
    public List<Board> findPaginatedBoards(int page, int size){
        return em.createQuery("select b from Board b order by b.boardDate desc ", Board.class)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    //댓글 리스트
    public List<Reply> findReplyList(Long boardId){
        return em.createQuery("select r from Reply r where r.board.id = :boardId ", Reply.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    //제목 검색기능
    public List<Board> findTitleSearchBoards(String keyword){
        return em.createQuery("SELECT b FROM Board b WHERE b.title LIKE :keyword", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    //내용 검색기능
    public List<Board> findContentSearchBoards(String keyword){
        return em.createQuery("SELECT b FROM Board b WHERE b.content LIKE :keyword", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }

    //제목 + 내용 검색기능
    public List<Board> findContentTitleSearchBoards(String keyword){
        return em.createQuery("SELECT b FROM Board b WHERE (b.content LIKE :keyword) OR (b.title LIKE :keyword)", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getResultList();
    }


    //조회 정렬 기능
    public List<Board> findClickBoards(){
        return em.createQuery("select b from Board b order by b.click desc", Board.class)
                .getResultList();
    }

    //내가 쓴 게시물 목록
    public List<Board> findMyBoards(Long id){
        return em.createQuery("select b from Board b where b.member.id = : id")
                .setParameter("id", id)
                .getResultList();
    }

}
