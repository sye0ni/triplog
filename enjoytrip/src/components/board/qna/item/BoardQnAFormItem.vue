<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";

const props = defineProps({
  article: Object,
  type: String,
});

const emit = defineEmits(["modifyEvent", "writeComment", "deleteArticle"]);

const router = useRouter();

// 댓글 작성 펼치기 기능
const toggleCommentState = ref(false);

const toggleCommentReset = function () {
  toggleCommentState.value = !toggleCommentState.value;
};

// readOnly 설정용
const toggleTitleState = ref(true);
const toggleContentState = ref(true);

// 수정 이벤트 처리
const toggleChange = function (event) {
  const parentNode = event.target.parentNode.parentNode;
  const textArea1 = parentNode.children[0].children[0]; // title
  const textArea2 = parentNode.children[1].children[0]; // content

  // readOnly 상태가 아니라면? 수정하러 가기
  if (!toggleContentState.value) {
    if (props.type === "question") {
      // 제목 지정
      props.article.title = textArea1.value;
    }
    props.article.content = textArea2.value;
    emit("modifyEvent", props.article);
    toggleTitleState.value = !toggleTitleState.value;
    toggleContentState.value = !toggleContentState.value;
  }

  // 작성하기 ; readOnly 해제 (답변일때에는 내용만 해제)
  else {
    toggleContentState.value = !toggleContentState.value;

    if (props.type === "question") {
      // 글 수정
      toggleTitleState.value = !toggleTitleState.value;
      textArea1.focus(); // 제목에 focus 주기
      textArea1.setSelectionRange(
        textArea1.value.length,
        textArea1.value.length
      );
    } else {
      // 댓글 수정
      textArea2.focus(); // 내용에 focus 주기
      textArea2.setSelectionRange(
        textArea2.value.length,
        textArea2.value.length
      );
    }
  }
};

// 댓글 작성 처리
const writeComments = function (event) {
  const commentContent =
    event.target.parentNode.parentNode.children[1].children[0].value;

  if (commentContent === "") {
    alert("내용을 작성하세요.");
  } else {
    emit("writeComment", props.article.boardQnaId, commentContent);
    toggleCommentReset();
  }
};

// 삭제 이벤트 처리
const deleteArticle = function (event) {
  if (confirm("정말로 삭제하시겠습니까?")) {
    if (props.type === "comment") {
      // 댓글 삭제
      // console.log(props.article.commentId);
      emit("deleteArticle", props.article.commentId); // 댓글 아이디 전달
    } else {
      // 질문 삭제
      // console.log(props.article.boardQnaId);
      emit("deleteArticle");
    }
  }
};
</script>

<template>
  <div class="container">
    <!-- 답글 등록이 아닌 경우 -->
    <template v-if="type != 'write'">
      <div class="box">
        <textarea
          id="textArea1"
          :value="
            type === 'question'
              ? article.title
              : type === 'comment'
              ? '답변'
              : ''
          "
          :readOnly="toggleTitleState"
        ></textarea>
        <span
          >{{ article.userId }}
          <span id="date">{{ article.registerTime }}</span></span
        >
      </div>

      <div class="box2">
        <textarea
          id="textArea2"
          :value="article.content"
          :readOnly="toggleContentState"
        ></textarea>
      </div>

      <template v-if="article.userId === 'admin'">
        <!-- 작성자한테만 보이게 => 나중에 수정하기 -->
        <div class="box3">
          <button @click="toggleChange">수정</button>
          <button @click="deleteArticle">삭제</button>
        </div>
      </template>
    </template>

    <!-- 답글 등록 -->
    <template v-else>
      <div class="box3" @click="toggleCommentReset">
        <button>답글 작성</button>
      </div>

      <template v-if="toggleCommentState">
        <div class="box2">
          <textarea placeholder="답변을 입력하세요."></textarea>
        </div>
        <div class="box3">
          <button @click="writeComments">등록</button>
        </div>
      </template>
    </template>
  </div>
</template>

<style scoped>
.container {
  position: relative;
  /* margin: 0% 0%; */
}

.box {
  display: flex;
  justify-content: space-between;
  /* margin: 0% 5%; */
  padding: 1% 1%;
}

.box2 {
  background-color: rgb(247, 247, 247);
  /* margin: 0% 5%; */
  padding: 1% 1%;
  border-top: 1px solid lightgray;
  border-bottom: 1px solid lightgray;
}

.box3 {
  display: flex;
  justify-content: center;
  /* margin: 0% 5%; */
  padding: 1% 1%;
}

#date {
  font-size: x-small;
  color: gray;
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

textarea {
  border-width: 0;
  resize: none;
  width: 100%;
  max-width: 100%;
  background-color: rgb(247, 247, 247);
}

#textArea1 {
  /* title 부분 */
  font-weight: bold;
  background-color: white;
}

textarea:focus {
  outline: none;
}

span {
  white-space: nowrap;
}
</style>
