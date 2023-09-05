package project.controller;


import com.oracle.wls.shaded.org.apache.xpath.operations.Mult;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.domain.Board;
import project.domain.Image;
import project.domain.Member;
import project.domain.Reply;
import project.service.BoardService;
import project.service.FileUploadService;
import project.service.MemberService;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoradController {

    private final BoardService boardService;

    private final MemberService memberService;

    FileUploadService fileUploadService;

    @GetMapping("/home")
    public String main(Model model, @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size){

        List<Board> boards = boardService.findPaginatedBoards(page, size);
        //List<Board> boards = boardService.findBoards();

        model.addAttribute("boards", boards);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "practice/home";
    }

    //jsp 페이지
//    @GetMapping("/home")
//    public String main(Model model){
//        List<Board> boards = boardService.findBoards();
//        model.addAttribute("boards", boards);
//        return "main";
//    }

    //게시물 등록
    @GetMapping("/boards/new")
    public String createBoard(Model model, HttpSession session) {
        Member sessionMember = (Member) session.getAttribute("member");

        if (sessionMember != null) {
            model.addAttribute("BoardForm", new BoardForm());
            return "practice/board";
        } else {
            return "redirect:/members/login";
        }
    }



    // 게시물 등록 및 파일 업로드
    @PostMapping("/boards/new")
    public String create(@Valid BoardForm boardForm, HttpSession session,
                         @RequestParam("files") List<MultipartFile> files) {
        try {
            Member sessionMember = (Member) session.getAttribute("member");

            if (sessionMember == null) {
                return "redirect:/members/login";
            }

            Member member = memberService.findByEmail(sessionMember.getEmail());

            // 게시물 저장
            Board board = new Board(boardForm.getTitle(), boardForm.getContent(), LocalDateTime.now());

            if (files != null && !files.isEmpty() && !files.get(0).isEmpty()) {
                boardService.saveBoard(board, member, files); //이미지가 포함 될 때
            } else {
                boardService.saveBoard(board, member); //이미지가 포함되지 않을 때
            }

            return "redirect:/home";
        } catch (Exception e) {
            return "redirect:/members/login";
        }
    }


    //게시물 조회
    @GetMapping("/boards/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boardService.increaseBoardClick(id);
        List<Reply> replies = boardService.findReplies(id);
        List<Image> images = boardService.findImagesByBoardId(id);

        model.addAttribute("board", board);
        model.addAttribute("ReplyForm", new ReplyForm());
        model.addAttribute("replies", replies);

        if (images != null && !images.isEmpty()) {
            model.addAttribute("images", images);
        }

        return "practice/boardView";
    }

    //댓글기능
    @PostMapping("/boards/{id}/reply")
    public String setReply(@ModelAttribute ReplyForm replyForm , HttpSession session, @PathVariable Long id){
        Member sessionMember = (Member) session.getAttribute("member");
        Member member = memberService.findByEmail(sessionMember.getEmail());

        Board board = boardService.findOne(id);
        Reply reply = new Reply(replyForm.getContent(), replyForm.getRate());

        boardService.saveReply(reply, board, member);

        return "redirect:/boards/" + id;
}

//검색기능
@GetMapping("/boards/search")
    public String searchBoards(@RequestParam("keyword")String keyword, Model model){
    List<Board> searchResults = boardService.findSearchBoards(keyword);
    model.addAttribute("searchResults", searchResults);
    return "practice/searchList";
}

//조회순 정렬
    @GetMapping("/boards/click")
    public String searchClickBoards(Model model){
        List<Board> clickResults = boardService.findClickBoards();
        model.addAttribute("clickResults", clickResults);
        return "practice/clickList";
    }

    //즐겨찾기 기능
    @PostMapping("/boards/favorite")
    public String addFavorite(@RequestParam("boardId") Long boardId, HttpSession session) {
        try {
            Member sessionMember = (Member) session.getAttribute("member");
            Board board = boardService.findOne(boardId);

            Member member = memberService.findByEmail(sessionMember.getEmail()); // 세션에서 가져온 Member를 영속 상태로 가져옴


            boardService.addFavorite(member, board);

            return "redirect:/home";
        } catch (Exception e) {
            return "practice/home";
        }
    }

    //즐겨찾기 목록
    @GetMapping("/members/favorites")
    public String listFavorite(HttpSession session, Model model){
        Member sessionMember = (Member) session.getAttribute("member");

        List<Board> favorList = boardService.favoriteList(sessionMember.getId());
        model.addAttribute("favorList", favorList);
        return "practice/favorList";
    }


}
