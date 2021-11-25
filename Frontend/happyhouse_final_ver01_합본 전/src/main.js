import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import { BootstrapVue, IconsPlugin } from "bootstrap-vue";

// Import Bootstrap an BootstrapVue CSS files (order is important)
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import store from "./store";
import memberStore from "./store/modules/memberStore";

// Make BootstrapVue available throughout your project
Vue.use(BootstrapVue);
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin);

Vue.config.productionTip = false;

new Vue({
  router,
  store,

  // 뷰가 만들어지기 전
  async beforeCreate() {
    // sessionStorage에서 token을 얻어와라
    let token = sessionStorage.getItem("access-token");

    // userInfo state는 없는데 token은 있다 == 로그인을 한 사람이다.
    // localStorage를 써서 유저 정보를 저장하는 경우, 토큰은 없어도 세션에 유저 정보가 남아 있을 수 있음
    // 이 경우, 다시 로그인 정보가 맞는지 확인 해야 하므로 아래처럼 userInfo를 가지고 진짜인지 가짜인지 판단해라.
    if (memberStore.state.userInfo == null && token) {
      await memberStore.dispatch("getUserInfo", token);
    }
  },
  render: (h) => h(App),
}).$mount("#app");
