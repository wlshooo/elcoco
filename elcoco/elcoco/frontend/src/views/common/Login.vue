<template>
  <PageBanner/>
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-body">
              <div class="custom-box">
                <h2 class="card-title text-center"><strong>로그인</strong></h2>
                <br>
                <h2 class="card-subtitle text-center">어서 오세요! 환영합니다.</h2>
              </div>
              <br><br>
              <form @submit.prevent="fnLogin">
                <div class="form-group">
                  <label for="uid" class="col-md-4 col-form-label"><strong>아이디</strong></label>
                  <div class="input-group">
                    <input id="uid" class="form-control rounded-left" name="uid" placeholder="아이디를 입력해주세요."
                           ref="idInput" v-model="user_id">
                  </div>
                </div>
                <br>
                <div class="form-group">
                  <label for="password" class="col-form-label"><strong>비밀번호</strong></label>
                  <div class="input-group">
                    <input id="password" name="password" class="form-control rounded-left" placeholder="비밀번호를 입력해주세요."
                           ref="passwordInput" v-model="user_pw" type="password">
                  </div>
                </div>

                <br><br>
                <div class="form-group text-center mt-3">
                  <!-- 버튼 스타일 조정 -->
                  <button type="submit" class="btn btn-primary btn-block my-button"><strong>로그인</strong></button>
                </div>

                <div class="form-group text-center mt-3">
                  <router-link to="/signup">아직 회원이 아니신가요?</router-link>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  border: none;
}

.card-title {
  font-size: 24px;
}

.card-subtitle {
  font-size: 20px;
}
/* .custom-box 스타일 추가 */
.custom-box {
  background-color: #f2f2f2;
  padding: 20px;
  border-radius: 10px;
  text-align: center;
}

.btn-primary {
  background-color: #f2f2f2;
  border: none;
  color: black;
}

/* input을 둥글게 만들기 */
.input-group .form-control {
  border-radius: 25px;
  height: 45px;
  /* 아래 라인 추가 */
  width: 100%; /* 너비를 100%로 설정하여 input의 너비와 동일하게 만듭니다. */
}

/* 버튼 크기 및 폰트 크기 조정 */
.my-button {
  border-radius: 25px;
  height: 45px;
  font-size: 16px;
  /* 아래 라인 추가 */
  width: 300px;
}

</style>

<script>
import {mapActions, mapGetters} from 'vuex'
import PageBanner from "@/components/PageBanner.vue";

export default {
  components:{
    PageBanner
  },
  data() {
    return {
      user_id: '',
      user_pw: ''
    }
  },
  methods: {
    ...mapActions(['login']),
    async fnLogin() {
      if (this.user_id === '') {
        alert('아이디를 입력해주세요.')
        this.$refs.idInput.focus()
        return
      }
      if (this.user_pw === '') {
        alert('비밀번호를 입력해주세요.')
        this.$refs.passwordInput.focus()
        return
      }

      try {
        let loginResult = await this.login({
          user_id: this.user_id,
          user_pw: this.user_pw
        })
        if (loginResult) this.goToPages()
      } catch (err) {
        alert('아이디와 패스워드를 다시 확인해주세요')
        location.reload()
      }
    },
    goToPages() {
      this.$router.push({
        name: 'BoardList'
      })
    }
  },
  computed: {
    ...mapGetters({
      errorState: 'getErrorState'
    })
  }
}
</script>
