<template>
  <b-container class="bv-example-row mt-3">
    <h1 class="welcome">Welcome,<br />HappyHouse</h1>
    <b-row>
      <b-col></b-col>
      <b-col></b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "Main",
  props: {
    msg: String,
  },
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Raleway:wght@300&display=swap");

* {
  font-family: "Raleway";
}

.welcome {
  font-size: 5rem;
}
</style>
