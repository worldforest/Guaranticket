<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3 style="margin-top: 100px;">티켓거래 페이지</h3>
      <router-link
        :to="{ name: 'dealregister' }"
        class="btn btn-primary"
        style="color: white; background-color: #FF4155"
      >
          거래등록
      </router-link>
    </div>
  </div>

</template>

<script>
import HNav from "@/components/common/HNav";
import axios from "axios";

export default {
  components: {
    HNav
  },
}
</script>

<style>

</style>