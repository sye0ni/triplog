<script setup>
import BoardQnADetail from "../components/board/qna/BoardQnADetail.vue";
import BoardQnAList from "../components/board/qna/BoardQnAList.vue";
</script>

<template>
  <div class="borderContainer">
    <div class="area">
      <div id="title">QnA 게시판</div>
      <div class="list">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.borderContainer {
  display: flex;
  justify-content: center;
  overflow-x: auto;
  /* max-width: calc(100% - 200px);
  margin: 0 auto; */
  /* display: flex;
  justify-content: center;
  max-width: 100%; */
}
.area {
  margin: 10px;
  width: 100%;
}
.list {
  padding-left: 5%;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}

#title {
  padding-left: 5%;
  font-size: 1.4rem;
  font-weight: 600;
  padding-top: 40px;
  padding-bottom: 0px;
  min-width: 130px;
}
</style>
