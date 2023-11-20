<script setup>
import { ref } from "vue";

const planCreateInfo = ref({
  sidoCode: "",
  gugunCode: "",
  startDate: "",
  endDate: "",
  period: "",
});

const planStoreBox = ref([]);

const planDetailListBox = ref([]);

// const setPeriod = function (data) {
//   console.log(data.period + "받았다!!!");
//   planCreateInfo.value.period = data.period;
//   planCreateInfo.value.sidoCode = data.planDto.sidoCode;
//   planCreateInfo.value.gugunCode = data.planDto.gugunCode;
//   planCreateInfo.value.startDate = data.planDto.startDate;
//   planCreateInfo.value.endDate = data.planDto.endDate;

//   console.log(planCreateInfo.value);
// };
</script>

<template>
  <div>
    <!-- theplanview -->
    <div class="container">
      <!-- <router-view @period-change="setPeriod" :planCreateInfo="planCreateInfo"></router-view> -->
      <router-view :planCreateInfo="planCreateInfo"></router-view>
    </div>
  </div>
</template>

<style scoped></style>
