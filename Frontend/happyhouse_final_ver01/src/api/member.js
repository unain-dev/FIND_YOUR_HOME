import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  // axios 객체인 api의 header에 토큰을 넣어서 보냄.
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");

  // 토큰과 함께 path variable로 userid도 같이 감.
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

// function logout(success, fail)

export { login, findById };
