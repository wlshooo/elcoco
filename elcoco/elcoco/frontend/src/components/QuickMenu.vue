<template>
  <div class="quick-menu" :class="{ 'fixed': isFixed }">
    <div class="quick-menu-header" @click="toggleDropdown">
      <h3 class="quick-menu-title">QuickMenu</h3>
    </div>
    <div  v-show="isDropdownOpen">
      <hr>
      <a class="quick-menu-link" href="https://www.skuniv.ac.kr/main" target="_blank">
        <img class="quick-menu-img" src="/skuhomepage.png" height="100" width="100" />
      </a>
      <a class="quick-menu-link" href="https://library.skuniv.ac.kr/#/" target="_blank">
        <img class="quick-menu-img" src="/skulibrary.png" height="100" width="100" />
      </a>
      <a class="quick-menu-link" href="https://sportal.skuniv.ac.kr" target="_blank">
        <img class="quick-menu-img" src="/skupotal.png" height="100" width="100" />
      </a>
      <a class="quick-menu-link" href="https://en.skuniv.ac.kr/" target="_blank">
        <img class="quick-menu-img" src="/skuelco.png" height="100" width="100" />
      </a>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isFixed: false,
      isDropdownOpen: false,
    };
  },
  methods: {
    toggleDropdown() {
      this.isDropdownOpen = !this.isDropdownOpen;
    },
    handleScroll() {
      const scrollY = window.scrollY;
      if (scrollY > 100) {
        this.isFixed = true;
      } else {
        this.isFixed = false;
      }
    },
  },
  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeUnmount() {
    window.removeEventListener('scroll', this.handleScroll);
  },
};
</script>

<style scoped>
.quick-menu {
  position: fixed;
  top: 50%;
  right: 60px;
  transform: translateY(-50%);
  z-index: 1000;
  width: 120px;
  background-color: #fff;
  border: 1px solid #d3d3d3;
  border-radius: 5px;
  box-shadow: 15px 10px 5px rgba(0, 0, 0, 0.1);
}

.quick-menu-title {
  font-size: 1.5rem;
  margin: 0;
  padding: 0;
  color: #333;
  text-align: center;
  cursor: pointer;
}

.quick-menu-header {
  cursor: pointer;
}

.fixed {
  /* Quick Menu가 고정되었을 때의 스타일을 설정하세요 */
}
</style>
