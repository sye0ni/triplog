<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { listArticle } from "@/api/boardQnA";

import BoardQnaListItem from "./item/BoardQnaListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";
import VSelect from "@/components/common/VSelect.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "글번호", value: "board_qna_id" },
  { text: "제목", value: "title" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
});

onMounted(() => {
  getArticleList();
});

const getArticleList = () => {
  console.log("서버에서 글 목록 얻어오자!! ", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      console.log("get qna list", data);
      articles.value = data.list;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const moveWrite = () => {
  console.log("글작성하러가자!!");
  router.push({ name: "board-qna-write" });
};
</script>

<template>
  <div class="board-area">
    <div class="button">
      <div class="find">
        <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
        <input
          type="text"
          v-model="param.word"
          placeholder="검색어..."
          @keyup.enter="getArticleList"
        />
        <button id="findButton" @click="getArticleList">
          <i class="fa-solid fa-magnifying-glass" style="color: #000000"></i>
        </button>
      </div>

      <!-- 로그인해야 보임 -->
      <button type="button" @click="moveWrite" id="wirteButton">글작성</button>
    </div>
    <table class="table custom-table">
      <thead>
        <tr>
          <th scope="col" class="three">글번호</th>
          <th scope="col" class="title">제목</th>
          <th scope="col" class="three">
            <i class="fa-regular fa-comment" style="color: #000000"></i>
          </th>
          <th scope="col" class="three">작성자</th>
          <th scope="col" class="date">작성일</th>
        </tr>
      </thead>
      <tbody>
        <BoardQnaListItem
          v-for="article in articles"
          :key="article.boardQnaId"
          :article="article"
        ></BoardQnaListItem>
      </tbody>
    </table>
  </div>

  <div class="pgnv">
    <PageNavigation
      :current-page="currentPage"
      :total-page="totalPage"
      @pageChange="onPageChange"
    ></PageNavigation>
  </div>
</template>

<style scoped>
table {
  border-collapse: collapse;
  table-layout: fixed;
  width: 90%;
}

.custom-table th {
  border-bottom: 2px solid #333;
  text-align: center;
  padding: 10px 10px;
}

thead {
  vertical-align: bottom;
}

.title {
  width: 40%;
  width: 200px;
  min-width: 60px;
}

.date {
  width: 20%;
  width: 150px;
  min-width: 60px;
}

.board-area {
  width: 100%;
}

.pgnv {
  padding: 30px;
}

.button {
  /* background: #000; */
  display: flex;
  justify-content: end;
  align-items: center;
  min-width: 550px;
  /* align-items: center; */
}

button {
  padding: 5px 10px;
  margin: 5px;
  margin-bottom: 10px;
  border-radius: 13px;
  background-color: white;
  font-weight: 700;
  font-size: 0.9rem;
}

#wirteButton {
  margin-right: 10%;
}

button:hover {
  cursor: pointer;
  border-color: #d20000;
}

.three {
  width: 100px;
  min-width: 60px;
}

input {
  border: 2px solid;
  border-radius: 13px;
  height: 30px;
  padding: 0px 5px;
  margin: 5px;
  margin-right: 0px;
}

input:focus {
  outline-color: #d20000;
}

#findButton {
  border: none;
}
</style>
