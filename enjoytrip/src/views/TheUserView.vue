<script setup></script>

<template>
  <div>
    the user view
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped>
.container {
  min-height: 1000px;
  height: 100%;
}
</style>
