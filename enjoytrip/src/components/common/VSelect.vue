<script setup>
import { ref } from "vue";

const props = defineProps({
  selectOption: Array,
  index: Object,
});

const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  console.log(key.value + "선택!!!");
  console.log("selectedIndex:::", props.index);
  emit("onKeySelect", key.value);
};
</script>

<template>
  <select v-model="key" @change="onSelect">
    <option
      v-for="option in selectOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
      :selected="option.value == index"
    >
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped>
select {
  border: 2px solid;
  border-radius: 10px;
  height: 35px;
  padding: 5px 5px;
  margin-right: 10px;
}

select:focus {
  outline-color: #d20000;
}
</style>
