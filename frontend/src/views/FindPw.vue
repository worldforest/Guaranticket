<template>
<div class="container" style="text-align: center;">
  <h-nav></h-nav>
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <h3 style="font-weight: bold;">비밀번호 찾기</h3>
        <h6>회원가입 시 등록한 정보를 입력해주세요.</h6>
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
            임시비밀번호 발급
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { findpw } from "../api/user.js";
import { sendEmail } from "../api/user.js";
import HNav from "@/components/common/HNav";

export default {
  components: {
    HNav
  },
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

<style scoped>
  label {
    float: left
  }
  button {
    color: white;
    background: #FF4155;
    border: #FF4155
  }
</style>
