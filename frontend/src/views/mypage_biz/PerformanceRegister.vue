<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
        <h3>기업회원 공연등록 페이지</h3>
        
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
}
</script>

<style scoped>
 h3 {
     margin-top: 100px;
     font-weight: bold;
     margin-bottom: 10px;
 }
</style>