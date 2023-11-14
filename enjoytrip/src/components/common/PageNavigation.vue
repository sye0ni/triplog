<script setup>
import { computed } from "vue";

const props = defineProps({ currentPage: Number, totalPage: Number });
const emit = defineEmits(["pageChange"]);

const navigationSize = parseInt(import.meta.env.VITE_ARTICLE_NAVIGATION_SIZE);

const startPage = computed(() => {
  return (
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize + 1
  );
});

const endPage = computed(() => {
  let lastPage =
    parseInt((props.currentPage - 1) / navigationSize) * navigationSize +
    navigationSize;
  return props.totalPage < lastPage ? props.totalPage : lastPage;
});

const endRange = computed(() => {
  return (
    parseInt((props.totalPage - 1) / navigationSize) * navigationSize <
    props.currentPage
  );
});

function range(start, end) {
  const list = [];
  for (let i = start; i <= end; i++) list.push(i);
  return list;
}

function onPageChange(pg) {
  console.log(pg + "로 이동!!!");
  emit("pageChange", pg);
}
</script>

<template>
  <div class="row">
    <a class="page-link" @click="onPageChange(1)">
      <i class="fa-solid fa-angles-left"> </i>
    </a>
    <a
      class="page-link"
      @click="onPageChange(startPage == 1 ? 1 : startPage - 1)"
      ><i class="fa-solid fa-angle-left"></i
    ></a>

    <!-- 하단 표시 개수 -->
    <template v-for="pg in range(startPage, endPage)" :key="pg">
      <p :class="currentPage === pg ? 'page-item active' : 'page-item'">
        <a class="page-link" @click="onPageChange(pg)">{{ pg }}</a>
      </p>
    </template>

    <a
      class="page-link"
      @click="onPageChange(endRange ? totalPage : endPage + 1)"
      ><i class="fa-solid fa-angle-right"></i
    ></a>
    <a class="page-link" @click="onPageChange(totalPage)"
      ><i class="fa-solid fa-angles-right"></i
    ></a>
  </div>
</template>

<style scoped>
a {
  cursor: pointer;
  padding: 10px;
}

.row {
  display: flex;
  justify-content: center;
  align-items: center;
}

.page-item.active {
  color: #d20000;
}

.page-item :hover {
  color: #d20000ad;
}

.page-item {
  color: rgb(101, 101, 101);
}
</style>
