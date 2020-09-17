<template>
  <div>
    <h-nav></h-nav>
    <div id="main-overview" class="container">
      <div class="row">
        <div class="col-md-12">
          <h1>GuaranTicket</h1>
          <h4 class="mt-5">
            모두가 함께 만드는 공정한 티켓 거래
          </h4>
          <router-link
            :to="{ name: 'signup' }"
            class="btn btn-lg btn-primary mt-5"
            v-if="!$store.state.isSigned"
          >
            회원가입
          </router-link>
          <router-link
            :to="{ name: 'shop' }"
            class="btn btn-lg btn-primary mt-5"
            v-if="$store.state.isSigned"
          >
            Shop
          </router-link>
        </div>
      </div>
    </div>
    <step-flow></step-flow>
  </div>
</template>

<script>
import HNav from "../components/common/HNav";
import StepFlow from '@/components/common/StepFlow';

export default {
  components: {
    HNav,
    StepFlow
  }
};
</script>

<style>
</style>
