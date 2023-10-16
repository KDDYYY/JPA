package project.controller;

import lombok.Data;

@Data
public class ResponseCountry {
    String resultCode;                  //결과 코드
    String resultMsg;                   //결과 메시지
    int currentCount;                   //현재 개수
    int page;                           //현재 페이지
    int perPage;                        //페이지당 개수
    int totalCount;                     //총 개수
    String country_eng_nm;              //영문 국가명
    String country_nm;                  //한글 국가명
    int country_iso_alp2;               //ISO 2자리 코드
    String continent_cd;                //대륙 코드
    String continent_eng_nm;            //영문 대륙명
    String continent_nm;                //한글 대륙명
    String dang_map_download_url;       //위험 지도 경로
    String flag_download_url;           //국기 다운로드 경로
    String map_download_url;            //지도 다운로드 경로
    int alarm_lvl;                      //경보 단계
    int region_ty;                      //지역 유형
    String remark;                      //경보 내용
    String written_dt;                  //작성일
}
