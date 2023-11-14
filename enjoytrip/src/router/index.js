import { createRouter, createWebHistory } from "vue-router";

import MainView from "@/views/TheMainView.vue";
import BoardQnAView from "@/views/TheBoardQnAView.vue";
import PlanView from "@/views/ThePlanView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/plan",
      name: "plan",
      component: PlanView,
    },
    {
      path: "/board/qna",
      name: "board-qna",
      component: BoardQnAView,
      redirect: { name: "board-qna-list" },
      children: [
        {
          path: "",
          name: "board-qna-list",
          component: () => import("@/components/board/qna/BoardQnAList.vue"),
        },
        {
          path: ":articleno",
          name: "board-qna-detail",
          component: () => import("@/components/board/qna/BoardQnADetail.vue"),
        },
        {
          path: "write",
          name: "board-qna-write",
          component: () => import("@/components/board/qna/BoardQnAWrite.vue"),
        },
      ],
    },
  ],
});

export default router;
