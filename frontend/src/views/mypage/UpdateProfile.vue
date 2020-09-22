<template>
  <div>
    <h-nav></h-nav>
      <div class="container" style="text-align: center;">
        <h3 style="margin-top: 100px;">회원정보 수정 페이지</h3>
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
        .get(API_BASE_URL + '/api/')
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