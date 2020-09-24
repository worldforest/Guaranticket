<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3>티켓 상세 정보 페이지</h3>
    
      <div id="contents" style="margin-top: 50px;">
        <!-- <h4>{{ deal_detail.title }}</h4> -->
        <h4><span style="color: #FF4155">카테고리</span> | 공연명</h4>
        <!-- 공연 포스터 -->
        <!-- <img src="" class="rounded float-left"> -->
        <table class="table table-bordered">
          <thead class="thead-dark">
            <tr>
              <td class="col">공연장</td>
              <!-- <td>{{ deal_detail.place }}</td> -->
              <td>경희대학교 평화의 전당</td>
            </tr>
            <tr>
              <td class="col">날짜</td>
              <td>{{ deal_detail.date }}</td>
            </tr>
            <tr>
              <td class="col">회차(시간대)</td>
              <td>{{ deal_detail.time }}</td>
            </tr>
            <tr>
              <td class="col">좌석 등급</td>
              <td>{{ deal_detail.grade }}석</td>
            </tr>
            <tr>
              <td class="col">양도 가격</td>
              <td>{{ deal_detail.price }}원</td>
            </tr>
          </thead>
        </table>
      </div>

      <!--버튼-->
      <div id="buttons" style="margin-top: 50px;">
        <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="15%" color="grey lighten-2">목록으로</v-btn>
        <v-btn class="mx-3 mb-3 white--text" large width="15%" color="#FF4155">구매 요청하기(채팅)</v-btn>
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
        deal_detail: [],
      }
  },
  created() {
    axios
      .get(API_BASE_URL + '/api/deal/did/' + this.did)
      .then((res) => {
        console.log(res.data)
        this.deal_detail = res.data;
        // 가격 (원화 기준으로 콤마 표시)
        this.deal_detail.price = Number(this.deal_detail.price).toLocaleString();
      })
      .catch((err) => {
        console.log("created axios get method error!")
      })
  },
  computed: {
    did: function() {
      return this.$route.params.did;
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
 .col {
   width: 40%;
   font-weight: bold;
   /* margin-left: 1px; 아마 padding*/
   /* margin-right: 1px; */
 }
 h4 {
   font-weight: bold;
   margin-bottom: 30px;
 }
 table {
   width: auto;
   margin: auto;
   table-layout: fixed;
 }
</style>