<template>
  <div class="black-bg" v-if="modalOpen === true">
    <div class="white-bg">
      <h1>게시글 {{ReplyResponseDto.postId}}번 댓글 상세</h1>
          <div class="m-button-container">
            <b-button @click="modalOpen = false" class="m-modal-exit-btn">닫기</b-button>
            <button type="button" class="btn btn-danger btn-rounded" @click="fnDelete(ReplyResponseDto.replyId)">삭제</button>
          </div>
          <div class="info-row">
            <div class="info">
              <span class="m-sendName">작성자: {{ ReplyResponseDto.memberNickname }}</span>
              <span class="create-at">&nbsp&nbsp 날짜: {{ formatDateTime(ReplyResponseDto.regDate) }}</span>
            </div>
          </div>
          <hr>
          <h2>{{ ReplyResponseDto.content }}</h2>
        </div>
      </div>
  <div class="board-list mt-5">
    <table class="table table-striped">
      <colgroup>
        <col style="width: 15%;"/> <!-- No 열의 너비 -->
        <col style="width: 15%;"/> <!-- 카테고리 열의 너비 -->
        <col style="width: 20%;"/> <!-- 제목 열의 너비를 최대한 확보하고 나머지 열은 자동 조정 -->
        <col style="width: auto;"/> <!-- 작성자 열의 너비 -->
        <col style="width: 15%;"/> <!-- 등록일시 열의 너비 -->
      </colgroup>
      <thead>
      <tr>
        <th scope="col">댓글 번호</th>
        <th scope="col">게시글 번호</th>
        <th scope="col">작성자</th>
        <th scope="col">내용</th>
        <th scope="col">삭제 여부</th>
        <th scope="col">등록 일시</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="(item, idx) in list" :key="idx"  class="hover-pointer">
        <td>{{ item.replyId }}</td>
        <td>{{ item.postId }}</td>
        <td>{{ item.memberNickname }}</td>
        <td @click="modalOpen = true">
          <span v-if="item.content.length < 15"><button type="button" class="w3-button w3-round w3-blue-gray" v-on:click="clickModel(item)">
            {{ item.content }}
          </button></span>
          <span v-else>{{ item.content.substring(0, 15) + "..." }}</span>

        </td>
        <td>{{ item.deleteAt }}</td>
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
      modalOpen: false,
      requestBody: this.$route.query,
      memberId: this.$route.query.idx,
      list:'',
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
      },
      ReplyResponseDto: {
        postId: '',
        replyId: '',
        memberNickname: '',
        content: '',
        regDate: '',
      },
    }
  },
  mounted() {
    this.MemberReplyList()
  },
  methods: {
    MemberReplyList(){
      this.$axios.get("/api/v1/admin/reply/" + this.memberId, {
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
        }
      })
    },
    clickModel(item) {
      this.ReplyResponseDto.content = item.content
      this.ReplyResponseDto.replyId = item.replyId
      this.ReplyResponseDto.postId = item.postId
      this.ReplyResponseDto.memberNickname = item.memberNickname
      this.ReplyResponseDto.regDate = item.regDate
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
    fnDelete(replyId) {
      if (!confirm("댓글을 삭제 할까요?")) return

      this.$axios.delete('/api/v1/admin/reply/' + replyId, {})
          .then((res) => {
            alert(res.data.message)
            this.modalOpen = false
            location.reload()
          }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
        }
      })
    },
  },
}
</script>