<script setup></script>

<template>
  <div class="container">
    <div class="titleBox">
      <div class="title">여기에 다녀왔어요!</div>
    </div>
    <div class="itemBox">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>

.titleBox {
  display: flex;
  justify-content: center;
}

.title {
  font-size: 1.4rem;
  font-weight: 600;
  margin-top: 1%;
}

.itemBox {
  margin: 3%;
}
</style>
