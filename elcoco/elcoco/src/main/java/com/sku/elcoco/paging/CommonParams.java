package com.sku.elcoco.paging;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonParams {
    private int page;               //현재 페이지 번호
    private int recordPerPage;      //페이지당 출력할 데이터의 개수
    private int pageSize;           //화면 화단에 출력할 페이지 개수
    private String keyword;         //검색 키워드
    private String searchType;      //검색 유형 - 제목, 내용, 작성자 중 하나 또는 세 가지 전부를 기준으로 LIKE 검색을 하는 데 사용됩니다.
    private Pagination pagination;
}
