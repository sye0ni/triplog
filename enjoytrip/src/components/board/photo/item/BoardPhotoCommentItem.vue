<script setup>

const props = defineProps({
    comment: Object
})


</script>

<template>
    <div class="container">
        <div class="box1">
            <span id="userId" style="font-weight: bold;">{{ comment.userId }}</span>
            <!-- 작성자의 경우에만 보이게 -->
            <div class="box1-1">
                <span style="font-size: x-small;">{{ comment.registerTime }}</span>
                <i class="fa-solid fa-ellipsis"></i>
            </div>
        </div>
        <div class="box2">
            <span style="font-size: small;">{{ comment.content }}</span>
        </div>
    </div>
</template>

<style scoped>
.container {
    display: flex;
    flex-direction: column;
    margin: 0px 5px;
    margin-top: 10px;
}

.box1 {
    height: 40%;
    /* background-color: lavender; */
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.box1-1 i {
    margin-left: 10px;
}

.box2 {
    height: 60%;
    /* background-color: lightblue; */
    display: flex;
    /* justify-content: space-between; */
    align-items: center;
    margin-top: 5px;
}
</style>