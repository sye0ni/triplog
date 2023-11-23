<script setup>

const props = defineProps({
    photo: Object,
})

const emit = defineEmits(['showDetail']);

</script>

<template>
    <div class="container" @click="$emit('showDetail', props.photo)">
        <div class="item">
            <div class="image">
                <img :src="'http://localhost:80/upload/' + props.photo.thumbnail" alt="not found">
            </div>
            <div class="text">
                <div class="text1">
                    <span id="title">{{ props.photo.location }}</span>
                    <span id="time">{{ props.photo.registerTime }}</span>
                </div>
                <div class="text2">

                    <i class="fa-solid fa-comment" style="color: #000000;"></i>&nbsp;
                    <span id="cmt">{{ props.photo.commentCnt }}</span>&nbsp;
                    <i class="fa-solid fa-heart" style="color: #db0000;"></i>&nbsp;
                    <span id="like">{{ props.photo.like }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
#title {
    font-weight: bold;
}

#time {
    font-size: small;
}



.container {
    margin-top: 2%;
    width: 22%;
    /* 최대 너비를 지정하여 한 줄에 몇 개의 요소가 배치될지 결정합니다. */
    height: 300px;
    /* background-color: rgb(235, 235, 235); */
    border-radius: 14px;
    border: 1px lightgray solid;
    cursor: pointer;
}

.item {
    height: 100%;
    display: flex;
    flex-direction: column;
}

.image {
    height: 70%;
    /* background-color: wheat; */
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 3%;
}

.image img {
    height: 100%;
    max-width: 100%;
}

.text {
    height: 30%;
    margin: 0% 3%;

}

.text1 {
    display: flex;
    justify-content: space-between;
    height: 50%;
    /* background-color: brown; */
}

.text2 {
    height: 50%;
    display: flex;
    justify-content: flex-end;
}
</style>