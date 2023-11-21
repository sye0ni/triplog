<script setup>
import { RouterLink, RouterView } from "vue-router";
import TheHeadingNavbar from "/src/components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <TheHeadingNavbar />

  <div id='routerview'>

      <RouterView />
  </div>
  
</template>

<style scoped>
#routerview{
  margin-top:63px;
}

</style>
