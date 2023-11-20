<script setup>
import { ref, onMounted } from "vue";


const props = defineProps({
    attraction: Object
})

onMounted(() => {
    console.log(props.attraction);
});

</script>

<template>
        <transition name="modal" appear>
            <div class="modal modal-overlay">
                <div class="modal-window">
                    <div class="modal-content"> 
                        <img :src='props.attraction.firstImage'>
                        <p id='title'>{{ props.attraction.title }}</p>
                        <p id='addr'>{{ props.attraction.addr1 }}{{ props.attraction.addr2 }}</p>
                        <p id='description'>{{ props.attraction.overview }}</p>
                    </div>
                </div>
            </div>
        </transition>
</template>

<style scoped>
#title{
    font-size:large;
}

#title, #addr{
    font-weight: bold;

}

#description{
    font-size: small;
}

.modal-content{
    display: block;
    /* flex-direction: row; */
    align-items: center;
    overflow-y: scroll;
    /* padding:10px; */
    position:relative;
    /* width:100%; */
    height:inherit;
    padding:10px 10px;
}

.modal-content img{
    width:100%;
    height:auto;
}


.modal.modal-overlay {
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.3);
}

.modal-window {
    background: white;
    border-radius: 4px;
    overflow: hidden;
    width: 30%;
    height: 80%;
    /* display: inline-block; */
    position: absolute;
    top:12%;
    right:15%;
    display: flex;
    flex-direction: row;
    align-items: center;
    border:2px solid gray;
}




.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.4s;
}


.modal-enter-active .modal-window,
.modal-leave-active .modal-window {
    transition: opacity 0.4s, transform 0.4s;
}

.modal-leave-active {
    transition: opacity 0.6s ease 0.4s;
}

.modal-enter,
.modal-leave-to {
    opacity: 0;
}

.modal-enter .modal-window,
.modal-leave-to .modal-window {
    opacity: 0;
    transform: translateY(-20px);
}

</style>