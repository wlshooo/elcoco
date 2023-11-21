<template>
  <div class="board-list mt-5">
    <table class="table table-striped">
      <colgroup>
        <col style="width: 5%;"/> <!-- No 열의 너비 -->
        <col style="width: 10%;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: auto;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: 15%;"/> <!-- 작성자 열의 너비 -->
        <col style="width: 20%;"/> <!-- 등록일시 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">No</th>
        <th scope="col">카테고리</th>
        <th scope="col">제목</th>
        <th scope="col">삭제 여부</th>
        <th scope="col">작성자</th>
        <th scope="col">등록일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="hover-pointer">
        <td>{{ item.postId }}</td>
        <td>{{ item.postCategory }}</td>
        <td>
          <span v-if="item.title.length < 15"><b-button>{{ item.title }}</b-button></span>
          <span v-else>{{ item.title.substring(0, 15) + "..." }}</span>
        </td>
        <td>{{ item.deleteAt }}</td>
        <td>{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>

      </tr>
      </tbody>
    </table>
    <hr>
  </div>
</template>

<script>
export default {
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      memberId: this.$route.query.idx,
      list: '',
      no: '', //게시판 숫자처리
      paging: {
        block: 0,
        end_page: 0,
        next_block: 0,
        page: 0,
        page_size: 0,
        prev_block: 0,
        start_index: 0,
        start_page: 0,
        total_block_cnt: 0,
        total_list_cnt: 0,
        total_page_cnt: 0,
      }, //페이징 데이터
      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,
      keyword: this.$route.query.keyword,
      paginavigation: function () { //페이징 처리 for문 커스텀
        let pageNumber = [] //;
        let start_page = this.paging.start_page;
        let end_page = this.paging.end_page;
        for (let i = start_page; i <= end_page; i++) pageNumber.push(i);
        return pageNumber;
      }
    }
  },
  mounted() {
    this.MemberPostList()
  },
  methods: {
    MemberPostList() {
      this.$axios.get("/api/v1/admin/member-post/" + this.memberId, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            this.list = res.data.data  //서버에서 데이터를 목록으로 보내므로 바로 할당하여 사용할 수 있다.
          }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    fnView(idx) {
      this.requestBody.postId = idx
      this.$router.push({
        path: '/admin/member/post/detail',
        query: this.requestBody
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