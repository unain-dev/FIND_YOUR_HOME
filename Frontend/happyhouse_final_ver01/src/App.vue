<template>
  <div id="app">
    <navi-bar />
    <router-view />
    <Footer />
  </div>
</template>

<script>
import NaviBar from "./components/layout/NaviBar.vue";
import Footer from "./components/layout/Footer.vue";

export default {
  name: "App",
  components: {
    NaviBar,
    Footer,
  },
};
</script>

<style>
a:hover {
  text-decoration: none;
  font-weight: bold;
}

a.router-link-exact-active {
  color: #42b983;
}
#app {
  margin-top: 50px;
  margin-bottom: 100px;
}
</style>
