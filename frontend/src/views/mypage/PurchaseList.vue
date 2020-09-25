<template>
  <div>
    <h-nav></h-nav>
      <div class="container" style="text-align: center;">
        <h3>예매내역 리스트 페이지</h3>
        <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>예매번호</th>
                      <th>분류</th>
                      <th>공연명</th>
                      <th>날짜</th>
                      <th>회차(시간대)</th>
                      <th>좌석 등급</th>
                      <th>가격</th>
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(item, index) in purchase_list" :key="index" :purchase_list="purchase_list">
                      <td>{{ index+1 }}</td>
                      <td>{{ item.category }}</td>
                      <!--예매내역 상세보기 페이지로 이동-->
                      <td>{{ item.title }}</td>
                      <td>{{ item.date }}</td>
                      <td>{{ item.time }}</td>
                      <td>{{ item.grade }}석</td>
                      <td>{{ item.price }}원</td>
                  </tr>
              </tbody>
          </table>
          <!--예매내역이 없을 경우 메세지 출력-->
          <!-- <div v-show="msg" id="msg">
            {{ this.msg }}
          </div> -->
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
        purchase_list: [],
        msg: ""
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/purchases/buyer/')
        .then(res => {
          // 헤더에 토큰 실어서 보내기
          console.log(res.data)
          this.purchase_list = res.data;
        })
        .catch(err => {
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