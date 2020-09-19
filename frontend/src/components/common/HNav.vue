<template>
  <nav class="navbar navbar-expand fixed-top navbar-dark basic-color">
    <div class="container">
      <img id="nav-icon" src="../../../public/images/guaranticket.svg">
      <router-link class="navbar-brand" to="/">GuaranTicket</router-link>
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/explorer/dashboard"
              >콘서트</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/shop">뮤지컬</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/shop">스포츠</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/shop">거래하기</router-link>
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/login">로그인</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <router-link class="nav-link" to="/logout">MyPage</router-link>
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
