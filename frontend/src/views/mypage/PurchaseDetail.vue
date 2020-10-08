<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
        <h3>예매내역 상세정보 페이지</h3>

      <div id="contents">
        <!--공연 분류, 공연명-->
        <h4><span style="color: #FF4155">{{ purchase_detail.category }}</span> {{ purchase_detail.title }}</h4>
        <!--공연 포스터-->
        <div id="poster">
          <img :src="`https://j3b101.p.ssafy.io/api/file/${purchase_detail.poster}`" style="width: 218px;">
        </div>
        <!--공연 상세정보-->
        <div id="detail">
          <table class="table table-bordered">
            <thead class="thead-dark">
              <tr>
                <td class="col">공연장</td>
                <td>{{ purchase_detail.place }}</td>
              </tr>
              <tr>
                <td class="col">날짜</td>
                <td>{{ purchase_detail.date }}</td>
              </tr>
              <tr>
                <td class="col">회차(시간대)</td>
                <td>{{ purchase_detail.time }}</td>
              </tr>
              <tr>
                <td class="col">좌석</td>
                <td>{{ purchase_detail.grade }}석 {{ this.seat }}</td>
              </tr>
              <tr>
                <td class="col">가격</td>
                <td>{{ purchase_detail.price }}원</td>
              </tr>
            </thead>
          </table>
        </div>
      </div>

      <!--버튼-->
      <div id="buttons" style="margin-top: 30px; margin-left: 530px;">
        <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="15%" color="grey lighten-2">목록으로</v-btn>
        <!-- <v-btn @click="dealRegister()" class="mx-3 mb-3 white--text" large width="15%" color="#FF4155">거래 등록</v-btn> -->
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
          purchase_detail: [],
          seat: ""
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/ticket/tid/' + this.tid)
        .then(res => {
            // // console.log(res.data)
            this.purchase_detail = res.data;
            // 공연 분류 번호에 따라 문자열로 변환
            switch(this.purchase_detail.category) {
            case "0":
                this.purchase_detail.category = "콘서트";
                break;
            case "1":
                this.purchase_detail.category = "뮤지컬";
                break;
            case "2":
                this.purchase_detail.category = "스포츠";
                break;
            }
            // 좌석의 행,열 구하기
            if(this.purchase_detail.seatNumber > 18) {
            this.purchase_detail.seatNumber -= 18;
            }
            if(this.purchase_detail.seatNumber % 6 == 0) { // 6의 배수일 때
            this.seat = (this.purchase_detail.seatNumber/6) + "행 6열";
            } else {
            this.seat = parseInt(this.purchase_detail.seatNumber/6)+1 + "행 " + (this.purchase_detail.seatNumber%6) + "열";
            }
            // 가격 (원화 기준으로 콤마 표시)
            this.purchase_detail.price = Number(this.purchase_detail.price).toLocaleTimeString();
        })
        .catch(err => {
            // console.log("created axios get method error!")
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
 h4 {
   font-weight: bold;
   margin-bottom: 30px;
 }
 #contents {
    margin-top: 50px;
    display: inline-block;
    vertical-align: middle;
 }
 #poster {
   float: left;
 }
 #detail {
   float: left;
   margin-left: 20px;
   vertical-align: middle;
 }
 .col {
   width: 35%;
   font-weight: bold;
 }
 table {
   width: 500px;
   margin: 15px 0;
   /* vertical-align: middle; */
   table-layout: fixed;
 }
</style>