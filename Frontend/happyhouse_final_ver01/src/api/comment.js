import { apiInstance } from "./index.js";

const api = apiInstance();

function deleteComment(memono, success, fail) {
  api.delete(`/comment/${memono}`).then(success).catch(fail);
}

function getComments(articleno, success, fail) {
  api.get(`/comment/${articleno}`).then(success).catch(fail);
}

function registerComment(article, success, fail) {
  api
    .post("/comment/", {
      articleno: article.articleno,
      comment: article.comment,
      userid: "ssafy",
    })
    .then(success)
    .catch(fail);
}

function updateComment(comment, success, fail) {
  api.put(`/comment`, comment).then(success).catch(fail);
}

export { deleteComment, getComments, registerComment, updateComment };
