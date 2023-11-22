import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { draggable } from "vuedraggable";
import '@/assets/css/fonts.css';

// import "bootstrap/dist/css/bootstrap.min.css";
// import "bootstrap";

const app = createApp(App);
const pinia = createPinia();

// app.component("draggable", draggable);

pinia.use(piniaPluginPersistedstate);

app.use(pinia);
app.use(router);

app.mount("#app");
