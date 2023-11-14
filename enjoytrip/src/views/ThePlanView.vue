<script setup></script>

<template>
  <div class="borderContainer">
    <div class="area">
      <div id="title">Plan View</div>
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

#title {
  padding-left: 5%;
  font-size: 1.4rem;
  font-weight: 600;
  padding-top: 40px;
  padding-bottom: 0px;
  min-width: 130px;
}
</style>
