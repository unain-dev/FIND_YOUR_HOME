<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <h3>회원정보수정</h3>
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
            <b-form-group label="관심지역 등록:" label-for="areacode">
              <b-row class="mt-4 mb-4 text-center">
                <b-col class="sm-3">
                  <b-form-select
                    v-model="area.sidoCode"
                    :options="sidos"
                    @change="gugunList"
                  ></b-form-select>
                </b-col>
                <b-col class="sm-3">
                  <b-form-select
                    v-model="area.gugunCode"
                    :options="guguns"
                    @change="dongList"
                  ></b-form-select>
                </b-col>
                <b-col class="sm-3">
                  <b-form-select
                    v-model="area.dongCode"
                    :options="dongs"
                    @change="selectArea"
                  ></b-form-select>
                </b-col>
              </b-row>
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
import { mapState, mapActions, mapMutations } from "vuex";

const memberStore = "memberStore";
const houseStore = "houseStore";

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
      area: {
        userid: null,
        sidoCode: null,
        sidoName: null,
        gugunCode: null,
        gugunName: null,
        dongCode: null,
        dongName: null,
      },
      defferentPwd: false,
      empty: false,
      pwdcheckpage: true,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin", "updateUser"]),
    ...mapState(houseStore, ["sidos", "guguns", "dongs", "aa"]),
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
    if (this.userInfo.areacode != null) {
      const temp = this.userInfo.areacode.split("");
      this.area.sidoCode = temp[0] + temp[1];
      this.gugunList();
      this.area.gugunCode = temp[0] + temp[1] + temp[2] + temp[3] + temp[4];
      this.dongList();
      this.area.dongCode = this.userInfo.areacode;
    }
  },
  methods: {
    ...mapActions(memberStore, [
      "userUpdate",
      "userConfirm",
      "userDelete",
      "getUserInfo",
    ]),
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      "SHOW_SIDO_LIST",
      "SHOW_GUGUN_LIST",
      "SHOW_DONG_LIST",
    ]),
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
        let token = sessionStorage.getItem("access-token");
        if (this.updateUser) {
          await this.getUserInfo(token);
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
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.area.gugunCode = null;
      if (this.area.sidoCode) this.getGugun(this.area.sidoCode);
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.area.dongCode = null;
      if (this.area.gugunCode) this.getDong(this.area.gugunCode);
    },
    selectArea() {
      this.user.areacode = this.area.dongCode;
    },
  },
};
</script>

<style></style>
