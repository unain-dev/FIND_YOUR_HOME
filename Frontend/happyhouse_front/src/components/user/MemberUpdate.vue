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
              <!-- area 리스트 -->
              <div v-for="(item, index) in area" :key="index">
              {{ item.sidoName }} {{ item.gugunName }} {{ item.dongName }}
              <button type="button" @click="deleteArea(index)">X</button>
              </div>
              <!-- ----------- -->
              <b-row class="mt-4 mb-4 text-center">
                <!-- 지역코드 앞에 area 전부 제거 -->
                <b-col class="sm-3">
                  <b-form-select
                    v-model="sidoCode"
                    :options="sidos"
                    @change="gugunList"
                  ></b-form-select>
                </b-col>
                <b-col class="sm-3">
                  <b-form-select
                    v-model="gugunCode"
                    :options="guguns"
                    @change="dongList"
                  ></b-form-select>
                </b-col>
                <b-col class="sm-3">
                  <b-form-select
                    v-model="dongCode"
                    :options="dongs"
                    @change="selectArea"
                  ></b-form-select>
                </b-col>
                <!-- 버튼 추가 -->
                <b-button
                type="button"
                variant="secondary"
                class="mr-3"
                @click="addArea"
                >추가</b-button
                >
                <!-- -------- -->
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
        dongcodes: [], //doncodes 리스트 추가
      },
      // area 객체를 빈 리스트로
      area: [],
      // userid: null,
      sidoCode: null,
      sidoName: null,
      gugunCode: null,
      gugunName: null,
      dongCode: null,
      dongName: null,
      defferentPwd: false,
      empty: false,
      pwdcheckpage: true,
    };
  },
  computed: {
    ...mapState(memberStore, ["userInfo", "isLogin", "updateUser", "areas"]),
    ...mapState(houseStore, ["sidos", "guguns", "dongs"]), // aa항목 제거
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
    // if (this.userInfo.areacode != null) {
    //   const temp = this.userInfo.areacode.split("");
    //   this.area.sidoCode = temp[0] + temp[1];
    //   this.gugunList();
    //   this.area.gugunCode = temp[0] + temp[1] + temp[2] + temp[3] + temp[4];
    //   this.dongList();
    //   this.area.dongCode = this.userInfo.areacode;
    // }

    // for 문 추가
    for (let i = 0; i < this.areas.length; i++) {
      this.area.push({
        sidoName: this.areas[i].sidoName,
        gugunName: this.areas[i].gugunName,
        dongName: this.areas[i].dongName,
      });
      this.user.dongcodes.push(this.areas[i].dongCode);
    }
  },
  methods: {
    ...mapActions(memberStore, [
      "userUpdate",
      "userConfirm",
      "userDelete",
      "getUserInfo",
      "areaDelete", // 추가
      "areaRegister", // 추가
      "getArea", // 추가
    ]),
    // mapMutations 추가
    ...mapMutations(memberStore, [
      "CLEAR_AREA_LIST", 
      "SET_IS_LOGIN",
      "SET_USER_INFO"
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
      // "SHOW_SIDO_LIST",
      // "SHOW_GUGUN_LIST",
      // "SHOW_DONG_LIST",
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
        await this.areaDelete(this.user.userid); // 추가
        await this.areaRegister(this.user);   //추가
        let token = sessionStorage.getItem("access-token");
        if (this.updateUser) {
          await this.getUserInfo(token);
          this.CLEAR_AREA_LIST(); //추가
          await this.getArea(this.user.userid); //추가
          this.$router.push({ name: "MyPage" });
        }
      }
    },
    canclepage() {
      this.$router.push({ name: "MyPage" });
    },
    // addArea 추가
    addArea() {
      if (this.dongCode == null) return;
      this.area.push({
        sidoName: this.sidoName,
        gugunName: this.gugunName,
        dongName: this.dongName,
      });
      this.user.dongcodes.push(this.dongCode);
    },
    // deletArea 추가
    deleteArea(index) {
      this.area.splice(index, 1);
      this.user.dongcodes.splice(index, 1);
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
          this.SET_IS_LOGIN(false);
          this.SET_USER_INFO(null);
          sessionStorage.removeItem("access-token");
          this.CLEAR_AREA_LIST(); //추가
          if (this.$route.path != "/") this.$router.push({ name: "Home" });
        }
      } else this.defferentPwd = true;
    },
    gugunList() {
      this.CLEAR_GUGUN_LIST();
      this.gugunCode = null;  // this.area -> this.으로
      if (this.sidoCode) this.getGugun(this.sidoCode); 
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null; // this.area -> this.으로
      if (this.gugunCode) this.getDong(this.gugunCode);
    },
    selectArea() {
      // this.user.areacode = this.area.dongCode;
      for (let i = 0; i < this.sidos.length; i++) {
        if (this.sidos[i].value == this.sidoCode)
          this.sidoName = this.sidos[i].text;
      }
      for (let i = 0; i < this.guguns.length; i++) {
        if (this.guguns[i].value == this.gugunCode)
          this.gugunName = this.guguns[i].text;
      }
      for (let i = 0; i < this.dongs.length; i++) {
        if (this.dongs[i].value == this.dongCode)
          this.dongName = this.dongs[i].text;
      }
    },
  },
};
</script>

<style></style>
