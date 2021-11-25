<template>
  <b-row v-if="this.modifyComment != null" class="mb-3 mt-2">
    <b-col cols="10">
      <b-form-textarea
        id="comment"
        placeholder="도서평 입력..."
        v-model="modifyComment.comment"
        rows="2"
      ></b-form-textarea>
    </b-col>
    <b-col>
      <b-button variant="outline-info" class="mr-2 h-100" @click="updateComment"
        >수정</b-button
      >
      <b-button
        variant="outline-danger"
        class="h-100"
        @click="updateCommentCancel"
        >취소</b-button
      >
    </b-col>
  </b-row>

  <b-row v-else class="mb-3 mt-2">
    <b-col cols="11">
      <b-form-textarea
        id="comment"
        placeholder="댓글 입력..."
        v-model="comment"
        rows="2"
      ></b-form-textarea>
    </b-col>
    <b-col
      ><b-button variant="dark" class="h-100" @click="registComment"
        >등록</b-button
      ></b-col
    >
  </b-row>
</template>

<script>
// import http from "@/util/http-common";
import { registerComment, updateComment } from "@/api/comment.js";
import { mapActions, mapGetters } from "vuex";

let store = "commentStore";
let mStore = "memberStore";

export default {
  name: "commentwrite",
  data() {
    return {
      // 차후 작성자 이름은 로그인 구현후 로그인한 사용자로 바꾼다.
      // user_name: "안효인",
      comment: "",
    };
  },
  props: {
    no: { type: Number },
    modifyComment: { type: Object },
    store: { type: String },
  },
  computed: {
    ...mapGetters(mStore, ["checkUserInfo"]),
  },
  methods: {
    ...mapActions(store, ["actionComments"]),

    registComment() {
      const article = {
        articleno: this.no,
        comment: this.comment,
        userid: this.checkUserInfo.userid,
      };

      registerComment(
        article,
        () => {
          alert("댓글 작성이 완료 되었습니다.");

          // 작성글 지우기
          this.comment = "";

          this.actionComments(this.no);
        },
        (error) => {
          console.log("댓글 등록 중 오류 발생 : ", error);
        }
      );
    },
    updateComment() {
      const comment = {
        articleno: this.modifyComment.articleno,
        comment: this.modifyComment.comment,
        memono: this.modifyComment.memono,
        memotime: this.modifyComment.memotime,
        userid: this.modifyComment.userid,
      };

      updateComment(
        comment,
        () => {
          alert("댓글 수정 완료");
          this.updateCommentCancel();
          this.actionComments(this.modifyComment.articleno);
        },
        (error) => {
          console.log("댓글 수정 중 오류 발생 : ", error);
        }
      );
    },
    updateCommentCancel() {
      this.$emit("modify-comment-cancel", false);
    },
  },
};
</script>
