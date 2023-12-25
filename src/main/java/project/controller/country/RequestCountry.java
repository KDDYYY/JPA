package project.controller.country;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestCountry {
    //초기 설정
    int page;                // 페이지 번호
    int perPage;             //페이지 당 개수
    String countryName;      //나라 이름
    String countryIso;       //나라 ISO 코드
}