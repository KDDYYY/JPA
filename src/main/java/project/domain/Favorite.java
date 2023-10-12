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
public class Favorite {

    @Id @GeneratedValue
    @Column(name = "FAVOR_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public Favorite(Member member, Board board) {
        this.member = member;
        this.board = board;
    }

    //즐겨찾기
//    public static Favorite addFavorite(Member member, Board board){
//        Favorite favorite = new Favorite();
//        favorite.setMember(member);
//        favorite.setBoard(board);
//
//        return favorite;
//    }

}
