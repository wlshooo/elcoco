<template>
  <br>
  <footer class="footer">
    <div class="container">
      <div class="row">
        <div class="col-md text-center">
          <p>&copy; 2023 Your Company. All rights reserved.</p>
        </div>
        <div class="col-md text-center">
          <a href="https://github.com/wlshooo" target="_blank">
            <i class="fab fa-github">이진호</i>
          </a>
        </div>
      </div>
    </div>
  </footer>
</template>

<script>
export default {}
</script>

<style scoped>
.footer {
  background-color: #F2F2F2; /* 배경색 설정 */
  color: black; /* 글자색 설정 */
  padding: 20px 0; /* 상하 여백 설정 */
}

.footer a {
  color: black; /* 링크 색상 설정 */
  text-decoration: none; /* 밑줄 제거 */
  margin-left: 10px; /* 링크 사이 간격 설정 */
}

.footer a:hover {
  text-decoration: underline; /* 호버 시 밑줄 표시 */
}

.fab.fa-github {
  color: #333; /* GitHub 아이콘 색상 설정 */
  font-size: 18px; /* 아이콘 크기 설정 */
  margin-left: 5px; /* 아이콘과 텍스트 간격 설정 */
}
</style>
