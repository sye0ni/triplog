<script setup>
// import { ref } from "vue";

defineProps({ article: Object });
</script>

<template>
  <tr class="">
    <td scope="row" class="no">{{ article.boardQnaId }}</td>
    <td class="title">
      <router-link
        :to="{
          name: 'board-qna-detail',
          params: { articleno: article.boardQnaId },
        }"
      >
        {{ article.title }}</router-link
      >
    </td>
    <td>{{ article.commentCnt }}</td>
    <td>{{ article.userId }}</td>
    <td>{{ article.registerTime }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
  color: black;
}

td {
  text-align: center;
  /* background-color: aqua; */
  border-bottom: 1px solid #afa9a9;
  padding: 10px 10px;
  white-space: nowrap; /* 텍스트를 한 줄로 표시 */
  overflow: hidden; /* 넘치는 부분을 숨김 */
  text-overflow: ellipsis; /* 넘치는 부분에 ...을 표시 */
}

tr:hover {
  background-color: #f8cbcb49;
}

.text-start,
.no {
  text-align: left;
}

.title {
  text-align: left;
}
</style>
