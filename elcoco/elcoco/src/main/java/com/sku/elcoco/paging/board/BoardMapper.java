//package com.sku.elcoco.paging.board;
//
//import com.sku.elcoco.domain.board.dto.BoardResponseDto;
//import com.sku.elcoco.paging.CommonParams;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//
///**
// * @Mapper
// *
// * MyBatis는 @Mapper가 선언된 인터페이스와 연결된 XML Mapper에서
// *
// * 메서드명과 동일한 SQL을 찾아 쿼리를 실행합니다.
// *
// *
// */
//@Mapper
//public interface BoardMapper {
//    /**
//     * 게시글 수 조회_
//     * totalRecordCount와 연관되는 메서드입니다.
//     *
//     * 검색 조건의 유무에 따라, 테이블에서 데이터 수를 카운팅 합니다.
//     *
//     * 카운팅 된 데이터 수(totalRecordCount)를 기준으로 페이지 번호를 계산합니다.
//     */
//    int count(final CommonParams params);
//
//    /**
//     * 게시글 리스트 조회_
//     * count( )와 마찬가지로, 검색 조건의 유무를 기준으로 게시글 데이터를 조회합니다.
//     */
//    List<BoardResponseDto> findAll(final CommonParams params);
//}
