<template>
  <b-container class="bv-example-row mt-3 text-center container">
    <h3 class="underline-hotpink"><b-icon icon="journals"></b-icon> Q&amp;A</h3>
    <div class="empty-block"></div>
    <router-view></router-view>
  </b-container>
</template>
<script>
export default {
  name: "Board",
};
</script>
<style scoped>
.underline-hotpink {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(231, 27, 139, 0.3) 30%
  );
}
.empty-block {
  height: 50px;
}
.container {
  padding-bottom: 3rem;
}
</style>
