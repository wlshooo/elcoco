<template>
  <div class="board-list mt-3">
    <h2 class="title">내가 좋아요 누른 게시물</h2>
    <hr><br>
    <table class="table table-striped">
      <colgroup>
        <col style="width: 5%;"/> <!-- No 열의 너비 -->
        <col style="width: 10%;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: auto;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: 15%;"/> <!-- 작성자 열의 너비 -->
        <col style="width: 15%;"/> <!-- 등록일시 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">No</th>
        <th scope="col">카테고리</th>
        <th scope="col">제목</th>
        <th scope="col">작성자</th>
        <th scope="col">작성일시</th>
      </tr>
      </thead>
      <tbody>

      <tr v-for="(item, idx) in likeBoardList" :key="idx" @click="fnBoardView(item.postId)" class="hover-pointer">
        <td>{{ item.postId }}</td>
        <td>{{ item.postCategory }}</td>
        <td>
              <span v-if="item.title.length < 10">{{ item.title }} &nbsp;&nbsp;
                <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
                <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
              </span>
          <span v-else>{{ item.title.substring(0, 10) + "..." }}
                <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
                <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
              </span>
        </td>
        <td>{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>

      </tbody>
    </table>
  </div>
</template>

<script>
export default {

  data() {
    return {
      requestBody: {}, // 리스트 페이지 데이터전송
      likeBoardList: {}, // 내가 좋아요 누른 게시물 리스트
      memberNickname: localStorage.getItem('user_nickname'),
    }
  },

  mounted() {
    this.fnLikeBoardList();
  },

  methods: {
    fnLikeBoardList() {
      this.$axios.post(`/api/v1/posts/like/member/${this.memberNickname}`)
          .then((res) => {
            this.likeBoardList = res.data.data;
          }).catch((err) => {
        if (err.response.status === 401 || err.response.status === 404) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },

    fnBoardView(idx) {
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

  }

};
</script>

<style>

.title {
  font-size: 35px;
  color: lightgray;
}

.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.hover-pointer:hover {
  background-color: blue; /* 마우스 호버 시 원하는 배경색으로 변경하세요. */
}
</style>