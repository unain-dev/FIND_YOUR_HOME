<template>
  <div>
    <b-container class="bv-example-row mt-3">
      <img
        v-b-toggle.sidebar-no-header
        src="@/assets/toggle.png"
        class="toggle-btn"
        width="70px"
        alt="Kitten"
      />
      <b-sidebar
        id="sidebar-no-header"
        aria-labelledby="sidebar-no-header-title"
        no-header
        shadow
        right
      >
        <template #default="{ hide }">
          <div class="p-3">
            <img
              src="@/assets/close.png"
              class="toggle-close-btn"
              width="40px"
              alt="Kitten"
              @click="hide"
            />
            <h4 id="sidebar-no-header-title">This is HappyHouse</h4>
            <p>
              Welcome, This is HappyHouse. we can help you to find some house
              you what you want to get. if you wanna explore our site or find
              house, please press the below buttons.
            </p>
            <nav class="mb-3">
              <b-nav vertical>
                <b-nav-item href="#"
                  ><router-link :to="{ name: 'Home' }" class="link"
                    ><b-icon icon="house" font-scale="1"></b-icon>
                    HOME</router-link
                  ></b-nav-item
                >
                <b-nav-item href="#"
                  ><router-link :to="{ name: 'Board' }" class="link"
                    ><b-icon icon="journal" font-scale="1"></b-icon>
                    Q&amp;A</router-link
                  ></b-nav-item
                >
                <b-nav-item href="#"
                  ><router-link :to="{ name: 'Instargram' }" class="link"
                    ><b-icon icon="instagram" font-scale="1"></b-icon>
                    SNS</router-link
                  ></b-nav-item
                >
                <b-nav-item href="#"
                  ><router-link :to="{ name: 'House' }" class="link"
                    ><b-icon icon="house-fill" font-scale="1"></b-icon>
                    Apartment</router-link
                  ></b-nav-item
                >

                <div v-if="userInfo">
                  <b-nav-item class="align-self-center"
                    ><b-avatar
                      variant="primary"
                      v-text="
                        userInfo ? userInfo.userid.charAt(0).toUpperCase() : ''
                      "
                    ></b-avatar
                    >{{ userInfo.username }}({{ userInfo.userid }})님
                    Welcome!</b-nav-item
                  >
                  <b-nav-item class="align-self-center"
                    ><router-link
                      :to="{ name: 'MyPage' }"
                      class="link align-self-center"
                      >내정보보기</router-link
                    ></b-nav-item
                  >
                  <b-nav-item
                    class="link align-self-center"
                    @click.prevent="onClickLogout"
                    >로그아웃</b-nav-item
                  >
                </div>

                <div v-else>
                  <b-nav-item-dropdown>
                    <template #button-content>
                      <b-icon icon="people" font-scale="2"></b-icon>
                    </template>
                    <b-dropdown-item href="#"
                      ><router-link :to="{ name: 'SignUp' }" class="link"
                        ><b-icon icon="person-circle"></b-icon>
                        회원가입</router-link
                      ></b-dropdown-item
                    >
                    <b-dropdown-item href="#"
                      ><router-link :to="{ name: 'SignIn' }" class="link"
                        ><b-icon icon="key"></b-icon> 로그인</router-link
                      ></b-dropdown-item
                    >
                  </b-nav-item-dropdown>
                </div>
              </b-nav>
            </nav>
          </div>
        </template>
      </b-sidebar>
    </b-container>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

const memberStore = "memberStore";
const houseStore = "houseStore";
const spinnerStore = "spinnerStore";

export default {
  name: "NaviBar",
  computed: {
    ...mapState(memberStore, ["isLogin", "userInfo"]),
  },
  methods: {
    ...mapMutations(memberStore, [
      "SET_IS_LOGIN",
      "SET_USER_INFO",
      "CLEAR_AREA_LIST",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "CLEAR_GUGUN_CODE",
      "CLEAR_HOUSE_LIST",
      "CLEAR_DONG_CODE",
    ]),
    ...mapMutations(spinnerStore, ["loading"]),
    onClickLogout() {
      this.SET_IS_LOGIN(false);
      this.SET_USER_INFO(null);
      this.CLEAR_AREA_LIST();
      sessionStorage.removeItem("access-token");
      this.CLEAR_SIDO_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_DONG_LIST();
      this.CLEAR_GUGUN_CODE();
      this.CLEAR_HOUSE_LIST();
      this.CLEAR_DONG_CODE();
      this.$store.commit("loading", false, { root: true });
      if (this.$route.path != "/") this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style>
.toggle-btn {
  float: right;
  /* margin-right: 3rem; */
  top: 0px;
}

.toggle-close-btn {
  float: right;
}
</style>
