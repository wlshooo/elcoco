<template>
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <h4 class="text-center mb-4"><strong>회원가입</strong></h4>
          <hr/>
          <b-form @submit.prevent="onSubmit">
            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right">이메일</label>
              <div class="col-md-8">
                <b-form-input v-model="form.email" type="email" id="input-1" placeholder="이메일을 입력해주세요"
                              ref="emailInput" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right">이름</label>
              <div class="col-md-8">
                <b-form-input v-model="form.name" type="text" id="input-2" placeholder="이름을 입력해주세요"
                              ref="nameInput" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right">비밀번호</label>
              <div class="col-md-8">
                <b-form-input v-model="form.password" type="password" id="input-3"
                              ref="passwordInput" placeholder="비밀번호를 입력해주세요" required></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right">닉네임</label>
              <div class="col-md-8">
                <b-form-input v-model="form.nickname" type="text" id="input-4" placeholder="닉네임을 입력해주세요"
                              ref="nicknameInput" required></b-form-input>
              </div>
            </div>

            <hr>

            <div class="text-center">
              <b-button type="button" @click="onSubmit" variant="primary" style="margin-right: 10px;">
                가입하기
              </b-button>
              <b-button type="reset" variant="danger">초기화</b-button>
            </div>

          </b-form>
        </div>
      </div>
    </div>
  </div>

</template>

<script>

export default {
  data() {
    return {
      form: {
        email: '',
        password: '',
        name: '',
        nickname: '',
        role: 'USER',
      }
    }
  },
  methods: {
    onSubmit() {
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.form.name) {
        alert("이름을 입력해주세요.");
        this.$refs.nameInput.focus();
        return;
      }
      if (!this.form.password) {
        alert("비밀번호를 입력해주세요.");
        this.$refs.passwordInput.focus();
        return;
      }
      if (!this.form.nickname) {
        alert("닉네임을 입력해주세요.");
        this.$refs.nicknameInput.focus();
        return;
      }

      this.$axios.post("/api/v1/member", this.form)
          .then((res) => {
            alert(res.data.message)
            this.$router.push({name: 'Login'})
          }).catch((err) => {
        alert(err.response.data.message);
        return;
      })
    }
  }
}
</script>

<style scoped>

</style>
