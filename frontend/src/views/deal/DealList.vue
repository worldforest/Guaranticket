<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3>티켓거래 페이지</h3>
      <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>거래번호</th>
                      <th>분류</th>
                      <th>공연명</th>
                      <th>날짜</th>
                      <th>회차(시간대)</th>
                      <th>좌석 등급</th>
                      <th>가격</th>
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(deal, index) in deal_list" :key="index" :deal_list="deal_list">
                      <td>{{index+1}}</td>
                      <td>{{ deal.category }}</td>
                      <td>{{ deal.title }}</td>
                      <td>{{ deal.date }}</td>
                      <td>{{ deal.time }}</td>
                      <td>{{ deal.grade }}석</td>
                      <td>{{ deal.price }}</td>
                  </tr>
              </tbody>
          </table>
      </div>
      <!-- <router-link
        :to="{ name: 'dealregister' }"
        class="btn btn-primary"
        style="color: white; background-color: #FF4155"
      >
          거래등록
      </router-link> -->
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
          deal_list: []
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/deal')
        .then((res) => {
            this.deal_list = res.data;
            console.log(this.deal_list)
        })
        .catch((err) => {
            console.log("created axios get method error!")
        })
  },
  methods() {

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