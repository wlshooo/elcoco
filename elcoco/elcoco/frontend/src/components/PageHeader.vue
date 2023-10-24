<template>
  <header class="header header-background">
    <div id="nav" class="navbar navbar-expand-lg navbar-light ">
      <div class="container">
        <router-link to="/" class="navbar-brand">ElCOCO</router-link>
        <button
            class="navbar-toggler"
            type="button"
            data-toggle="collapse"
            data-target="#navbarNav"
            aria-controls="navbarNav"
            aria-expanded="false"
            aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
          <ul class="navbar-nav">
            <li class="nav-item">
              <router-link to="/board/list" v-if="this.$store.state.isLogin" class="nav-link">게시판</router-link>
            </li>
            <li class="nav-item">
              <span class="nav-link"  v-if="this.$store.state.isLogin" @click="toggleLectureMenu"><strong>강의 게시판</strong>
                <ul class="dropdown-menu" :class="{ show: isLectureMenuOpen }">
                  <li class="nav-item">
                    <router-link to="/lecture/1st-year" class="nav-link">1학년</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link to="/lecture/2nd-year" class="nav-link">2학년</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link to="/lecture/3rd-year" class="nav-link">3학년</router-link>
                  </li>
                  <li class="nav-item">
                    <router-link to="/lecture/4th-year" class="nav-link">4학년</router-link>
                  </li>
                </ul>
              </span>
            </li>
            <li class="nav-item">
              <router-link to="/admin/main" v-if="Role()" class="nav-link">관리자 페이지</router-link>
            </li>
          </ul>
        </div>

        <div>
          <router-link to="/mypage" v-if="this.$store.state.isLogin" class="no-underline" style="margin-right: 10px;">
            마이페이지
          </router-link>
        </div>

        <div class="d-flex">
          <router-link to="/signup" v-if="!this.$store.state.isLogin" class="nav-link" style="margin-right: 10px;">회원 가입
          </router-link>
          <router-link to="/login" v-if="!this.$store.state.isLogin" class="no-underline" style="margin-right: 10px;">
            로그인
          </router-link>
          <router-link to="/login" v-if="this.$store.state.isLogin" @click="fnLogout" class="no-underline">로그아웃
          </router-link>
        </div>
      </div>
    </div>
  </header>
</template>

<script>
export default {
  data() {
    return {
      isLectureMenuOpen: false,
      role: localStorage.getItem("user_role")
    }
  },
  methods: {
    toggleLectureMenu() {
      this.isLectureMenuOpen = !this.isLectureMenuOpen;
    },
    fnLogout() {
      localStorage.removeItem("user_token")
      localStorage.removeItem("user_role")
      localStorage.removeItem("user_nickname")
      localStorage.removeItem('user_expiration');
      location.reload();
      this.$router.push({
        path: '/login',
      })
    },
    Role() {
      if (localStorage.getItem("user_role") === "ROLE_ADMIN") {
        return true;
      } else return false;
    },
  }
}
</script>

<style scoped>
.no-underline {
  text-decoration: none !important;
}
.header-background {
  background-color: #F2F2F2;
}
</style>
