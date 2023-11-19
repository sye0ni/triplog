<script setup>
import BoardQnAFormItem from "./item/BoardQnAFormItem.vue";
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import {
  getArticle,
  getComments,
  modifyQuestion,
  modifyComment,
  writeComment,
  deleteQuestion,
  deleteComment,
} from "@/api/boardQnA";

const route = useRoute();
const router = useRouter();
const userId = ref("admin");
const { articleno } = route.params; // 질문 번호

const article = ref({});
const comments = ref([]);

onMounted(() => {
  // 질문 얻어오기
  detailArticle();

  // 답변 얻어오기
  getComment();
});

const detailArticle = () => {
  //console.log("articleno:" + articleno);

  getArticle(
    articleno,
    ({ data }) => {
      //console.log("article:" + data);
      article.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const getComment = () => {
  getComments(
    articleno,
    ({ data }) => {
      //console.log("comments:" + data);
      comments.value = data;
    },
    (error) => {
      console.log(error);
    }
  );
};

const modifyQuestions = function () {
  // console.log("글 수정하러 가기");
  // console.log(article.value);

  modifyQuestion(
    articleno,
    article.value,
    () => {
      console.log("질문 수정 성공");
    },
    (error) => {
      console.log(error);
    }
  );
};

const modifyComments = function (args) {
  // console.log("댓글 수정");
  // console.log(args.commentId);

  modifyComment(
    articleno,
    args.commentId,
    args,
    () => {
      console.log("댓글 수정 성공");
    },
    (error) => {
      console.log(error);
    }
  );
};

const writeComments = function (...args) {
  // articleno, commentcontent
  // console.log("댓글 작성");

  let commentJson = {
    content: args[1],
    userId: "admin",
  };

  writeComment(
    args[0],
    commentJson,
    () => {
      console.log("댓글 작성 성공");
      // location.reload();
      getComment();
    },
    (error) => {
      console.log(error);
    }
  );
};

const deleteQuestions = function () {
  deleteQuestion(
    articleno,
    () => {
      console.log("삭제 성공");
      router.push({ name: "board-qna-list" });
    },
    (error) => {
      console.log(error);
    }
  );
};

const deleteComments = function (args) {
  // commentId
  console.log("댓글 삭제하러 가자?");

  deleteComment(
    articleno,
    args,
    () => {
      console.log("댓글 삭제 성공");
      // location.reload();
      getComment();
    },
    (error) => {
      console.log(error);
    }
  );
};

// 목록으로 보내기
const list = function () {
  router.push({ name: "board-qna-list" });
};
</script>

<template>
  <!-- <hr /> -->
  <div class="detailarea">
    <div class="golist">
      <button @click="list">목록</button>
    </div>
    <!-- 질문 영역 -->
    <div>
      <BoardQnAFormItem :article="article" type="question" @modify-event="modifyQuestions"
        @delete-article="deleteQuestions" />
    </div>

    <!-- (답변이 있다면) 답변 보이기 -->
    <div v-for="comment in comments" :key="comment.commentId">
      <BoardQnAFormItem :article="comment" type="comment" @modify-event="modifyComments"
        @delete-article="deleteComments" />
    </div>

    <!-- (관리자라면) 답변 등록 -->
    <template v-if="userId === 'admin'">
      <!-- 현재 로그인한 유저가 관리자인가?-->
      <div>
        <BoardQnAFormItem :article="article" @write-comment="writeComments" type="write" />
      </div>
    </template>
  </div>
</template>

<style scoped>
hr {
  margin: 0% 5%;
  height: 2px;
  width: 100%;
  background-color: black;
}

.detailarea {
  /* background-color: aquamarine; */
  width: 100%;
  padding-right: 10%;

  /* min-width: 800px; */
}

.golist {
  /* width: 100%; */
  display: flex;
  justify-content: end;
}

button {
  padding: 5px 15px;
  margin: 0px 5px;
  border-radius: 20px;
  background-color: white;
  font-weight: bold;
}

button:hover {
  cursor: pointer;
  border-color: red;
}
</style>
