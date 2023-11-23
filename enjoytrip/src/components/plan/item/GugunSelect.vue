<script setup>
import { ref, watch, onMounted } from "vue";

const props = defineProps({
  selectOption: Array,
  index: String,
  type: String,
});

const emit = defineEmits(["onKeySelect"]);

const key = ref("");

watch(() => {
  console.log("index 변경!!", props.index);
  key.value = props.index;
});

const onSelect = () => {
  console.log(key.value + "선택!!!");
  console.log("selectedIndex:::", props.index);
  emit("onKeySelect", key.value);
};

onMounted(() => {
  console.log("gugunSelect OnMouned index ", props.index);
  key.value = props.index;
});
</script>

<template>
  <select v-model="key" @change="onSelect">
    <option
      v-for="option in selectOption"
      :key="option.value"
      :value="option.value"
      :disabled="option.value === '' ? true : false"
    >
      {{ option.text }}
    </option>
  </select>
</template>

<style scoped>
select {
  border: 2px solid;
  border-radius: 5px;
  height: 35px;
  padding: 5px 5px;
  margin-right: 10px;
}

select:focus {
  outline-color: #d20000;
}
</style>
