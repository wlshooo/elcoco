<template>
  <div class="background">
    <PageBanner/>
  <div class="board-list mt-5">
    <div class="nav-buttons mb-3">
      <button :class="{ active: postCategory === '' }" class="btn btn-link" @click="fnSelectCategory('')">
        전체글
      </button>
      <button :class="{ active: postCategory === 'NOTICE' }" class="btn btn-link" @click="fnSelectCategory('NOTICE')">
        공지사항
      </button>
      <button :class="{ active: postCategory === 'QNA' }" class="btn btn-link" @click="fnSelectCategory('QNA')">
        질문
      </button>
      <button :class="{ active: postCategory === 'INFO' }" class="btn btn-link" @click="fnSelectCategory('INFO')">
        정보
      </button>
      <button :class="{ active: postCategory === 'FREE' }" class="btn btn-link" @click="fnSelectCategory('FREE')">
        자유
      </button>
      <button :class="{ active: postCategory === 'GW' }" class="btn btn-link" @click="fnSelectCategory('GW')">
        졸업작품
      </button>
    </div>
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
        <th scope="col">등록일시</th>
      </tr>
      </thead>
      <tbody>
      <!--공지사항 상시 상단 고정-->
      <tr v-for="(item, idx) in noticeList" :key="idx" @click="fnView(item.postId)" class="hover-pointer notice-post">
        <td >{{ item.postId }}</td>
        <td class="red-text"> {{ item.postCategory }}</td>
        <td>
          <span v-if="item.title.length < 10" >{{ item.title }} &nbsp;&nbsp;
            <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
            <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
          </span>
          <span v-else >{{ item.title.substring(0, 10) + "..." }}
            <i class="fa-solid fa-comment small-icon">{{ item.replyCount }}</i>
            <i class="fa-solid fa-heart small-icon">{{ item.likeCount }}</i>
          </span>
        </td>
        <td v-if="Role(item.memberRole)" class="red-text">{{ item.memberNickname }}</td>
        <td v-if="!Role(item.memberRole)" class="black-text">{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>

      <tr v-for="(item, idx) in list" :key="idx" @click="fnView(item.postId)" class="hover-pointer">
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
        <td v-if="Role(item.memberRole)" class="red-text">{{ item.memberNickname }}</td>
        <td v-if="!Role(item.memberRole)" class="black-text">{{ item.memberNickname }}</td>
        <td>{{ formatDateTime(item.regDate) }}</td>
      </tr>
      </tbody>
    </table>

    <div class="common-buttons mb-3">
      <button type="button" class="btn btn-outline-dark btn-rounded small-button" @click="fnWrite">
        <span><i class="fa-solid fa-pen small-icon"></i></span>
        <!--글쓰기-->
      </button>
    </div>

    <div class="pagination d-flex justify-content-center">
      <ul class="pagination">
        <li class="page-item">
          <a class="page-link" @click="fnPage(0)" href="javascript:;">&lt;&lt;</a>
        </li>
        <li class="page-item">
          <a class="page-link" @click="fnPage(page - 10)" href="javascript:;">&lt;</a>
        </li>
        <li class="page-item" v-for="n in paginavigation()" :key="n">
          <a class="page-link" @click="fnPage(n)" href="javascript:;">{{ n + 1 }}</a>
        </li>
        <!-- ">" 버튼은 다음 그룹으로 이동 -->
        <li class="page-item">
          <a class="page-link" @click="nextGroup" href="javascript:;" v-if="!isLastGroup">></a>
        </li>
        <!-- ">>" 버튼은 마지막 페이지로 이동 -->
        <li class="page-item">
          <a class="page-link" @click="fnPage(totalPage - 1)" href="javascript:;">&gt;&gt;</a>
        </li>
      </ul>
    </div>
    <hr>

    <div class="form-group row align-items-center">
      <div class="col-md-2">
        <b-form-select v-model="searchType" :options="selectedOption" id="searchType"></b-form-select>
      </div>
      <div class="col-md">
        <input type="text" class="form-control" id="searchText" v-model="searchText" placeholder="검색어를 입력하세요"/>
      </div>
      <div class="col-md-2">
        <button type="submit" class="btn btn-primary btn-rounded" @click="fnSearch(searchType, searchText)">검색
        </button>
      </div>
    </div>
  </div>
  </div>
</template>

<style scoped>
.hover-pointer {
  cursor: pointer;
  transition: background-color 0.3s ease-in-out;
}

.small-icon {
  font-size: 15px; /* Adjust the font size to your preference */
}

.small-button {
  font-size: 12px; /* Adjust the font size for the button text */
  padding: 4px 8px; /* Adjust padding for the button */
}

/*TODO : 마우스 hover 시 테이블 열 색이 바뀌도록, 현재는 적용이 안됨 */
.hover-pointer:hover {
  background-color: blue; /* 마우스 호버 시 원하는 배경색으로 변경하세요. */
}

.notice-post {
  font-weight: bold;
}

.nav-buttons {
  display: flex;
  gap: 10px;
}

.btn {
  text-decoration: none;
  background-color: #f2f2f2;
  color: black;
}

.btn.active {
  background-color: #f2f2f2;
  color: black;
  border-color: darkblue;
}

/* 빨간색 텍스트 색상 */
.red-text {
  color: red;
}

/* 기본 텍스트 색상 (검은색) */
.black-text {
  color: black;
}
.background{
  background: #f2f2f2;
}

</style>

<script>

import PageBanner from "@/components/PageBanner.vue";

export default {
  components:{
    PageBanner
  },
  data() {
    return {
      requestBody: {}, //리스트 페이지 데이터전송
      list: {}, //리스트 데이터
      noticeList: {},
      postCategory: '',
      replyCount: '',
      likeCount: '',
      searchType: 'title',
      searchText: '',
      totalPage: '',
      selectedOption: [
        {value: 'title', text: '제목'},
        {value: 'writer', text: '작성자'},
        {value: 'titleAndWriter', text: '제목 + 작성자'},
      ],

      // URL에서 page, size 정보를 가져와서 저장
      page: this.$route.query.page ? this.$route.query.page : 0,
      size: this.$route.query.size ? this.$route.query.size : 10,

      paginavigation() {
        let pageNumber = [];
        let start_page = Math.floor(this.page / 10) * 10; // 현재 페이지 그룹의 시작 페이지
        let end_page = Math.min(start_page + 10, this.totalPage); // 현재 페이지 그룹의 끝 페이지, 최대 10개까지 표시
        for (let i = start_page; i < end_page; i++) pageNumber.push(i);
        return pageNumber;
      },
    }
  },
  mounted() {
    const postCategoryFromURL = this.$route.query.postCategory;

    if (postCategoryFromURL) {
      this.postCategory = postCategoryFromURL;
    }
    this.fnGetList();
    this.fnGetNoticeList();
  },
  methods: {
    fnGetNoticeList() {
      this.$axios.get("/api/v1/posts/notice", {}).then((res) => {
        console.log(res);
        this.noticeList = res.data.data;
      }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          location.reload()
        }
      })
    },

    fnGetList() {
      this.requestBody = {
        page: this.page,
        size: this.size,
        totalPage: this.totalPage,
        postCategory: this.postCategory,
        searchText: this.searchText,
        searchType: this.searchType,
      }
      this.$axios.get("/api/v1/posts", {
        params: this.requestBody,
      }).then((res) => {
        console.log(res);
        this.page = res.data.data.number;
        this.size = res.data.data.size;
        this.totalPage = res.data.data.totalPages;
        this.list = res.data.data.content;
        this.replyCount = res.data.data.replyCount;
        this.likeCount = res.data.data.likeCount;
      }).catch((err) => {
        if (err.response.status === 401) {
          this.$router.push({ path: '/login' });
        } else {
          alert(err.response.data.message);
          this.$router.push({});
        }
      })
    },
    fnView(idx) {
      this.requestBody.idx = idx
      this.$router.push({
        path: './detail',
        query: {idx}
      })
    },
    fnWrite() {
      this.$router.push({
        path: './write'
      })
    },
    fnList() {
      delete this.requestBody.idx
      this.$router.push({
        path: './list',
        query: this.requestBody
      })
    },
    fnPage(n) {
      if (this.page !== n) {
        if (this.totalPage > n && n >= 0) {
          this.page = n;
          this.fnGetList()
        }
      }
    },
    fnSelectCategory(postCategory) {
      this.postCategory = postCategory;
      this.page = 0;
      this.$router.push({path: this.$route.path, query: {postCategory}});
      this.fnGetList();
    },
    fnSearch(searchType, searchText) {
      this.searchText = searchText;
      this.searchType = searchType;
      this.page = 0;
      this.$router.push({
        path: this.$route.path,
        query: {
          searchType: searchType,
          searchText: searchText
        }
      });
      this.fnGetList();
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
    Role(memberRole)
    {
      if (memberRole === "ROLE_ADMIN") {
        return true;
      }
      else return false;
    }
  }
}
</script>
