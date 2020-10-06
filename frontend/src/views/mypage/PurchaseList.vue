<template>
  <div>
    <h-nav></h-nav>
      <div class="container" style="text-align: center;">
        <h3>예매내역 목록</h3>
        <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>예매번호</th>
                      <th>분류</th>
                      <th>공연명</th>
                      <th>날짜</th>
                      <th>회차(시간대)</th>
                      <th>좌석</th>
                      <th>블록에 기록하기</th>
                      <!-- <th>가격</th> -->
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(item, index) in purchase_list" :key="index" :purchase_list="purchase_list">
                      <td>{{ index+1 }}</td>
                      <td>{{ item.category }}</td>
                      <!--예매내역 상세보기 페이지로 이동-->
                      <td><router-link :to="{ name: 'purchasedetail', params: {tid: item.tid}}">{{ item.title }}</router-link></td>
                      <td>{{ item.date }}</td>
                      <td>{{ item.time }}</td>
                      <td>{{ item.grade }}석 {{ item.seat }}</td>
                      <td v-if="!!item.contractAddress">{{ item.contractAddress }}</td>
                      <td v-else><v-btn @click="deployContract(item)" color="success">기록하기</v-btn></td>
                      <!-- <td>{{ item.price }}원</td> -->
                  </tr>
              </tbody>
          </table>
          <!--토큰 해결될 때까지 예매내역 상세정보 테스트할 링크-->
          <!-- <td><router-link :to="{ name: 'purchasedetail', params: {tid: 1}}">ㅌㅅㅌ</router-link></td> -->
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

import Web3 from "web3";
import { ADMIN_ACCOUNT, ADMIN_ACCOUNT_PRIVATE_KEY, KEY_VALUE_DATA, BLOCKCHAIN_URL } from '../../config';
import { KEY_VALUE_ABI } from '../../config/ABIs.js';

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
        .get(API_BASE_URL + '/api/ticket/uid', { headers : { "jwt-auth-token" : localStorage.getItem("jwt-auth-token")}})
        .then(res => {
          res.data.forEach(ticket => {
            var seat = "";
            if(ticket.seatNumber > 18) {
              ticket.seatNumber -= 18;
            }
            if(ticket.seatNumber % 6 == 0) { // 6의 배수일 때
              seat = (ticket.seatNumber/6) + "행 6열";
            } else {
              seat = parseInt(ticket.seatNumber/6)+1 + "행 " + (ticket.seatNumber%6) + "열";
            }
            ticket["seat"] = seat;
            switch (ticket.category) {
              case "0":
                ticket.category = '콘서트';
                break;
              case "1":
                ticket.category = '뮤지컬';
                break;
              case "2":
                ticket.category = '스포츠';
                break;
            }
            this.purchase_list.push(ticket);
          });
        })
        .catch(err => {
          console.log("created axios get method error!")
        })
  },
  methods : {
    async deployContract(ticket){
      console.log(ticket)
      var web3 = new Web3(BLOCKCHAIN_URL);
      var keyvaluestoreContract = new web3.eth.Contract(KEY_VALUE_ABI);
      var keyvaluestore = keyvaluestoreContract.deploy({data:KEY_VALUE_DATA});
      var options = {
        data : keyvaluestore.encodeABI(),
        gas : await keyvaluestore.estimateGas(),
      }
      var signedTransaction = await web3.eth.accounts.signTransaction(options, ADMIN_ACCOUNT_PRIVATE_KEY);
      var hash = await web3.eth.sendSignedTransaction(signedTransaction.rawTransaction);
      ticket["contractAddress"] = hash.contractAddress;
      console.log(ticket)
      keyvaluestoreContract.options.address = hash.contractAddress;
      keyvaluestoreContract.methods.setValue(ticket.tid+"", ticket.uid+"").send(
        {from : ADMIN_ACCOUNT}
      ).then(
        response => {
          console.log(response)
          keyvaluestoreContract.methods.getValue1(0).call(
            {from : ADMIN_ACCOUNT}
          ).then(console.log)
          keyvaluestoreContract.methods.getValue2(0).call(
            {from : ADMIN_ACCOUNT}
          ).then(console.log)
        }
      )
    },
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