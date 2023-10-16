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
public class Address {

    @Id @GeneratedValue
    @Column(name = "ADDRESS_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String address; // 주소
    private double latitude; // 위도
    private double longitude; // 경도

    public Address(Member member) {
        this.member = member;
    }

}