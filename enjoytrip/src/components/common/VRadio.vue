<script setup>
import { ref } from "vue";

const props = defineProps({
  item: Object,
});

const emit = defineEmits(["changeValue"]);

const emitItem = function () {
  emit("changeValue", props.item.value);
};
</script>

<template>
  <div>
    <input
      class="form-check-input"
      name="checks"
      type="radio"
      :value="item.value"
      @change="emitItem"
    />
    <label> {{ item.title }} </label>
  </div>
</template>

<style scoped></style>
