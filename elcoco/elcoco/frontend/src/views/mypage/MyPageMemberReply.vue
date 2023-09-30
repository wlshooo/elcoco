<template>
  <div class="board-list mt-5">
    <table class="table table-striped">
      <colgroup>
        <col style="width: 15%;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: 20%;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: auto;"/> <!-- 작성자 열의 너비 -->
        <col style="width: 15%;"/> <!-- 등록일시 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">게시글 번호</th>
        <th scope="col">작성자</th>
        <th scope="col">내용</th>
        <th scope="col">등록 일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="hover-pointer">
        <td>{{ item.postId }}</td>
        <td>{{ item.memberNickname }}</td>
        <td>
          <span v-if="item.content.length < 15"><b-button>{{ item.content }}</b-button></span>
          <span v-else>{{ item.content.substring(0, 15) + "..." }}</span>
        </td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      list: '',

    }
  },
  mounted() {
    this.PostList()
  },
  methods: {
    PostList() {
      this.$axios.get("/api/v1/mypage/member/reply" , {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            this.list = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
          }).catch((err) => {
        alert(err.response.data.message)
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: '/board/detail',
        query: {idx}
      })
    },
    formatDateTime(dateTimeStr) {
      const dateTime = new Date(dateTimeStr);
      const year = dateTime.getFullYear();
      const month = String(dateTime.getMonth() + 1).padStart(2, '0');
      const day = String(dateTime.getDate()).padStart(2, '0');
      const hours = String(dateTime.getHours()).padStart(2, '0');
      const minutes = String(dateTime.getMinutes()).padStart(2, '0');
      return `${year}-${month}-${day} ${hours}:${minutes}`;
    },
  },
}
</script>