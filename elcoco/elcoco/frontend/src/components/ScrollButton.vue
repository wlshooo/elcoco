<template>
  <div class="scroll-button-container">
    <button class="btn btn-outline-dark" @click="scrollToTop">▲</button>&nbsp;
    <button class="btn btn-outline-dark" @click="scrollToBottom">▼</button>
  </div>
</template>

<script>
export default {
  methods: {
    scrollToTop() {
      window.scrollTo({ top: 0, behavior: 'smooth' });
    },
    scrollToBottom() {
      window.scrollTo({ top: document.documentElement.scrollHeight, behavior: 'smooth' });
    }
  }
}
</script>

<style scoped>
/* 스크롤 버튼 컨테이너 스타일 */
.scroll-button-container {
  position: fixed;
  z-index: 999; /* 다른 요소 위에 표시하기 위한 z-index 설정 */
  top: 850px; /* 상단 여백 조절 */
  right: 100px; /* 오른쪽 여백 조절 */
  display: flex; /* 버튼을 수직으로 배치하기 위해 flex를 사용합니다. */
  flex-direction: column; /* 버튼을 세로로 배치합니다. */
}

/* 버튼 스타일 */
button {
  cursor: pointer;
  display: block; /* 버튼을 블록 요소로 변경하여 수직으로 정렬합니다. */
  margin-bottom: 0px; /* 버튼 사이의 간격을 조절합니다. */
}
</style>