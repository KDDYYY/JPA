package project.service;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import project.domain.*;
import project.repository.BoardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private static final Logger logger = LoggerFactory.getLogger(BoardService.class);
    private final BoardRepository boardRepository;
    @Value("${upload.path}") // application.properties 또는 application.yml에 파일 업로드 경로를 설정합니다.
    private String uploadPath;

    //게시판 등록
    @Transactional
    public void saveBoard(Board board, Member member, List<MultipartFile> files) throws IOException {
        board.setMember(member);

            for (MultipartFile file : files) {
                String originalFilename = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalFilename);
                String storedFilename = UUID.randomUUID().toString() + "." + extension;
                String fullPath = uploadPath + storedFilename;
                long fileSize = file.getSize();

                // 파일을 서버에 저장
                Files.write(Paths.get(fullPath), file.getBytes());

                // Image 엔티티 생성 및 저장
                Image image = new Image(originalFilename, storedFilename, fullPath, fileSize, extension, board);

                boardRepository.saveFile(image);
                board.getImages().add(image);
            }
            boardRepository.saveBoard(board);
            member.getBoards().add(board);
    }

    //게시물 등록 (이미지 없을 떼)
    @Transactional
    public void saveBoard(Board board, Member member){
        board.setMember(member);
        boardRepository.saveBoard(board);
        member.getBoards().add(board);
    }

    //게시물 좋아요 등록 ------------
    @Transactional
    public void saveBoardLike(Member member, Board board) {
        BoardLike boardLike = new BoardLike(member, board);
        boardRepository.saveBoardLike(boardLike);

        board.addLike(boardLike);
    }

    //좋아요 삭제 메소드
    @Transactional
    public void removeBoardLike(Member member, Board board) {
        BoardLike boardLike = boardRepository.findBoardLike(member, board);
        boardRepository.removeBoardLike(member, board);

        board.removeLike(boardLike);
    }

    //좋아요 여부 확인 메소드
    public boolean isLikedByMember(Member member, Board board) {
        Long likeCount = boardRepository.countBoardLikesByMemberAndBoard(member, board);
        return likeCount > 0;
    }

    //해당 게시물의 전체 좋아요 개수 조회
    public long getLikeCountForBoard(Board board) {
        return boardRepository.countLikesForBoard(board);
    }

    //게시판 수정 -----------------------------
    @Transactional
    public void modifyBoard(Board board, Member member, List<MultipartFile> files) throws IOException {
        board.setMember(member);

        // 이전 이미지 파일 삭제 로직 추가 (예: boardRepository.deleteImages(board))
        boardRepository.deleteImage(board);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String extension = StringUtils.getFilenameExtension(originalFilename);
                String storedFilename = UUID.randomUUID().toString() + "." + extension;
                String fullPath = uploadPath + storedFilename;
                long fileSize = file.getSize();

                // 파일을 서버에 저장
                Files.write(Paths.get(fullPath), file.getBytes());

                // Image 엔티티 생성 및 저장
                Image image = new Image(originalFilename, storedFilename, fullPath, fileSize, extension, board);

                boardRepository.saveFile(image);
                board.getImages().add(image);
            }
        }
        // 게시물 수정
        boardRepository.modifyBoard(board);
        member.getBoards().add(board);
    }

    //게시판 수정(이미지 있을 경우)
    @Transactional
    public void modifyBoard(Board board, Member member){
        board.setMember(member);
        boardRepository.deleteImage(board);

        boardRepository.modifyBoard(board);
        member.getBoards().add(board);
    }


    //해당 게시물의 이미지
    public List<Image> findImagesByBoardId(Long boardId) {
        return boardRepository.findImagesByBoardId(boardId);
    }


    //댓글 등록
    @Transactional
    public void saveReply(Reply reply, Board board, Member member){
        reply.setBoard(board);
        reply.setMember(member);

        boardRepository.saveReply(reply);

        member.getReplies().add(reply);
        board.getReplies().add(reply);
    }

    //조회수 클릭
    @Transactional
    public Board increaseBoardClick(Long boardId) {
        Board board = boardRepository.findByBoardId(boardId);
        if (board != null) {
            board.addClick();
            return boardRepository.saveBoardReturn(board);
        }
        return null;
    }

    //즐겨찾기
    @Transactional
    public void addFavorite(Member member, Board board){
        try {
            Favorite favorite = new Favorite(member, board);

            member.getFavorites().add(favorite);

            boardRepository.addFavorite(favorite);
        } catch (Exception e) {
            logger.error("예외 발생:" + e.getMessage(), e);
        }
    }

    //페이지로 게시글 목록
    public List<Board> findPaginatedBoards(int page, int size){
        return boardRepository.findPaginatedBoards(page, size);
    }

    //즐겨찾기 목록
    public List<Board> favoriteList(Long id){
        return boardRepository.favoriteList(id);
    }

    public Board findOne(Long id){
        return boardRepository.findByBoardId(id);
    }

    //게시물 목록
    public List<Board> findBoards(){
        return boardRepository.findAll();
    }

    //댓글 목록
    public List<Reply> findReplies(Long boardId) {
        return  boardRepository.findReplyList(boardId);
    }

    //제목 검색 기능
    public List<Board> findTitleSearchBoards(String keyword){
        return boardRepository.findTitleSearchBoards(keyword);
    }

    //내용 검색 기능
    public List<Board> findContentSearchBoards(String keyword){
        return boardRepository.findContentSearchBoards(keyword);
    }

    //제목+내용 검색기능
    public List<Board> findContentTitleSearchBoards(String keyword){
        return boardRepository.findContentTitleSearchBoards(keyword);
    }

    //조회 순 정력
    public List<Board> findClickBoards(){
        return boardRepository.findClickBoards();
    }

    //내가 쓴 게시물
    public List<Board> findMyBoards(Long id){ return boardRepository.findMyBoards(id); }
}
