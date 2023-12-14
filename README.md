# ELCOCO <br>(SKU Electronic and Computer Engineering Community) <hr>

## 🌱개요 
ELCOCO는 서경대학교 전자컴퓨터공학과 학우들을 위한 익명 커뮤니티 웹 서비스입니다. <br><br>
► **개발기간** : 2023.05 ~ 2023.11

<br>

## 🌱개발환경
|종류|환경|
|--|--|
|Language|Java(JDK17), HTML, CSS, Javascript|
|Framework|Spring Boot, Spring Security, Vue.js|
|Server|Apache Tomcat 9.0|
|DB|MySQL 8.0, JPA, QueryDsl, Redis|
|IDE , Tools|IntelliJ IDEA, MySQL Workbench|

<br>

## 🌱System Architecture
![image](https://github.com/wlshooo/elcoco/assets/118791679/c53aac5d-9969-4d40-a753-a8d478cac674)


## 🌱기능

- 관리자페이지 – 유저 전체 조회, 특정 유저 정보 변경(권한, 닉네임 등), 유저 상세 조회, 특정 유저가 작성한 게시글 조회, 특정 유저가 작성한 게시글 상세 조회, 유저가 작성한 게시글 삭제, 특정 유저가 작성한 전체 댓글 조회, 유저가 작성한 댓글 삭제, 전체 신고 내역 조회, 신고 내역 상세 조회 <br><br>
- 유저 – 회원가입, 로그인, 특정 유저 상세 조회, 전체 유저 조회, 특정 유저 조건 조회, 유저 정보 수정, 유저 삭제 <br><br>
- 메시지(쪽지) - 메시지 생성, 받은 메시지 전체 조회, 받은 메시지 삭제, 보낸 메 시지 전체 조회, 보낸 메시지 삭제 <br><br>
- 마이페이지 – 유저 본인 정보 상세 조회, 유저 본인이 작성한 게시글 전체 조회, 유저 본인이 작성한 댓글 전체 조회, 닉네임 변경, 비밀번호 변경, 내가 좋아요 누른 게시글 전체 조회, 내가 좋아요. 누른 댓글 전체 조회 <br><br>
- 게시글 – 게시글 생성, 파일 업로드, 파일 다운로드, 게시글 조건 조회, 공지사항 조회, 게시글 좋아요. 기능, 게시글 수정, 게시글 삭제, 게시 글 상세 조회, 게시글 조회 수 업데이트 <br><br>
- 댓글 – 댓글 생성, 댓글 전체 조회, 댓글 조건 조회, 댓글 삭 제, 댓글 좋아요. 기능 <br><br>
- 신고 – 신고 생성, 신고당한 게시글 전체 조회, 신고 상세 조회, 신고 조건 조회 <br><br>
- 이메일 인증 – 구글 SMTP를 통해 6자리 인증번호를 통한 재학생 이메일 인증 <br><br>
- 날씨, 시간 – API를 사용하여 메인 홈에 날씨, 시간 표시 <br><br>



## 🌱상세 페이지

### ✏️메인 홈<br> 학교가 위치해있는 서울의 날씨와 현재 시간을 볼 수 있습니다. <br>
<img width="1359" alt="스크린샷 2023-12-06 오후 4 38 00" src="https://github.com/wlshooo/elcoco/assets/118791679/13fa0504-ea1f-476f-88d6-dbb71ab83aa2"> <br>

### ✏️메인 홈(디테일)<br> 게시물을 최신순으로 10개씩 가져와 미리보기 기능을 제공합니다.<br>
<img width="811" alt="스크린샷 2023-12-06 오후 4 23 51" src="https://github.com/wlshooo/elcoco/assets/118791679/f8b4105f-5a2f-424d-9a59-2e0025ef58f5"> <br>


### ✏️게시판 리스트<br> 공지게시물은 일반 게시물 상단에 고정됩니다. 하단에 페이징 기능과 조건 검색 기능을 제공합니다. <br>
<img width="461" alt="스크린샷 2023-12-06 오후 4 18 08" src="https://github.com/wlshooo/elcoco/assets/118791679/2a6129a8-e386-4761-ba8a-99cab5d2cb56"> <br>

### ✏️게시판 상세<br> 파일을 첨부할 수 있습니다. 파일이 이미지인 경우 게시글과 함께 보여지게 됩니다. <br>
![image](https://github.com/wlshooo/elcoco/assets/118791679/2f168bc6-f13f-44da-b58d-c0915f2e299c) <br>

### ✏️메시지(쪽지)<br> 1대1로 다른 사용자들과 소통할 수 있습니다. <br>
<img width="1349" alt="스크린샷 2023-12-06 오후 4 21 42" src="https://github.com/wlshooo/elcoco/assets/118791679/74f3f48a-5ab2-4f41-8c0f-e3312aa6b540"> <br>


### ✏️마이페이지<br> 개인정보 수정 및 유저와 관련된 정보를 확인할 수 있습니다. <br>
<img width="827" alt="스크린샷 2023-12-06 오후 4 19 37" src="https://github.com/wlshooo/elcoco/assets/118791679/71963eee-62e6-4194-8382-3a4730c0765e"> <br>

### ✏️관리자페이지<br> 관리자권한을 가진 유저는 다른 유저의 CRUD가 가능합니다.
<img width="965" alt="스크린샷 2023-12-06 오후 4 20 05" src="https://github.com/wlshooo/elcoco/assets/118791679/acdac85e-f9b4-4768-aa29-0027cd592d02"> <br>





