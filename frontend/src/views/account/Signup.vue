<template>
<div class="text-center" style="margin-top:17rem;">
  <h-nav></h-nav>
    <div class="text-h4">회원가입</div>
    <div class="text-h6">가입 종류를 선택해주세요</div>
    <div class="my-16" style="margin-left: 640px;">
      <v-btn @click="goSignupUser()" class="ma-2" width="100" height="100">
        <v-card width="100" height="100" color="#FF4155" flat>
          <v-card-title class="pb-2">
            <v-icon x-large color="white" class="ma-auto">mdi-account-multiple</v-icon>
          </v-card-title>
          <v-card-text class="py-0 white--text">
            일반회원
          </v-card-text>
        </v-card>
      </v-btn>
      <v-btn @click="goSignupBiz()" class="ma-2" width="100" height="100">
        <v-card width="100" height="100" color="#FF4155" flat>
          <v-card-title class="pb-2">
            <v-icon x-large color="white" class="ma-auto">mdi-domain</v-icon>
          </v-card-title>
          <v-card-text class="py-0 white--text">
            기업회원
          </v-card-text>
        </v-card>
      </v-btn>
    </div>
  </div>
</template>

<script>
import { signup } from "@/api/user.js";

export default {
  data() {
    return {
    };
  },
  methods: {
    goSignupUser(){
      this.$router.push("/SignupUser");
    },
    goSignupBiz(){
      this.$router.push("/SignupBiz");
    },
  }
};
</script>

<style></style>
