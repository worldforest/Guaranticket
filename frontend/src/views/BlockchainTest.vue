<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;width:800px; margin-top:8rem;">
        <h3>블록체인 입장 페이지</h3><br>
        <div style="margin-bottom:4rem;">
            <v-img class="mx-auto" width="30%" src="https://lh3.googleusercontent.com/proxy/MG6Zx4ihVOryAgQKla0nQBsLRa-LZRML2038UhDrLhQ21Aq8FSmXpfbkXhpsyGDafUHVaGfeVbwBCHrKYQMnFJaWq9j-JtGLlp34eg-uGvgBKu4H63o"></v-img>
            <p class="grey--text"><i>*시연을 위해 임의로 생성된 QR입니다.</i></p>
        </div>
        
        DB에 기록된 해당 티켓의 구매자 정보
        <table style="width:800px" class="mx-auto" border="1">
            <tr>
                <th>티켓 ID</th>
                <th>구매자 ID</th>
                <th>스마트 컨트랙트 주소</th>
            </tr>
            <tr>
                <td class="blue--text">{{ticket.tid}}</td>
                <td class="red--text">{{ticket.uid}}</td>
                <td class="green--text">{{ticket.contractAddress}}</td>
            </tr>
        </table>
        <br>
        블록체인에 기록된 해당 티켓의 구매자 정보
        <table style="width:800px" class="mx-auto" border="1">
            <tr>
                <th>스마트 컨트랙트 주소</th>
                <th>티켓 ID</th>
                <th>구매자 ID</th>
            </tr>
            <tr>
                <td class="green--text">{{ticket.contractAddress}}</td>
                <td class="blue--text">{{contractInfo.tid}}</td>
                <td class="red--text">{{contractInfo.uid}}</td>
            </tr>
        </table>
        <br>
        현재 로그인 중인 유저 정보
        <table style="width:800px" class="mx-auto" border="1">
            <tr>
                <th>유저 ID</th>
                <th>유저 이메일</th>
                <th>유저 패스워드</th>
            </tr>
            <tr>
                <td class="red--text">{{user.id}}</td>
                <td>{{user.email}}</td>
                <td>{{user.password}}</td>
            </tr>
        </table>
  

    </div>

  </div>
</template>

<script>
import HNav from "@/components/common/HNav";
import axios from "axios";
import { API_BASE_URL } from "../config";
import { findById, dispatch } from "../api/user.js";
import { setContratAddress, ticketDetail } from "../api/ticket.js";

import Web3 from "web3";
import { ADMIN_ACCOUNT, ADMIN_ACCOUNT_PRIVATE_KEY, KEY_VALUE_DATA, BLOCKCHAIN_URL } from '../config';
import { KEY_VALUE_ABI } from '../config/ABIs.js';

export default {
  components: {
    HNav
  },
  data: () => {
      return {
          ticket : {},
          contractInfo : {
              address : "",
              tid : "",
              uid : "",
          },
          user : {
          }
      }
  },
  created() {
    this.ticket = this.$route.params.ticket;

    var web3 = new Web3(BLOCKCHAIN_URL);
    var keyvaluestoreContract = new web3.eth.Contract(KEY_VALUE_ABI, this.ticket.contractAddress);
    keyvaluestoreContract.methods.getValue1(0).call(
        {from : ADMIN_ACCOUNT}
    ).then(
        response => {
            this.contractInfo.tid = response;
        }
    )

    keyvaluestoreContract.methods.getValue2(0).call(
        {from : ADMIN_ACCOUNT}
    ).then(
        response => {
            this.contractInfo.uid = response;
        }
    )

    dispatch(
        response => {
            var uid = response.data.data;
            findById(uid,
                response => {
                    this.user = response.data.data;
                },
                error => {
                    // console.log(error);
                }
            )
        },
        error => {
            // console.log(error);
        }
    )

    
  },

}
</script>

<style scoped>
 
</style>