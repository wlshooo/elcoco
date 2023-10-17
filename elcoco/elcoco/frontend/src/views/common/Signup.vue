<template>
  <PageBanner />
  <div>
    <div class="container my-5">
      <div class="row justify-content-center">
        <div class="col-md-6">
          <h4 class="text-center mb-4"><strong>회원가입</strong></h4>
          <hr />
          <b-form @submit.prevent="onSubmit">
            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right"
              >이메일 </label
              >
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input
                      v-model="form.email"
                      type="text"
                      id="input-1"
                      placeholder="이메일을 입력해주세요"
                      ref="emailInput"
                      required
                      style="width: 50%"
                  ></b-form-input>
                  <div class="input-group-append" style="width: 30%">
                    <span class="input-group-text">@skuniv.ac.kr</span>
                  </div>
                  <div style="width: 20%">
                    <b-button
                        type="button"
                        @click="sendEmailVerification"
                        variant="primary"
                    >
                      인증
                    </b-button>
                  </div>
                </div>
                <small class="form-text text-info">
                  @skuniv.ac.kr를 제외한 이메일을 입력해주세요.
                </small>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-1" class="col-md-4 col-form-label text-md-right"
              >인증 번호</label
              >
              <div class="col-md-8">
                <div class="input-group">
                  <b-form-input
                      v-model="code"
                      type="text"
                      id="input-1"
                      placeholder="인증번호를 입력해주세요"
                      ref="codeInput"
                      required
                      style="width: 50%"
                  ></b-form-input>

                  <div style="width: 20%">
                    <b-button
                        type="button"
                        @click="checkEmailVerification"
                        variant="primary"
                    >
                      확인
                    </b-button>
                  </div>
                </div>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-2" class="col-md-4 col-form-label text-md-right"
              >이름</label
              >
              <div class="col-md-8">
                <b-form-input
                    v-model="form.name"
                    type="text"
                    id="input-2"
                    placeholder="이름을 입력해주세요"
                    ref="nameInput"
                    required
                ></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-3" class="col-md-4 col-form-label text-md-right"
              >비밀번호</label
                >
              <div class="col-md-8">
                <b-form-input
                    v-model="form.password"
                    type="password"
                    id="input-3"
                    ref="passwordInput"
                    placeholder="비밀번호를 입력해주세요"
                    required
                ></b-form-input>
              </div>
            </div>

            <div class="form-group row mb-3">
              <label for="input-4" class="col-md-4 col-form-label text-md-right"
              >닉네임</label
              >
              <div class="col-md-8">
                <b-form-input
                    v-model="form.nickname"
                    type="text"
                    id="input-4"
                    placeholder="닉네임을 입력해주세요"
                    ref="nicknameInput"
                    required
                ></b-form-input>
              </div>
            </div>

            <hr />

            <div class="text-center">
              <b-button
                  type="button"
                  @click="onSubmit"
                  variant="primary"
                  style="margin-right: 10px"
              >
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
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner,
  },
  data() {
    return {
      form: {
        email: "",
        password: "",
        name: "",
        nickname: "",
        role: "USER",
      },
      code: '',
      isCheck: false,
    };
  },
  methods: {
    onSubmit() {
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.code) {
        alert("인증번호를 입력해주세요.");
        this.$refs.codeInput.focus();
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
      if (this.form.email && this.form.email.includes('@')) {
        alert("이메일 형식이 올바르지 않습니다. @skuniv.ac.kr를 제외한 이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      if (this.isCheck === false) {
        alert("이메일 인증이 완료되지 않았습니다.")
        this.$refs.codeInput.focus();
        return;
      }

      this.$axios
          .post("/api/v1/member", this.form)
          .then((res) => {
            alert(res.data.message);
            this.$router.push({ name: "Login" });
          })
          .catch((err) => {
            alert(err.response.data.message);
            return;
          });
    },
    sendEmailVerification() {
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }

      // Axios 스타일로 이메일 인증번호 발송 요청
      this.$axios
          .post("/api/v1/emails/verification-requests", null,{
            params: {
              email: this.form.email
            }
          }).then(() => {
        alert("인증번호를 이메일로 발송했습니다.");
      })
          .catch((err) => {
            alert(err.response.data.message);
            location.reload()
          });
    },
    checkEmailVerification() {
      if (!this.code) {
        alert("인증번호를 입력해주세요");
        this.$refs.emailInput.focus();
        return;
      }
      if (!this.form.email) {
        alert("이메일을 입력해주세요.");
        this.$refs.emailInput.focus();
        return;
      }
      // Axios 스타일로 이메일 인증번호 발송 요청
      this.$axios
          .post("/api/v1/emails/verification", null,{
            params: {
              email: this.form.email,
              code: this.code
            }
          }).then((res) => {
        if (res.data.data) {
          this.isCheck = true
          alert("인증이 완료되었습니다.");
        } else {
          alert("인증에 실패하였습니다.")
        }
      })
          .catch(() => {
            alert("인증에 실패하였습니다.");
          });
    },
  },
};
</script>
