<template>
  <b-container class="bv-example-row mt-3 text-center">
    <h2 class="underline-green"><b-icon icon="instagram"></b-icon> SNS</h2>
    <b-row>
      <b-col>
        <b-carousel
          id="carousel-1"
          :interval="4000"
          controls
          indicators
          background="#ababab"
          img-width="1024"
          img-height="480"
          style="text-shadow: 1px 1px 2px #333"
        >
          <!-- Text slides with image -->
          <b-carousel-slide
            caption="SNS Service"
            text="We have a Instagram Account for Customer using our website."
            img-src="https://cdn.pixabay.com/photo/2017/03/28/12/10/chairs-2181947_960_720.jpg"
          ></b-carousel-slide>

          <!-- Slides with custom text -->
          <b-carousel-slide
            img-src="https://cdn.pixabay.com/photo/2021/08/23/01/03/cubic-house-6566412_960_720.jpg"
          >
            <h1>Please down below</h1>
          </b-carousel-slide>

          <!-- Slides with image only -->
          <b-carousel-slide
            img-src="https://cdn.pixabay.com/photo/2017/04/10/22/28/residence-2219972_960_720.jpg"
          ></b-carousel-slide>

          <!-- Slides with img slot -->
          <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
          <b-carousel-slide>
            <template #img>
              <img
                class="d-block img-fluid w-100"
                width="1024"
                height="480"
                src="https://cdn.pixabay.com/photo/2014/09/18/11/56/skyscrapers-450793_960_720.jpg"
                alt="image slot"
              />
            </template>
          </b-carousel-slide>
        </b-carousel>
      </b-col>
    </b-row>
    <b-row class="card-row">
      <b-col>
        <b-card
          title="Instram Account"
          img-src="https://mblogthumb-phinf.pstatic.net/MjAxNzA0MjdfMTU2/MDAxNDkzMjM2OTk0OTUw.lOULJUkRCPISpdHYKj9QbNdCgYc5TxGSsk-dejUfw3Ig.QCq0hdPnO_C0UOEWRqgrjLGZ0zzJ-T76ykE_0SdOq8gg.PNG.fxgame/unnamed-02.png?type=w800"
          img-alt="Image"
          img-top
          tag="article"
          style="max-width: 20rem"
          class="insta-card"
        >
          <b-card-text>
            Some quick example text to build on the card title and make up the
            bulk of the card's content.
          </b-card-text>

          <b-button href="#" variant="primary">Go Instagram</b-button>
        </b-card>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "Instargram",
};
</script>

<style scoped>
.underline-green {
  display: inline-block;
  background: linear-gradient(
    180deg,
    rgba(255, 255, 255, 0) 70%,
    rgba(56, 233, 40, 0.3) 30%
  );
}

.card-row {
  margin-bottom: 5rem;
  margin-top: 3rem;
}

.insta-card {
  display: inline-block;
  text-align: center;
}
</style>
