import { apiInstance } from "./index.js";

const api = apiInstance();
// const house = houseInstance();

function sidoList(success, fail) {
  api.get(`/map/sido`).then(success).catch(fail);
}

function gugunList(params, success, fail) {
  api.get(`/map/gugun`, { params: params }).then(success).catch(fail);
}

function dongList(params, success, fail) {
  api.get(`/map/dong`, { params: params }).then(success).catch(fail);
}

// function houseList(params, success, fail) {
//   house.get(``, { params: params }).then(success).catch(fail);
// }

function houseList(params, success, fail) {
  api.get(`/map/apt`, { params: params }).then(success).catch(fail);
}

function crimeLevel(params, success, fail) {
  api.get(`/map/crime`, { params: params }).then(success).catch(fail);
}

export { sidoList, gugunList, dongList, houseList, crimeLevel };
