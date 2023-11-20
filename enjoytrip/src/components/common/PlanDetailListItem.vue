<script setup>
const props = defineProps({
  item: Object,
});
</script>

<template>
  <div>
    <div class="container">
      <div class="order">1</div>
      <div>
        <div class="title">{{ item.title }}</div>
        <div class="addr">{{ item.addr1 }}</div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}
.container {
  width: 100%;
  width: 280px;
  min-height: 80px;
  display: flex;
  align-items: center;
  /* justify-content: center; */
  border: 1px solid rgb(151, 151, 151);
  border-radius: 10px;
  padding: 10px;
  /* margin: 10px; */
  margin-bottom: 20px;
  cursor: pointer;
}

.order {
  display: flex;
  justify-content: center;
  /* align-items: center; */
  background-color: #e3e3e3;
  border-radius: 100%;
  width: 30px;
  height: 30px;
  margin-right: 10px;
}

.title {
  font-size: 0.9rem;
  font-weight: bold;
}

.addr {
  font-size: 0.7rem;
}
</style>
