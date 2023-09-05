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

    @Column(name = "FILE_PATH")
    private String filePath; // 파일 저장 경로

    @Column(name = "FILE_EXTENSION")
    private String fileExtension; // 파일 확장자

    @Column(name = "FILE_SIZE")
    private long fileSize; // 파일 크기

    @Column(name = "UPLOAD_DATE")
    private LocalDateTime boardDate;

    private int click = 0; // 죄호수


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reply> replies = new ArrayList<>();


    @OneToMany(mappedBy = "board")
    private List<Image> images = new ArrayList<>();

    public Board(String title, String content, LocalDateTime boardDate) {
        this.title = title;
        this.content = content;
        this.boardDate = boardDate;
    }

    public Board(String title, String content, String filePath, String fileExtension, long fileSize, LocalDateTime boardDate) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.fileSize = fileSize;
        this.boardDate = boardDate;
    }
}
