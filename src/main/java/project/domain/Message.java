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
public class Message {

    @Id @GeneratedValue
    @Column(name = "MSG_ID")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "send_member_id")
    private Member sendMember; //보낸사람

    @ManyToOne
    @JoinColumn(name = "receive_member_id")
    private Member receiveMember; //받는사람

    public Message(String title, String content, Member sendMember, Member receiveMember) {
        this.title = title;
        this.content = content;
        this.sendMember = sendMember;
        this.receiveMember = receiveMember;
    }
}
