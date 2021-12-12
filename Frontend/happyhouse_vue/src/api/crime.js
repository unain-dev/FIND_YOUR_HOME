import { apiInstance } from "./index.js";

const api = apiInstance();

function getCrimeLevel(params, success, fail) {
  api.get(`/map/crime`, { params: params }).then(success).catch(fail);
}

export default { getCrimeLevel };
