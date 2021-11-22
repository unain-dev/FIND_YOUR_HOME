<template>
  <b-container class="mt-4" v-if="userInfo">
    <b-row>
      <b-col>
        <!-- <b-alert variant="" show><h3>내정보</h3></b-alert> -->
      </b-col>
    </b-row>
    <b-row>
      <b-col></b-col>
      <b-col cols="4">
        <b-card no-body style="max-width: 20rem" class="container-my-page">
          <template #header><h2>My Page</h2></template>

          <b-list-group flush>
            <b-list-group-item
              >아이디 : {{ userInfo.userid }}</b-list-group-item
            >
            <b-list-group-item
              >이름 : {{ userInfo.username }}</b-list-group-item
            >
            <b-list-group-item>이메일 : {{ userInfo.email }}</b-list-group-item>
            <b-list-group-item
              >가입일 : {{ userInfo.joindate }}</b-list-group-item
            >
          </b-list-group>

          <b-card-body>
            <b-button variant="primary" href="#" class="mr-1" @click="MovePage"
              >정보수정</b-button
            >
          </b-card-body>
        </b-card>
      </b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
import { mapState } from "vuex";

const memberStore = "memberStore";

export default {
  name: "MemberMyPage",
  components: {},
  computed: {
    ...mapState(memberStore, ["userInfo"]),
  },
  methods: {
    MovePage() {
      this.$router.push({ name: "Update" });
    },
  },
};
</script>

<style>
.container-my-page {
  background-color: white;
}
</style>
