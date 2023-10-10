<template>
  <div class="quick-menu" :class="{ 'fixed': isFixed }">
    <div class="quick-menu-links">
      <h3 class="quick-menu-title">QuickMenu</h3>
      <hr>
      <a class="quick-menu-link" href="https://www.skuniv.ac.kr/main" target="_blank">
        <img class="quick-menu-img" src="/skuhomepage.png" height="100" width="100"/>
      </a>
      <a class="quick-menu-link" href="https://library.skuniv.ac.kr/#/" target="_blank">
        <img class="quick-menu-img" src="/skulibrary.png" height="100" width="100"/>
      </a>
      <a class="quick-menu-link" href="https://sportal.skuniv.ac.kr" target="_blank">
        <img class="quick-menu-img" src="/skupotal.png" height="100" width="100"/>
      </a>
      <a class="quick-menu-link" href="https://en.skuniv.ac.kr/" target="_blank">
        <img class="quick-menu-img" src="/skuelco.png" height="100" width="100"/>
      </a>
      <!-- 원하는 이미지 및 경로를 사용하여 각 링크를 대체하세요 -->
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isFixed: false,
    };
  },
  mounted() {
    // 스크롤 이벤트를 감지하여 Quick Menu를 고정 또는 해제합니다.
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    // 컴포넌트가 파괴되기 전에 스크롤 이벤트 리스너를 제거합니다.
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    handleScroll() {
      // 스크롤 위치를 확인하여 Quick Menu를 고정 또는 해제합니다.
      const scrollY = window.scrollY;
      if (scrollY > 100) { // 100px 스크롤되면 고정
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }
    },
  },
};
</script>

<style scoped>
.quick-menu {
  position: fixed;
  top: 40%;
  right: 60px;
  transform: translateY(-50%);
  z-index: 1000;
  width: 120px;
  background-color: #fff;
  border: 5px solid #BBDEFB;
  border-radius: 5px;
  box-shadow: 15px 10px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */

  /* 다른 스타일 속성들... */
}

.quick-menu-title {
  font-size: 1.5rem;
  margin: 0;
  padding: 0;
  color: #333;
  text-align: center;
}

.quick-menu-links {
  display: flex;
  flex-direction: column;
  align-items: flex-end;
}

.quick-menu-link {
  margin: 10px 0;
  padding: 5px;
  text-decoration: none;
  color: #BBDEFB;
  box-shadow: 0 10px 5px rgba(0, 0, 0, 0.1); /* 그림자 효과 추가 */

  /* 다른 스타일 속성들... */
}

.quick-menu-img {
  display: block;
  max-width: 100%;
  border: 5px solid #BBDEFB;
  border-radius: 5px;
}

.fixed {
  /* Quick Menu가 고정되었을 때의 스타일을 설정하세요 */
}
</style>
