package project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Board {

    @Id @GeneratedValue
    @Column(name = "BOARD_ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "UPLOAD_DATE")
    private LocalDateTime boardDate;

    @Column(name = "CLICK_COUNT")
    private int click = 0; // 조회수

    @Column(name = "LIKE_COUNT")
    private int likeCount = 0; //좋아요 수

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();


    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<BoardLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    public Board(String title, String content, LocalDateTime boardDate) {
        this.title = title;
        this.content = content;
        this.boardDate = boardDate;
    }

    public void addClick(){
        click++;
    }

    public void addLike(BoardLike like) {
        likes.add(like);
        likeCount++;
    }

    public void removeLike(BoardLike like) {
        likes.remove(like);
        likeCount--;
    }

}
