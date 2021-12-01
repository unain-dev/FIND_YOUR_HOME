<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="" show><h3>로그인</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="5">
        <form class="loginForm">
          <b-alert show variant="danger" v-if="error"
            >아이디 또는 비밀번호를 확인하세요.</b-alert
          >
          <div label="아이디:" label-for="userid" class="Form idForm">
            <input
              id="userid"
              v-model="user.userid"
              required
              placeholder="아이디 입력...."
              @keyup.enter="confirm"
            />
          </div>
          <div label="비밀번호:" label-for="userpwd" class="passForm Form">
            <input
              type="password"
              id="userpwd"
              v-model="user.userpwd"
              required
              placeholder="비밀번호 입력...."
              @keyup.enter="confirm"
            />
          </div>
          <button
            type="button"
            variant="primary"
            class="m-1 mBtn"
            @click="confirm"
          >
            로그인
          </button>
          <button
            type="button"
            variant="success"
            class="m-1 mBtn"
            @click="movePage"
          >
            회원가입
          </button>
        </form>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberLogin",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
      },
      error: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["isLogin", "isLoginError"]),
  },
  methods: {
    ...mapActions(memberStore, ["userConfirm", "getUserInfo", "getArea"]),
    async confirm() {
      await this.userConfirm(this.user);
      this.error = this.isLoginError;
      let token = sessionStorage.getItem("access-token");
      if (this.isLogin) {
        await this.getUserInfo(token);
        this.getArea(this.user.userid);

        this.$router.push({ name: "Home" });
      }
    },
    movePage() {
      this.$router.push({ name: "SignUp" });
    },
  },
};
</script>

<style></style>
