<script setup>
defineProps({ text: String, bgcolor: String, color: String, border: String });
</script>

<template>
  <div>
    <button :style="{ backgroundColor: bgcolor, color: color, border: border }">{{ text }}</button>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

button {
  width: 500px;
  height: 65px;
  background-color: white;
  border-radius: 5px;
  font-size: 1.3rem;
}

button:hover {
  border: none;
  border: 2px solid #ca0f0f;
}
</style>
