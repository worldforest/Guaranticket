<template>
<div class="container">
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <router-link to="/">Escrow | STARMIX</router-link>
        <div class="mt-4">
          <div class="form-group">
            <label for="name">이름</label>
            <input
              type="text"
              class="form-control"
              id="name"
              v-model="user.name"
              placeholder="이름"
            />
          </div>
          <div class="form-group">
            <label for="email">이메일</label>
            <input
              type="email"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            />
          </div>
          <button type="button" class="btn btn-primary" v-on:click="findpw">
            비밀번호 찾기
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { findpw } from "../api/user.js";
import { sendEmail } from "../api/user.js";

export default {
  data() {
    return {
      user: {
        name : "",
        email: "",
      }
    };
  },
  methods: {
    sendEmail(){
      alert("해당 이메일로 임시비밀번호를 전송 하였습니다.")
      this.$router.push("/login");
      sendEmail(
        this.user.name,
        this.user.email,
        function(response) {
        },
        function(error) {
        }
      );
    },
    findpw(){
      const scope = this;
      findpw(
        this.user.name,
        this.user.email,
        function(response) {
          scope.sendEmail();
        },
        function(error) {
          if(error.response != 404)
            alert("이름 혹은 이메일이 일치하지 않습니다.");
        }
      );
    },
  }
};
</script>

<style></style>
