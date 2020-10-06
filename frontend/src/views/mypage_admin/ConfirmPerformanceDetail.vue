<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3>관리자 공연등록 관리 상세 페이지</h3>
      pid: {{ $route.params.pid }}
      sid: {{ $route.params.sid }}
      <br>

      <div id="contents">
        <!--공연 분류, 공연명-->
        <h4><span style="color: #FF4155">{{ performance_detail.category }}</span> {{ performance_detail.title }}</h4>
        <!--공연 포스터-->
        <div id="poster">
          <img :src="performance_detail.poster" style="width: 218px;">
        </div>
        <!--공연 상세정보-->
        <div id="detail">
          <table class="table table-bordered">
            <thead class="thead-dark">
              <tr>
                <td class="col">주소</td>
                <td>{{ performance_detail.location }}</td>
              </tr>
              <tr>
                <td class="col">공연장</td>
                <td>{{ performance_detail.place }}</td>
              </tr>
              <tr>
                <td class="col">기간</td>
                <td>{{ performance_detail.startDate }} ~ {{ performance_detail.endDate }}</td>
              </tr>
              <tr>
                <td class="col">관람시간</td>
                <td>{{ performance_detail.running }}분</td>
              </tr>
              <tr>
                <td class="col">예매가능 날짜</td>
                <td>{{ performance_detail.ticketingStartDate }} ~ {{ performance_detail.ticketingEndDate }}</td>
              </tr>
            </thead>
          </table>
        </div>
      </div>
      
      <!--버튼-->
      <div id="buttons" style="margin-top: 50px;">
        <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="15%" color="grey lighten-2">목록으로</v-btn>
        <v-btn @click="submitPerformance()" class="mx-3 mb-3 white--text" large width="15%" color="#FF4155">승인</v-btn>
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
        performance_detail: []
      }
  },
  computed: {
    pid: function() {
      return this.$route.params.pid;
    },
    sid: function() {
      return this.$route.params.sid;
    }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/performance/' + this.pid)
        .then(res => {
          console.log(res.data)
          this.performance_detail = res.data;
          // 공연 분류 번호에 따라 문자열로 변환
          switch(this.performance_detail.category) {
            case "0":
              this.performance_detail.category = "콘서트";
              break;
            case "1":
              this.performance_detail.category = "뮤지컬";
              break;
            case "2":
              this.performance_detail.category = "스포츠";
              break;
          }
        })
        .catch(err => {
          console.log("created axios get method error!")
        })
  },
  methods: {
    // 공연등록 승인하기
    submitPerformance() {
      confirm("등록을 승인하시겠습니까?");
      if(confirm) {
        axios
          .delete(API_BASE_URL + '/api/performance/submission/' + this.sid)
          .then(res => {
            // 승인완료 시 알림 메세지를 띄워준 후 목록페이지로 이동한다.
            alert("공연이 승인되었습니다!");
            this.$router.push("/confirmperformance");
          })
          .catch(err => {
            console.log("axios delete performance submit method error!")
          })
      }
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