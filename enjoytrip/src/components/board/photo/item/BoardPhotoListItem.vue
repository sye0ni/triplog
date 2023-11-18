<script setup>
import { onMounted } from "vue";

const props = defineProps({
  photo: Object,
});

const emit = defineEmits(['showDetail']);


onMounted(() => {
  console.log("들어옴!");
  // console.log(props.photo.thumbNail);
});
</script>

<template>
  <div class="container">
    <div>
      <img :src="'http://localhost:80/upload/' + props.photo.thumbNail" alt="not found"
        @click.prevent="$emit('showDetail', props.photo.boardPhotoId)" />
    </div>

    <!-- <hr> -->
    <div class="info">
      <div>
        <span>{{ props.photo.boardPhotoId }}</span>
        <span style="font-weight:bold">{{ props.photo.userId }}</span>
      </div>
      <div>
        <span>{{ props.photo.like }}</span>
        <i class="fa-solid fa-heart" style="color: #db0000;"></i>
        <!-- <i class="fa-regular fa-heart" style="color: #db0000;"></i> -->
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

div i {
  margin-left: 5px;
}

img {
  cursor: pointer;
}
</style>
