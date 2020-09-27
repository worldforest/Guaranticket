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
          <!--마이페이지(일반회원): 로그인 시 활성화-->
          <!--추후 user type=0,1,2으로 구분해서 네비바 구성 다르게 띄울 것!!-->
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
                    v-for="(item, index) in user_items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>

          <!--마이페이지(기업회원): 로그인 시 활성화-->
          <li class="nav-item" v-if="$store.state.isSigned">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn text
                    v-bind="attrs"
                    v-on="on"
                    style="color: white; font-weight: bold; margin-top: 2px;"
                  >
                  기업회원
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in biz_items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>

          <!--마이페이지(관리자): 로그인 시 활성화-->
          <li class="nav-item" v-if="$store.state.isSigned">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <v-btn text
                    v-bind="attrs"
                    v-on="on"
                    style="color: white; font-weight: bold; margin-top: 2px;"
                  >
                  관리자
                  </v-btn>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in admin_items"
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
    user_items: [
      { title: '예매내역 확인', to: '/purchaselist' },
      { title: '판매내역 관리', to: '/selllist' },
      { title: '회원정보 수정', to: '/updateprofile' },
      { title: '로그아웃', to: '/logout' }
    ],
    biz_items: [
      { title: '공연등록', to: '/performance/submission' },
      { title: '로그아웃', to: '/logout' }
    ],
    admin_items: [
      { title: '가입신청 관리', to: '/confirmuser' },
      { title: '공연등록 관리', to: '/confirmperformance' },
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
