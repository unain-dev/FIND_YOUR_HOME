import axios from "axios";
import { API_BASE_URL, APT_DEAL_URL } from "@/config";

// axios 객체 생성
function apiInstance() {
  const instance = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  // instance.interceptors.request.use(
  //   (config) => {
  //     this.$store.commit("spinnerStore/loading", true, { root: true });
  //     return config;
  //   },
  //   (error) => {
  //     this.$store.commit("spinnerStore/loading", false, { root: true });
  //     return Promise.reject(error);
  //   }
  // );

  // instance.interceptors.response.use(
  //   (response) => {
  //     this.$store.commit("loading", false, { root: true });
  //     return response;
  //   },
  //   (error) => {
  //     this.$store.commit("loading", false, { root: true });
  //     return Promise.reject(error);
  //   }
  // );

  return instance;
}

function houseInstance() {
  const instance = axios.create({
    baseURL: APT_DEAL_URL,
    headers: {
      "Content-type": "application/json",
    },
  });

  instance.interceptors.request.use(
    (config) => {
      console.log("interceptor running");

      this.$store.commit("loading", true, { root: true });
      return config;
    },
    (error) => {
      console.log("interceptor running");

      this.$store.commit("loading", false, { root: true });
      return Promise.reject(error);
    }
  );

  instance.interceptors.response.use(
    (response) => {
      console.log("interceptor running");

      this.$store.commit("loading", false, { root: true });
      return response;
    },
    (error) => {
      console.log("interceptor running");

      this.$store.commit("loading", false, { root: true });
      return Promise.reject(error);
    }
  );
  return instance;
}

export { apiInstance, houseInstance };
