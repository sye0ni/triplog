<script setup>
import { onMounted } from "vue";

const props = defineProps({
  photo: Object,
});

onMounted(() => {
  console.log("들어옴!");
  console.log(props.photo.thumbNail);
});
</script>

<template>
  <div class="container">
    <div>
    <img :src="'http://localhost:80/upload/' + props.photo.thumbNail" alt="not found" /> <!-- 이미지 스프링 통해서 불러오기 -->
  </div>

    <!-- <hr> -->
    <div class="info">
      <div>
        <span>{{ props.photo.boardPhotoId }}</span>
        <span>{{ props.photo.userId }}</span>
      </div>
      <div>
        <span>{{ props.photo.like }}</span>
        <span>♥</span>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  margin: 2%;
  padding: 1%;
  display: flex;
  flex-direction: column;
  display: inline-block;
}
img {
  width: 250px;
  height: 250px;
  display: block;
}
.info {
  display: flex;
  justify-content: space-between;
  margin-top: 2%;
}
</style>
