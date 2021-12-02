<template>
  <b-container class="bv-example-row bg-light mb-2 p-2">
    <b-row class="p-23">
      <b-col class="text-left text-bold"
        ><b>{{ comment.userid }}</b></b-col
      >
      <b-col class="text-right">{{ comment.memotime }}</b-col>
    </b-row>
    <b-row class="p-3">
      <b-col class="text-left" v-html="enterToBr(comment.comment)"></b-col>
    </b-row>
    <b-row class="p-3">
      <b-col class="text-left"></b-col>
    </b-row>
    <b-row>
      <b-col class="text-right">
        <b-link @click="modifyCommentView">수정</b-link> |
        <b-link @click="removeComment">삭제</b-link>
      </b-col>
    </b-row>
    <!-- 로그인 기능 구현 후 로그인한 자신의 글에만 보이게 한다. -->
  </b-container>
</template>

<script>
import { mapActions } from "vuex";
import { deleteComment } from "@/api/comment.js";
// import http from "@/util/http-common";

let store = "commentStore";

export default {
  name: "comment",
  props: {
    // 상위 component에서 전달한 도서평을 받는다.
    comment: Object,
  },
  computed: {
    modifiedComment: () => {
      return this.comment.comment;
    },
  },
  created() {
    store = this.store;
  },
  methods: {
    ...mapActions(store, ["actionComments"]),

    modifyCommentView() {
      // 상위 component의 modify-comment인 event listener에 전달.
      this.$emit("modify-comment", {
        articleno: this.comment.articleno,
        comment: this.comment.comment,
        memono: this.comment.memono,
        memotime: this.comment.memotime,
        userid: this.comment.userid,
      });
    },
    removeComment() {
      if (confirm("정말로 삭제?")) {
        deleteComment(
          this.comment.memono,
          () => {
            this.actionComments(this.comment.articleno);
          },
          (error) => {
            console.log("댓글 수정 중 오류 발생 : ", error);
          }
        );
        // this.$emit("remove-comment", {
        //   articleno: this.comment.articleno,
        //   comment: this.comment.comment,
        //   memono: this.comment.memono,
        //   memotime: this.comment.memotime,
        //   userid: this.comment.userid,
        // });
      }
    },
    enterToBr(str) {
      if (str) return str.replace(/(?:\r\n|\r|\n)/g, "<br />");
    },
  },
};
</script>
