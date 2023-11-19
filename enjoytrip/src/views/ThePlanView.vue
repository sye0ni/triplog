<script setup>
import { ref } from "vue";

const planCreateInfo = ref({
  sidoCode: "",
  gugunCode: "",
  startDate: "",
  endDate: "",
});

const planStoreBox = ref([]);

const planDetailListBox = ref([]);
</script>

<template>
  <div>
    <!-- theplanview -->
    <div class="container">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped></style>
