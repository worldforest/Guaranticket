<template>
  <div>
    <h-nav></h-nav>
    <div id="main-overview" class="container">
      <h3>{{ performance.title }}</h3>
      <v-row>
          <img
            :src="performance.poster"
            height="400px"
            alt="공연 포스터"/>
          <figcaption>
            <h4>공연 장소  {{ performance.title }}</h4>
            <h4>공연 기간  {{ performance.startDate }} ~ {{ performance.endDate }}</h4>
            <h4>관람 시간  {{ performance.running }}분</h4>
            <hr>
          </figcaption>
        </v-row>



    </div>
    <router-view></router-view>
  </div>
</template>

<script>
import { findById } from "@/api/performance.js";
import HNav from "../components/common/HNav";


export default {
  components: {
    HNav,
  },
  data() {
    return {
      performance: [],
      pid: '',
    }
  },
  mounted() {
    var scope = this;
    var pid = this.$route.params.pid;
  },
  created() {
    var scope = this;
    var pid = this.$route.params.pid;
    this.pid=pid;
    findById(
      pid,
      res => {
        this.performance = res.data
      }
    )
  }
}
</script>

<style>

</style>