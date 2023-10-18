<template>
  <PageBanner />
  <h1 class="title">닉네임 변경</h1>
  <hr>

  <br>
  <input class="inputNickname" placeholder="변경 할 닉네임을 입력해주세요." v-model="newNickname" type="text">
  <button :disabled="!isNicknameAvailable" @click="checkDuplicateNickname">중복 확인</button>
  <button @click="changeNickname">변경하기</button>
</template>

<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner,
  },
  data() {
    return {
      newNickname: '', // 사용자가 입력한 닉네임
      isNicknameAvailable: true // 닉네임 중복 여부를 저장할 변수
    };
  },
  methods: {
    async checkDuplicateNickname() {
      try {
        // Request Body로 보낼 데이터
        const requestData = { nickname: this.newNickname };

        // 서버로 닉네임 중복 확인 요청 보내기
        const response = await this.$axios.post('/api/v1/mypage/member/check-nickname', requestData, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('user_token')}`
          }
        });

        // 서버 응답에서 중복 여부 확인
        if (response.data.data === true) {
          // 중복된 닉네임인 경우
          alert('이미 사용 중인 닉네임입니다');
          this.isNicknameAvailable = true; // 버튼 활성화
        } else {
          // 사용 가능한 닉네임인 경우
          alert('사용 가능한 닉네임입니다.');
          this.isNicknameAvailable = false; // 버튼 비활성화
        }
      } catch (err) {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
      }
    },
    async changeNickname() {
      try {
        // Request Body로 보낼 데이터
        const requestData = { nickname: this.newNickname };

        // 서버로 닉네임 변경 요청 보내기
        const response = await this.$axios.put('/api/v1/mypage/member/nickname', requestData, {
          headers: {
            Authorization: `Bearer ${localStorage.getItem('user_token')}`
          }
        });

        // 서버 응답의 상태 코드 확인
        if (response.status === 200) {
          // 닉네임 변경 성공
          alert('닉네임 변경이 성공했습니다.');
          this.$router.push({ path: '/mypage' });
        } else {
          // 닉네임 변경 실패
          alert('닉네임 변경에 실패했습니다.');
        }
      } catch (err) {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
      }
    }
  }
}
</script>
