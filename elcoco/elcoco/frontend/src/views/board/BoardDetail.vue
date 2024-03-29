<template>
  <PageBanner/>
  <div class="board-detail mt-5">
    <div class="common-buttons mb-3">
      <i v-if="isAuthor()" @click="fnUpdate" class=" menu-icon fa-2x fa-solid fa-pen-to-square mouse-cursor small-icon"></i>
      <i v-if="isAuthor()" @click="fnDelete" class="menu-icon fa-2x fa-solid fa-trash mouse-cursor small-icon"></i>
      <i class="menu-icon fa-2x fa-solid fa-list" @click="fnList"></i>
    </div>
    <h1><strong>[{{ category }}] {{ title }}</strong></h1>
    <div>
      <p class="w3-large mb-3 mt-3">
        <i class="menu-icon fa-solid fa-envelope mouse-cursor" v-if="!isAuthor()" @click="toMessageWrite(author)"></i>
        <i class="menu-icon fa-solid fa-ban mouse-cursor" v-if="!isAuthor()" @click="toReportPost(postId, title)"></i>
        <span v-if="Role(memberRole)" class="red-text"><strong>{{ author }}</strong></span>
        <span v-if="!Role(memberRole)" class="black-text"><strong>{{ author }}</strong></span>
        <span class="small-font">&nbsp; {{ created_at }}</span>
        <span class="small-font">&nbsp;&nbsp; 조회수: {{ view_count }}</span>
      </p>
    </div>

    <br>
    <hr>
    <div class="board-contents">
      <div v-if="hasImages">
        <img v-for="(file, index) in imageFiles" :key="index" :src="file.fileUrl" :alt="'게시글 이미지 ' + (index + 1)" class="img-fluid" />
      </div>
      <!-- 이미지가 아닌 파일 표시 -->
      <div v-html="contentWithoutImages"></div>
    </div>

    <div class="button-container">
      <button @click="toPostLike(postId, loginUserNickname)" class="btn-liked btn btn-outline-primary btn-rounded">
        <i class="fa-3x fa-solid fa-heart heart-icon"></i>
      </button>
    </div>
    &nbsp;
    <i class="fa-solid fa-comment">{{ replyCount }}</i>
    <i class="fa-solid fa-heart">{{ likeCount }}</i>

    <div>
      <ul>
        <li v-for="file in files" :key="file.fileUrl">
          첨부파일 : <a :href="file.fileUrl" download>{{ file.fileName }}</a>
        </li>
      </ul>
    </div>

    <hr>

    <div v-for="(reply, idx) in replyList" :key="idx" class="mt-5">
      <i class="fa-solid fa-trash mouse-cursor" @click="removeReply(reply.replyId,reply.postId)"></i>
      <i class="fa-solid fa-envelope mouse-cursor" @click="toMessageWrite(reply.memberNickname)"></i>
      <i class="fa-solid fa-heart Reply-heart-icon" @click="toReplyLike(reply.replyId, loginUserNickname)"><small>&nbsp;{{ reply.likeCount }}</small></i>
      <div class="reply-detail">
        <span v-if="Role(reply.memberRole)" class="red-text"><strong>{{ reply.memberNickname }}</strong></span>
        <span v-if="!Role(reply.memberRole)" class="black-text"><strong>{{ reply.memberNickname }}</strong></span>
        <div class="create-at">
          <span>{{ formatDate(reply.regDate) }}</span>
        </div>
        <p>{{ reply.content }}</p>
      </div>
    </div>

    <div class="mt-5">
      <label for="reply"><strong>댓글</strong></label>
      <div style="position: relative;">
        <textarea id="reply" ref="replyInput" rows="5" v-model="reply" class="form-control" style="resize: none;" placeholder="댓글을 남겨보세요."></textarea>
        <button type="button" class="btn btn-outline-primary btn-rounded" @click="replySave" style="position: absolute; right: 10px; bottom: 10px;">댓글 저장</button>
      </div>
    </div>
  </div>
</template>


<style scoped>
.small-font {
  font-size: 0.8rem;
  color: gray;
}
.small-icon {
  font-size: 20px; /* Adjust the font size to your preference */
}

.mouse-cursor {
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
}
/* 하트 아이콘 스타일링 */
.heart-icon {
  color: red; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  font-size: 3rem; /* 원하는 크기로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
}
.Reply-heart-icon {
  color: #fd8181; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
  font-size: 20px;
  vertical-align: -10px;
}
.menu-icon {
  color: black; /* 원하는 색상(빨간색 또는 다른 원하는 색상)으로 설정합니다. */
  font-size: 2rem; /* 원하는 크기로 설정합니다. */
  cursor: pointer; /* 마우스 포인터가 포인팅 형태로 변경됩니다. */
}

/* 하트 아이콘과 버튼 컨테이너 스타일 */
.button-container {
  display: flex;
  justify-content: center;
  align-items: center; /* 수직 정렬 추가 */
  padding: 10px; /* 원하는 패딩 값으로 조정 */
}

/* 좋아요 버튼 스타일 */
.btn {
  margin: 0 auto; /* 가운데 정렬 */
  /* 나머지 스타일 유지 */
}

/* 좋아요 버튼 스타일 */
.btn-liked {
  background-color: transparent; /* 배경색을 투명으로 설정합니다. */
  border: none; /* 테두리 제거 */
}
/* 이미지가 내용의 최대 높이를 넘지 않도록 스타일링 */

/* 게시글 내용 스타일링 */
.board-contents {
  position: relative;
  height: auto; /* 내용의 크기에 따라 늘어남 */
  overflow: hidden; /* 내용이 특정 크기를 넘어갈 경우 스크롤 표시 */
}

/* 이미지 스타일 유지 */
.img-fluid {
  display: block;
  margin: 0 auto; /* 가운데 정렬 */
  max-width: 100%; /* 이미지의 최대 너비 설정 */
  height: auto; /* 이미지의 높이 자동 조정 */
  object-fit: contain; /* 이미지 비율 유지 */
}
/* 빨간색 텍스트 색상 */
.red-text {
  color: red;
}

/* 기본 텍스트 색상 (검은색) */
.black-text {
  color: black;
}
</style>


<script>
import PageBanner from "@/components/PageBanner.vue";

export default {
  components: {
    PageBanner
  },
  data() { //변수생성
    return {
      requestBody: this.$route.query,
      idx: this.$route.query.idx,
      postId: '',
      category: '',
      title: '',
      author: '',
      content: '',
      created_at: '',
      view_count: '',
      files: [],
      memberRole:'',
      //댓글
      replyCount: '',
      replyList: [],
      reply: '',
      replyAuthorNickname: '',
      // 좋아요
      loginUserNickname: localStorage.getItem('user_nickname'),
      isPostLiked: false,
      isReplyLiked: false,
      likeCount: 0,
    }
  },
  mounted() {
    this.fnGetView()
    this.fnGetReply()
  },
  computed: {
    hasImages() {
      return this.imageFiles.length > 0; // 이미지 파일이 하나 이상 있는지 확인
    },
    imageFiles() {
      return this.files.filter(this.isImageFile); // 이미지 파일만 필터링
    },
    nonImageFiles() {
      return this.files.filter(file => !this.isImageFile(file)); // 이미지가 아닌 파일만 필터링
    },
    contentWithoutImages() {
      // 이미지를 제외한 내용을 반환
      return this.content.replace(/!\[.*?\]\(.*?\)/g, ''); // 이미지 링크 제거
    },
  },
  methods: {
    formatDate(dateString) {
      const options = {year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric'};
      return new Date(dateString).toLocaleString(undefined, options);
    },

    fnGetView() {
      this.$axios.get('/api/v1/post/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        this.postId = res.data.data.postId
        this.title = res.data.data.title
        this.author = res.data.data.memberNickname
        this.content = res.data.data.content
        this.created_at = this.formatDate(res.data.data.regDate)
        this.category = res.data.data.postCategory
        this.view_count = res.data.data.viewCount
        this.memberRole = res.data.data.memberRole
        // 서버에서 게시물의 좋아요 상태와 개수 가져오기
        // this.isLiked = res.data.data.isLiked;
        this.replyCount = res.data.data.replyCount;
        this.likeCount = res.data.data.likeCount;
        //파일 가져오기
        this.files = res.data.data.fileFormat
      }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          this.$router.push({});
        }
      })
    },

    fnList() {
      delete this.requestBody.idx
      // this.$router.go(-1);
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnPost(postId) {
      this.requestBody.idx = postId
      this.$router.go(this.$router.currentRoute)
    },
    fnUpdate() {
      this.$router.push({
        path: './write',
        query: this.requestBody
      })
    },
    fnDelete() {
      if (!confirm("게시글을 삭제하시겠습니까?")) return

      this.$axios.delete('/api/v1/post/' + this.idx, {})
          .then((res) => {
            alert(res.data.message)
            this.fnList();
          }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          this.$router.push({});
        }
      })
    },
    isImageFile(file) {
      // 파일이 이미지 파일인지 확인 (확장자 기준)
      const imageExtensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp', '.svg'];
      const fileExtension = file.fileName.split('.').pop().toLowerCase();
      return imageExtensions.includes('.' + fileExtension);
    },


    fnGetReply() {
      this.$axios.get('/api/v1/reply/' + this.idx, {
        params: this.requestBody
      }).then((res) => {
        this.replyList = res.data.data
        this.replyAuthorNickname = res.data.data.memberNickname
      }).catch((err) => {
        alert(err.response.data.message)
        location.reload()
      })
    },
    removeReply(replyId, postId) {
      if (!confirm("댓글을 삭제하시겠습니까?")) return
      this.$axios.delete(`/api/v1/reply/${postId}/${replyId}`, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      }).then((res) => {
        alert(res.data.message)
        this.fnPost(postId);
      }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          this.$router.push({});
        }
      })
    },
    replySave() {
      if (!this.reply) {
        alert("댓글을 입력해주세요.");
        this.$refs.replyInput.focus();
        return;
      }

      this.form = {
        "content": this.reply
      }
      console.log(this.form.content)
      //INSERT
      this.$axios.post(`/api/v1/reply/` + this.idx, this.form, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('user_token')}`
        }
      })
          .then((res) => {
            alert(res.data.message)
            this.fnPost(this.idx);
          }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({path: '/login'});
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },
    toMessageWrite(receiverNickname) {
      this.$router.push({
        path: '/message/write',
        query: {name: receiverNickname}
      })
    },
    toReportPost(postId, title) {
      this.$router.push({
        path: '/report/write',
        query: {
          reportPost: postId,
          postTitle: title,
        }

      })
    },
    toPostLike(postId, loginNickname) {
      // 좋아요 상태 토글
      this.isPostLiked = !this.isPostLiked;

      this.$axios.post(`/api/v1/post/like/${postId}/${loginNickname}`, {like: this.isPostLiked})

          .then(() => {
            // if (this.isLiked) {
            //   this.likeCount++;
            // } else {
            //   this.likeCount--;
            // }
            alert("좋아요!");
            location.reload();
          })
          .catch((err) => {
            if (err.response.status === 401 || err.response.status === 404) {
              this.$router.push({ path: '/login' });
            } else {
              alert(err.response.data.message);
            }
            this.$store.state.loadingStatus = false;
          });
    },
    toReplyLike(replyId, loginNickname) {
      // 좋아요 상태 토글
      this.isReplyLiked = !this.isReplyLiked;

      this.$axios.post(`/api/v1/reply/like/${replyId}/${loginNickname}`, {like: this.isReplyLiked})

          .then(() => {
            // if (this.isLiked) {
            //   this.likeCount++;
            // } else {
            //   this.likeCount--;
            // }
            alert("좋아요!");
            location.reload()
          })
          .catch((err) => {
            if (err.response.status === 401 || err.response.status === 404) {
              this.$router.push({ path: '/login' });
            } else {
              alert(err.response.data.message);
            }
            this.$store.state.loadingStatus = false;
          });
    },
    isAuthor() {
      if (localStorage.getItem("user_nickname") === this.author) {
        return true;
      } else return false;
    },
    isReplyAuthor() {
      console.log("====" + this.replyAuthorNickname);
      if (localStorage.getItem("user_nickname") === this.replyAuthorNickname) {
        return true;
      } else return false;
    },
    Role(memberRole) {
      if (memberRole === "ROLE_ADMIN") {
        return true;
      } else return false;
    }
  }
}
</script>
