<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3 style="margin-top: 100px;">티켓 거래 등록 페이지</h3>
    </div>
  </div>
</template>

<script>
import HNav from "@/components/common/HNav";
import axios from "axios";
import { API_BASE_URL } from "@/config";

export default {
  components: {
    HNav
  },
  data: () => {
      return {

      }
  },
  created() {
      axios
        .post(API_BASE_URL + '/deal')
        .then((res) => {

        })
        .catch((err) => {

        })
  },
  methods() {

  }
}
</script>

<style>

</style>