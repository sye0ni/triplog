<script setup></script>

<template>
  <div class="container">
    <div>
      <img src="@/assets/gangneung.jpg" alt="not found" />
    </div>
    <!-- <hr> -->
    <div class="info">
      <div>
        <span>userName</span>
      </div>
      <div>
        <span>13 </span>
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
