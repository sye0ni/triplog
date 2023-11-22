<script setup>
import { ref, onMounted } from "vue";

const props = defineProps({
  selectOption: Array,
  index: String,
});

// onMounted(() => {
//   console.log("들어옴!!");
//   console.log(props.index);

//   console.log("key:", typeof key.value);
//   // key.value = props.index;
//   console.log("key.value", typeof key.value);

//   setInterval(100);
//   // key.value = props.index;
//   console.log("key.value", key.value);

    
// });


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
