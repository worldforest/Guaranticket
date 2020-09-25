<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
        <h3>예매내역 상세정보 페이지</h3>
        <div>
            {{ purchase_detail.title }}
        </div>
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
          purchase_detail: []
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/ticket/tid/' + this.tid)
        .then(res => {
            console.log(res.data)
            this.purchase_detail = res.data;
        })
        .catch(err => {
            console.log("created axios get method error!")
        })
  },
  computed: {
    tid: function() {
      return this.$route.params.tid;
    }
  }
}
</script>

<style scoped>
 h3 {
     margin-top: 100px;
     font-weight: bold;
     margin-bottom: 10px;
 }
</style>