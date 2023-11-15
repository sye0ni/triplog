<script setup>
defineProps({ text: String, color: String });
</script>

<template>
  <div>
    <button :style="{ backgroundColor: color }">{{ text }}</button>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

button {
  width: 500px;
  height: 70px;
  background-color: white;
  border-radius: 10px;
  font-size: 1.3rem;
}

button:hover {
  border-color: #ca0f0f;
}

/* Rectangle 28 */

/* box-sizing: border-box; */

/* position: absolute;
width: 473px;
height: 76px;
left: 482px;
top: 459px;

border: 1px solid #CA0F0F;
border-radius: 10px; */
</style>
