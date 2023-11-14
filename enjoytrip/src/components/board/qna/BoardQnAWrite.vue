<script setup>
import { ref } from "vue";
import { writeQuestion } from "@/api/boardQnA";
import { useRouter } from "vue-router";

const userId = ref("ssafy"); // session ? 에서 꺼내오는걸로 수정
const titleValue = ref("");
const contentValue = ref("");
const router = useRouter();

const writeQuestions = function () {
  // userId, title, content 전달하기

  if (titleValue.value === "" || contentValue.value === "") {
    alert("내용을 작성해주세요!");
  } else {
    let articleJson = {
      userId: userId.value,
      title: titleValue.value,
      content: contentValue.value,
    };

    writeQuestion(
      articleJson,
      () => {
        console.log("질문 작성 성공");
        router.push({ name: "board-qna" }); // list 로 이동하기
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const list = function () {
  router.push({ name: "board-qna-list" });
};
</script>

<template>
  <div class="writearea">
    <!-- 제목 영역 -->
    <div class="box1">
      <div>
        <span>제목</span>
      </div>
      <div>
        <textarea
          id="Title"
          placeholder="제목을 입력하세요."
          v-model="titleValue"
        ></textarea>
      </div>
    </div>

    <!-- 내용 영역 -->
    <div class="box2">
      <div>
        <span>내용</span>
      </div>
      <div>
        <textarea
          id="content"
          placeholder="내용을 입력하세요."
          v-model="contentValue"
        ></textarea>
      </div>
    </div>

    <div class="box3">
      <button @click="writeQuestions">등록</button>
      <button @click="list">목록</button>
    </div>
  </div>
</template>

<style scoped>
div {
  /* position: relative; */
  margin: 1% 5%;
  /* padding-left: 5%; */
}

.writearea {
  width: 100%;
}

textarea {
  border-width: 0;
  resize: none;
  width: 100%;
  max-width: 100%;
  background-color: rgb(247, 247, 247);
  padding: 2% 2%;
  outline-color: gray;
}

#Title {
  height: 100px;
}

#content {
  height: 400px;
}

span {
  font-weight: bold;
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

.box3 {
  display: flex;
  justify-content: center;
  /* margin: 0% 5%; */
  padding: 1% 1%;
}
</style>
