<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="secondary" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="empty"
              >가입 정보를 입력해주세요.</b-alert
            >
            <b-alert show variant="danger" v-else-if="!checkid"
              >이미 사용중인 아이디 입니다.</b-alert
            >
            <b-alert show variant="danger" v-else-if="defferentPwd"
              >비밀번호가 일치하지 않습니다.</b-alert
            >
            <b-alert show variant="success" v-else-if="aftercheck"
              >사용 가능한 아이디 입니다.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <b-button
                type="button"
                variant="warning"
                class="m-1"
                @click="check"
                >중복검사</b-button
              >
              <b-form-input
                id="userid"
                v-model="user.userid"
                required
                placeholder="아이디 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이름:" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                required
                placeholder="이름 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="useremail"
                v-model="user.email"
                required
                placeholder="이메일 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userpwdcheck">
              <b-form-input
                type="password"
                id="userpwdcheck"
                v-model="user.userpwdcheck"
                required
                placeholder="비밀번호 재입력...."
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >회원가입</b-button
            >
            <b-button
              type="button"
              variant="danger"
              class="m-1"
              @click="movePage"
              >취소</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberJoin",
  data() {
    return {
      user: {
        userid: null,
        userpwd: null,
        username: null,
        email: null,
        userpwdcheck: null,
      },
      defferentPwd: false,
      empty: false,
      aftercheck: false,
    };
  },
  computed: {
    ...mapState(memberStore, ["registerUser", "checkid"]),
  },
  methods: {
    ...mapActions(memberStore, ["userRegister", "userIdCheck"]),
    async check() {
      if (this.user.userid == null) {
        this.empty = true;
        return;
      } else {
        await this.userIdCheck(this.user.userid);
        this.empty = false;
        this.defferentPwd = false;
        this.aftercheck = true;
      }
    },
    async confirm() {
      if (
        this.user.userid == null ||
        this.user.username == null ||
        this.user.userpwd == null ||
        this.user.email == null ||
        this.user.userpwdcheck == null
      ) {
        this.empty = true;
        return;
      } else this.empty = false;
      if (this.user.userpwd != this.user.userpwdcheck) {
        this.defferentPwd = true;
        return;
      }
      this.check();
      if (!this.checkid) return;
      await this.userRegister(this.user);
      if (this.registerUser) {
        this.$router.push({ name: "SignIn" });
      }
    },
    movePage() {
      this.$router.push({ name: "Home" });
    },
  },
};
</script>

<style></style>
