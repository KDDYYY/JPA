package project.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id @GeneratedValue
    @Column(name = "IMAGE_ID")
    private Long id;

    private String uploadFileName; //사용자 지정 파일 이름

    private String  storedFileName; //저장된 파일 이름

    private String fullPath; //파일 저장 경로

    private Long size; //파일 사이즈

    private String extension; //확장자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Image(String uploadFileName, String storedFileName, String fullPath, Long size, String extension, Board board) {
        this.uploadFileName = uploadFileName;
        this.storedFileName = storedFileName;
        this.fullPath = fullPath;
        this.size = size;
        this.extension = extension;
        this.board = board;
    }
}
