<script setup>
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plan";
import { ref } from "vue";
import draggable from "vuedraggable";

import PlanDetailTempListItem from "@/components/plan/item/PlanDetailTempListItem.vue";
import PlanDetailListItem from "@/components/common/PlanDetailListItem.vue";

const memberStore = useMemberStore();
const planStore = usePlanStore();

const { isLogin } = storeToRefs(memberStore);
const { wishlist, storeBox } = storeToRefs(planStore);
const { userLogin, userInfo, getUserInfo } = memberStore;

// -- draggable
const list = ref(["Item 1", "Item 2", "Item 3", "Item 4"]);
let myArray = ref([
  { id: 1, name: "Item 1" },
  { id: 2, name: "Item 2" },
  { id: 3, name: "Item 3" },
]);
const onDragEnd = (event) => {
  console.log("Dragged item to index:", event.newIndex);
};
</script>

<template>
  <div>
    <div>is Login : {{ isLogin }}</div>
    <hr />
    <div>user Login : {{ userLogin }}</div>
    <hr />
    <div>getUserInfo : {{ getUserInfo }}</div>
    <hr />
    <div>UserInfo : {{ userInfo }}</div>
    <hr />

    ..

    <!-- <PlanDetailListItem /> -->
    <div style="display: flex; height: 100%">
      <div style="background-color: aliceblue">
        <!-- {{ wishlist }} -->
        <draggable
          v-model="wishlist"
          group="people"
          @start="drag = true"
          @end="drag = true"
          item-key="contentId"
        >
          <template #item="{ element }">
            <PlanDetailListItem :item="element" />
          </template>
        </draggable>
      </div>

      <div style="background-color: antiquewhite">
        <draggable
          v-model="storeBox"
          group="people"
          @start="drag = true"
          @end="drag = false"
          item-key="contentId"
        >
          <template #item="{ element }">
            <PlanDetailTempListItem :item="element" />
          </template>
        </draggable>
      </div>

      <div style="background-color: rgb(30, 206, 60); width: 300px">{{ wishlist }}</div>
      <div style="background-color: rgb(206, 30, 89); width: 300px">{{ storeBox }}</div>
    </div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
    <div>Main Page</div>
  </div>
</template>

<style scoped></style>
