<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="" show><h3>회원정보수정</h3></b-alert>
      </b-col>
    </b-row>
    <b-row v-if="pwdcheckpage">
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="defferentPwd"
              >비밀번호가 일치하지 않습니다.</b-alert
            >
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                placeholder="비밀번호 입력...."
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="pwdcheck"
            >
              정보수정
            </b-button>
            <b-button
              type="button"
              variant="danger"
              class="m-1"
              @click="deleteuser"
              >회원탈퇴</b-button
            >
          </b-form>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
    <b-row v-else>
      <b-col></b-col>
      <b-col cols="8">
        <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          <b-form class="text-left">
            <b-alert show variant="danger" v-if="empty"
              >회원 정보를 입력해주세요.</b-alert
            >
            <b-alert show variant="danger" v-else-if="defferentPwd"
              >비밀번호가 일치하지 않습니다.</b-alert
            >
            <b-form-group label="아이디:" label-for="userid">
              <h5>{{ userInfo.userid }}</h5>
            </b-form-group>
            <b-form-group label="이름" label-for="username">
              <b-form-input
                id="username"
                v-model="user.username"
                required
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="이메일:" label-for="email">
              <b-form-input
                id="useremail"
                v-model="user.email"
                required
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호:" label-for="userpwd">
              <b-form-input
                type="password"
                id="userpwd"
                v-model="user.userpwd"
                required
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-form-group label="비밀번호 확인:" label-for="userpwdcheck">
              <b-form-input
                type="password"
                id="userpwdcheck"
                v-model="user.userpwdcheck"
                required
                @keyup.enter="confirm"
              ></b-form-input>
            </b-form-group>
            <b-button
              type="button"
              variant="primary"
              class="m-1"
              @click="confirm"
              >정보수정</b-button
            >
            <b-button
              type="button"
              variant="danger"
              class="m-1"
              @click="canclepage"
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
  name: "MemberUpdate",
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
      pwdcheckpage: true,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin", "updateUser"]),
  },
  methods: {
    ...mapActions(memberStore, ["userUpdate", "userConfirm", "userDelete"]),
    async pwdcheck() {
      this.user.userid = this.userInfo.userid;
      this.user.username = this.userInfo.username;
      this.user.email = this.userInfo.email;
      await this.userConfirm(this.user);
      if (this.isLogin) {
        this.pwdcheckpage = false;
        this.defferentPwd = false;
        this.user.userpwdcheck = this.user.userpwd;
      } else {
        this.defferentPwd = true;
      }
    },
    async confirm() {
      if (
        this.user.username == "" ||
        this.user.userpwd == "" ||
        this.user.email == "" ||
        this.user.userpwdcheck == ""
      ) {
        this.empty = true;
        return;
      } else this.empty = false;
      if (this.user.userpwd != this.user.userpwdcheck) {
        this.defferentPwd = true;
        return;
      }
      let ok = confirm("수정 하시겠습니까?");
      if (ok) {
        await this.userUpdate(this.user);
        if (this.updateUser) {
          this.userInfo.userid = this.user.userid;
          this.userInfo.username = this.user.username;
          this.userInfo.email = this.user.email;
          this.$router.push({ name: "MyPage" });
        }
      }
    },
    canclepage() {
      this.$router.push({ name: "MyPage" });
    },
    async deleteuser() {
      this.user.userid = this.userInfo.userid;
      this.user.username = this.userInfo.username;
      this.user.email = this.userInfo.email;
      await this.userConfirm(this.user);
      if (this.isLogin) {
        let ok = confirm("회원탈퇴 하시겠습니까?");
        if (ok) {
          this.defferentPwd = false;
          await this.userDelete(this.user.userid);
          sessionStorage.removeItem("access-token");
          if (this.$route.path != "/") this.$router.push({ name: "Home" });
        }
      } else this.defferentPwd = true;
    },
  },
};
</script>

<style></style>
