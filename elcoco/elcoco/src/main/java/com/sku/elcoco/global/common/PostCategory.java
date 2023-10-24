package com.sku.elcoco.global.common;

import com.sku.elcoco.global.exception.NotFoundException;
import com.sku.elcoco.global.model.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PostCategory {

    NOTICE("공지사항"),

    QNA("질문"),

    INFO("정보글"),

    FREE("자유게시판"),

    GW("졸업작품"),

    /*강의 카테고리*/
    EN1001("전자컴퓨터공학개론"),
    EN1002("C프로그래밍"),
    EN1003("C++프로그래밍"),
    EN1004("파이썬프로그래밍"),
    EN1005("Java프로그래밍"),
    EN1006("논리회로"),
    EN1007("논리회로설계실습"),
    EN1008("회로이론"),
    EN1009("이산수학"),
    EN1010("알고리즘"),
    EN1011("컴퓨터구조"),
    EN1012("기초전자회로"),
    EN1013("전자회로응용"),
    EN1014("자료구조"),
    EN1015("선형대수학"),
    EN1016("신호및시스템"),
    EN1017("공학수학"),
    EN1018("디지털응용회로설계"),
    EN1019("운영체제"),
    EN1020("컴퓨터네트워크"),
    EN1021("데이터베이스시스템"),
    EN1022("컴퓨터그래픽스"),
    EN1023("멀티미디어시스템"),
    EN1024("제어공학"),
    EN1025("마이크로컨트롤러"),
    EN1026("디지털신호처리"),
    EN1027("디지털통신"),
    EN1028("반도체공학"),
    EN1029("전파공학"),
    EN1030("디지털영상처리"),
    EN1031("인공지능"),
    EN1032("네트워크프로그래밍"),
    EN1033("시스템프로그래밍"),
    EN1034("VR/AR"),
    EN1035("모바일프로그래밍"),
    EN1036("로봇공학"),
    EN1037("임베디드시스템"),
    EN1038("SoC설계"),
    EN1039("하드웨어설계언어및실험"),
    EN1040("무선시스템"),
    EN1041("머신러닝"),
    EN1042("컴퓨터비전"),
    EN1043("데이터사이언스"),
    EN1044("웹서버프로그래밍"),
    EN1045("클라우드컴퓨팅"),
    EN1046("자율주행"),
    EN1047("IoT프로그래밍"),
    EN1048("SoC응용프로젝트"),
    EN1049("딥러닝"),
    EN1050("빅데이터분석및시각화"),
    EN1051("인증시스템"),
    EN1052("게임프로그래밍"),
    EN1053("휴먼컴퓨터인터랙션"),
    EN1054("디지털통신실습"),
    EN1055("강화학습"),
    EN1056("데이터베이스관리"),
    EN1057("빅데이터분산컴퓨팅"),
    EN1058("지능시스템"),
    EN1059("현대암호와정보보안"),
    EN1060("신기술세미나"),
    EN1061("다학년다학기프로젝트1"),
    EN1062("다학년다학기프로젝트2"),
    EN1063("공학종합설계1"),
    EN1064("공학종합설계2"),
    EN1066("졸업논문및시험");

    String postCategory;

    public static PostCategory of(String postCategory) {
        return Arrays.stream(PostCategory.values())
                .filter(type -> type.toString().equalsIgnoreCase(postCategory))
                .findAny().orElseThrow(() -> new NotFoundException(ResponseStatus.FAIL_POST_CATEGORY_NOT_FOUND));
    }

}