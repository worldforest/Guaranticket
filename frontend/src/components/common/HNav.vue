<template>
  <nav class="navbar navbar-expand-lg fixed-top navbar-dark bg-primary">
    <div class="container">
      <img id="nav-icon" src="../../../public/images/guaranticket.svg">
      <!-- <div>Icons made by <a href="https://www.flaticon.com/authors/freepik" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a></div> -->
      <router-link class="navbar-brand" to="/">GuaranTicket</router-link>
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/explorer/dashboard"
              >공연가기</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/shop">티켓거래</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link
              class="nav-link"
              to="/mypage/wallet_create"
              v-if="!$store.state.user.walletAddress"
              >MyPage</router-link
            >
            <router-link
              class="nav-link"
              to="/mypage/wallet_info"
              v-if="$store.state.user.walletAddress"
              >MyPage</router-link
            >
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/login">로그인</router-link>
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/register">회원가입</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link class="nav-link" to="/logout">로그아웃</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {};
</script>

<style>
#nav-icon {
  height: 40px;
  padding-right: 0.5rem;
}
</style>
