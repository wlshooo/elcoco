<template>
  <PageBanner />
  <h1 class="title">비밀번호 변경</h1>
  <hr>

  <br>
  <label>새로운 비밀번호</label> <br>
  <input class="inputPassword" placeholder="변경 할 비밀번호 입력해주세요" v-model="newPassword" type="password">
  <br> <br>
  <label>비밀번호 확인</label> <br>
  <input class="inputPassword" placeholder="비밀번호를 다시 한 번 입력해주세요" v-model="checkPassword" type="password">
  <br><br>
  <small v-if="checkPassword===newPassword" class="form-text text-info">
    <strong>비밀번호가 일치합니다!</strong>
  </small>

  <small v-if="checkPassword!==newPassword" class="form-text text-info">
    <strong>비밀번호가 일치하지 않습니다!</strong>
  </small>
  <br> <br>
  <button @click="changePassword" class="custom-button change-button">변경하기</button>
  <br>
</template>

<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner,
  },
  data() {
    return {
      newPassword: '',
      checkPassword:'',
      isCheck:false,
    };
  },
  methods: {

    async changePassword() {
      if (!this.newPassword) {
        alert('변경할 패스워드를 입력해주세요');
        return;
      }
      if (!this.checkPassword) {
        alert('패스워드 확인을 입력해주세요');
        return;
      }
      if(this.newPassword === this.checkPassword) {
        try {
          // Request Body로 보낼 데이터
          const requestData = {password: this.newPassword};

          // 서버로 닉네임 변경 요청 보내기
          const response = await this.$axios.put('/api/v1/mypage/member/password', requestData, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('user_token')}`
            }
          });

          // 서버 응답의 상태 코드 확인
          if (response.status === 200) {
            // 닉네임 변경 성공
            alert('비밀번호 변경이 성공했습니다. ');
            this.$router.push({path: '/mypage'});
          } else {
            // 닉네임 변경 실패
            alert('닉네임 변경에 실패했습니다.');
          }
        } catch (err) {
          if (err.response.status === 401) {
            this.$router.push({path: '/login'});
          } else {
            alert(err.response.data.message);
          }
        }
      }
      else
        {
          alert("두 비밀번호가 일치하지 않습니다.")
        }
      }

    }
}
</script>

<style>
.inputPassword {
  width: 300px;
  border-radius: 10px; /* 둥글게 만들기 */
  border: 3px solid #ccc; /* 테두리 추가 */
  padding: 5px; /* 내용과 테두리 사이의 여백 설정 */
}

.custom-button {
  background-color: #f2f2f2; /* 버튼 배경색 설정 */
  border: none; /* 버튼 테두리 없애기 */
  border-radius: 10px; /* 둥글게 만들기 */
  padding: 10px 20px; /* 내용과 버튼 테두리 사이의 여백 설정 */
  cursor: pointer; /* 커서를 손가락 포인터로 변경하여 클릭 가능한 것처럼 보이도록 설정 */
}

.change-button {
  background-color: #f2f2f2; /* 다른 버튼에 대한 배경색 설정 */
  /* 다른 스타일을 추가할 수 있습니다 */
}

/* 나머지 스타일은 그대로 유지합니다 */
</style>
