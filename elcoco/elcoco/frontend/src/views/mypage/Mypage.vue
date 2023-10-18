<template>
  <div class="pageHome">
    <PageBanner/>
    <h1 class="title">마이 페이지</h1>
    <br>
    <div class="subTitle">반가워요!
      <br>
      {{ this.response.nickname }}님
    </div>
    <hr>

    <!-- 세로로 배치된 카드 레이아웃 시작 -->
    <div class="card-container">
      <!-- 카드 1 -->
      <div class="card">
        <h3><strong>내 정보 관리</strong></h3>
        <br>
        <router-link to="/mypage/nickname" v-if="this.$store.state.isLogin" class="nav-link">닉네임 변경</router-link>
        <br>
        <router-link to="/mypage/password" v-if="this.$store.state.isLogin" class="nav-link">비밀번호 변경</router-link>
      </div>

      <!-- 카드 2 -->
      <div class="card">
        <h3><strong>게시물 / 댓글</strong></h3>
        <br>
        <router-link to="/mypage/post" v-if="this.$store.state.isLogin" class="nav-link">내가 작성한 게시글</router-link>
        <br>
        <router-link to="/mypage/reply" v-if="this.$store.state.isLogin" class="nav-link">내가 작성한 댓글</router-link>
        <br>
        <router-link to="/mypage/like/post" v-if="this.$store.state.isLogin" class="nav-link">내가 좋아요 누른 게시글</router-link>

      </div>

      <!-- 카드 3 -->
      <div class="card">
        <h3><strong>쪽지</strong></h3>
        <br>
        <router-link to="/message/received" v-if="this.$store.state.isLogin" class="nav-link">내가 받은 쪽지</router-link>
        <br>
        <router-link to="/message/send" v-if="this.$store.state.isLogin" class="nav-link">내가 보낸 쪽지</router-link>
      </div>
    </div>
    <!-- 세로로 배치된 카드 레이아웃 끝 -->
  </div>
</template>


<style scoped>
/* 카드 컨테이너 스타일 */
.card-container {
  display: flex;
  flex-direction: column; /* 수직으로 배치 */
  gap: 20px; /* 카드 간격 조정 */
  align-items: center; /* 가운데 정렬 (선택 사항) */
}

/* 카드 스타일 */
.card {
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 5px;
  padding: 20px;
  width: 700px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.6);
}

.title {
  font-size: 35px;
  color: black;
}

.subTitle {
  font-size: 25px;
  color: black;

}

/* 카드 제목 스타일 */
.card h3 {
  font-size: 18px;
  margin-bottom: 10px;

}
.pageHome{
  background: #F2F2F2;
}

/* 카드 내용 스타일 */
.card p {
  font-size: 14px;
  color: #333;
}
</style>


<script>

import {defineComponent} from "vue";
import PageBanner from "@/components/PageBanner.vue";

export default defineComponent({
  components: {
    PageBanner,
  },
  data() { //변수생성
    return {
      response: ''
    }
  },

  mounted() {
    this.getMemberDetail()
  },
  methods: {
    getMemberDetail() {
      this.$axios.get("/api/v1/mypage/member", {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {

        this.response = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
      }).catch((err) => {
        if (err.response.status === 401 || err.response.status===404) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
  }
})
</script>