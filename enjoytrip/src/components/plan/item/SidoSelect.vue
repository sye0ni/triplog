<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  selectOption: Array,
  index: String,
});

const emit = defineEmits(["onKeySelect"]);

const key = ref("");

const onSelect = () => {
  console.log(key.value + "선택!!!");
  emit("onKeySelect", key.value);
};

onMounted(() => {
  console.log("sidoSelect onMounted:", props.index);
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
