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
            <router-link class="nav-link" to="/concert"
              >콘서트</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/musical">뮤지컬</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/sports">스포츠</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/deallist">티켓거래</router-link>
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/login">로그인</router-link>
          </li>
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/chat">채팅하기</router-link>
          </li>
          <li class="nav-item" v-if="$store.state.isSigned">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn text
                    v-bind="attrs"
                    v-on="on"
                    style="color: white; font-weight: bold; margin-top: 2px;"
                  >
                  마이페이지
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data: () => ({
    items: [
      { title: '예매내역 확인', to: '/purchaselist' },
      { title: '판매내역 관리', to: '/selllist' },
      { title: '회원정보 수정', to: '/updateprofile' },
      { title: '로그아웃', to: '/logout' }
    ]
  }),
  methods: {
    movePages(link){
      this.$router.push(link);
    }
  }
};
</script>

<style>
  #nav-icon {
    height: 40px;
    padding-right: 0.5rem;
  }
</style>
