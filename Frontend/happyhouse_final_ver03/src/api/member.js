import { apiInstance } from "./index.js";

const api = apiInstance();

async function login(user, success, fail) {
  await api.post(`/user/login`, JSON.stringify(user)).then(success).catch(fail);
}

async function findById(userid, success, fail) {
  api.defaults.headers["access-token"] = sessionStorage.getItem("access-token");
  await api.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function idcheck(userid, success, fail) {
  await api.get(`/user/${userid}`).then(success).catch(fail);
}

async function register(user, success, fail) {
  await api.post(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

async function updateInfo(user, success, fail) {
  await api.put(`/user`, JSON.stringify(user)).then(success).catch(fail);
}

async function deleteUser(userid, success, fail) {
  await api.delete(`/user/${userid}`).then(success).catch(fail);
}

async function getUserArea(params, success, fail) {
  await api.get(`/user/getarea`, { params: params }).then(success).catch(fail);
}

//추가
async function deleteArea(userid, success, fail) {
  await api.delete(`/user/area/${userid}`).then(success).catch(fail);
}

//추가
async function registerArea(user, success, fail) {
  await api.post(`/user/area`, JSON.stringify(user)).then(success).catch(fail);
}

// function logout(success, fail)

export {
  login,
  findById,
  idcheck,
  register,
  updateInfo,
  deleteUser,
  getUserArea,
  deleteArea,   // 추가
  registerArea, // 추가
};
