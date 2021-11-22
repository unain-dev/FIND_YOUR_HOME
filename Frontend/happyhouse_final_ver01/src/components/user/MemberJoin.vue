<template>
  <b-container class="bv-example-row mt-3">
    <b-row>
      <b-col>
        <b-alert variant="" show><h3>회원가입</h3></b-alert>
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="5">
        <form class="loginForm">
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

          <div label="아이디:" label-for="userid" class="Form idForm">
            <input
              id="userid"
              class="id"
              v-model="user.userid"
              required
              placeholder="아이디 입력...."
              @change="check"
            />
          </div>
          <!-- <button
            type="button"
            variant="warning"
            class="m-1 checkBtn"
            @click="check"
          >
            중복검사
          </button> -->
          <div label="이름:" label-for="username" class="Form">
            <input
              id="username"
              v-model="user.username"
              required
              placeholder="이름 입력...."
              @keyup.enter="confirm"
            />
          </div>
          <div label="이메일:" label-for="email" class="Form">
            <input
              id="useremail"
              v-model="user.email"
              required
              placeholder="이메일 입력...."
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
              class="pw"
            />
          </div>
          <div
            label="비밀번호 확인:"
            label-for="userpwdcheck"
            class="passForm Form"
          >
            <input
              type="password"
              id="userpwdcheck"
              v-model="user.userpwdcheck"
              required
              placeholder="비밀번호 재입력...."
              @keyup.enter="confirm"
              class="pw"
            />
          </div>
          <button
            type="button"
            variant="primary"
            class="m-1 mBtn"
            @click="confirm"
          >
            회원가입
          </button>
          <button
            type="button"
            variant="danger"
            class="m-1 mBtn"
            @click="movePage"
          >
            취소
          </button>
        </form>
        <!-- <b-card class="text-center mt-3" style="max-width: 40rem" align="left">
          
        </b-card> -->
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

<style>
.loginForm {
  /* width: 300px; */
  height: 400px;
  padding: 30px, 20px;
  background-color: #ffffff;
  text-align: center;
  /* transform: translate(-50%, -50%); */
  border-radius: 15px;
  margin-bottom: 50px;
}

.loginForm h2 {
  text-align: center;
  margin: 30px;
}

.Form {
  border-bottom: 2px solid #adadad;
  margin: 20px;
  padding: 10px 10px;
}

.Form input {
  width: 80%;
  border: none;
  outline: none;
  color: #636e72;
  font-size: 16px;
  height: 25px;
  background: none;
  background-color: white;
}

/* button {
  transform: translateX(-50%);
  margin-bottom: 40px;
  background: linear-gradient(125deg, #81ecec, #6c5ce7, #81ecec);
  background-position: left;
  background-size: 200%;
  color: white;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: 0.4s;
  display: inline;
  padding: 10px;
  
} */

.mBtn {
  position: relative;
  left: 40%;
  transform: translateX(-50%);
  margin-bottom: 40px;
  width: 80%;
  height: 40px;
  background: linear-gradient(125deg, #81ecec, #6c5ce7, #81ecec);
  background-position: left;
  background-size: 200%;
  color: white;
  font-weight: bold;
  border: none;
  cursor: pointer;
  transition: 0.4s;
  display: inline;
}

.mBtn:hover,
button:hover {
  background-position: right;
}

.bottomText {
  text-align: center;
}
</style>
