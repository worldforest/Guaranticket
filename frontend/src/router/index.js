import Vue from "vue";
import VueRouter from "vue-router";
//계정(로그인,회원가입)
import Login from "@/views/Login.vue";
import Signup from "@/views/Signup.vue";
import SignupUser from "@/views/SignupUser.vue";
import SignupBiz from "@/views/SignupBiz.vue";
import Home from "@/views/Home.vue";
import store from "@/store";
import Shop from "@/views/Shop.vue";
import MyPage from "@/views/MyPage.vue";
import Item from "@/views/Item.vue";
import Explorer from "@/views/Explorer.vue";
import Escrow from "@/views/Escrow.vue";
//티켓거래
import DealList from "@/views/deal/DealList.vue";
import DealRegister from "@/views/deal/DealRegister.vue";
import DealDetail from "@/views/deal/DealDetail.vue";
//마이페이지(일반회원)
import PurchaseList from "@/views/mypage/PurchaseList.vue";
import SellList from "@/views/mypage/SellList.vue";
import UpdateProfile from "@/views/mypage/UpdateProfile.vue";
//비밀번호찾기
import FindPw from "@/views/FindPw.vue";
//공연
import Concert from "@/views/Concert.vue";
import Musical from "@/views/Musical.vue";
import Sports from "@/views/Sports.vue";
//공연 상세
import PerformanceDetail from "@/views/PerformanceDetail";
import PDetail from "@/views/performanceDetail/Detail";
import PLocation from "@/views/performanceDetail/Location";
//채팅
import Chat from "@/views/Chat.vue";
// import { component } from "vue/types/umd";

Vue.use(VueRouter);

const routes = [
  //마이페이지
  {
    path: "/purchaselist",
    name: "purchaselist",
    component: PurchaseList
  },
  {
    path: "/selllist",
    name: "selllist",
    component: SellList
  },
  {
    path: "/updateprofile",
    name: "updateprofile",
    component: UpdateProfile
  },
  //티켓거래
  {
    path: "/deallist",
    name: "deallist",
    component: DealList
  },
  {
    path: "/dealregister",
    name: "dealregister",
    component: DealRegister
  },
  {
    path: "/dealdetail/:did",
    name: "dealdetail",
    component: DealDetail
  },
  //비밀번호찾기
  {
    path: "/findpw",
    name: "findpw",
    component: FindPw,
  },
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    name: "login",
    path: "/login",
    component: Login,
  },
  {
    path: "/register",
    name: "signup",
    component: Signup,
  },
  {
    path: "/signupUser",
    name: "signupUser",
    component: SignupUser,
  },
  {
    path: "/signupBiz",
    name: "signupBiz",
    component: SignupBiz,
  },
  {
    path: "/logout",
    name: "logout",
    beforeEnter(to, from, next) {
      store.commit("logout");
      alert("로그아웃 되었습니다.");
      next("/");
    },
  },
  {
    path: "/concert",
    name: "concert",
    component: Concert,
  },
  {
    path: "/musical",
    name: "musical",
    component: Musical,
  },
  {
    path: "/sports",
    name: "sports",
    component: Sports,
  },
  {
    path: "/chat",
    name: "chat",
    component: Chat,
  },
  {
    name: "shop",
    path: "/shop",
    component: Shop,
    children: [
      {
        path: "",
        component: () => import("@/components/shop/All.vue"),
      },
      {
        path: "digital",
        component: () => import("@/components/shop/Digital.vue"),
      },
      {
        path: "child",
        component: () => import("@/components/shop/Child.vue"),
      },
      {
        path: "hobby",
        component: () => import("@/components/shop/Hobby.vue"),
      },
    ],
    redirect: () => {
      return "/shop";
    },
  },
  {
    name: "mypage",
    path: "/mypage",
    component: MyPage,
    children: [
      {
        name: "mypage.wallet.create",
        path: "wallet_create",
        component: () => import("../components/mypage/WalletCreate.vue"),
      },
      {
        name: "mypage.wallet.info",
        path: "wallet_info",
        component: () => import("../components/mypage/WalletInfo.vue"),
      },
      {
        name: "mypage.items",
        path: "/mypage/items",
        component: () => import("../components/mypage/MyItems.vue"),
      },
      {
        name: "mypage.password",
        path: "/mypage/password",
        component: () => import("../components/mypage/Password.vue"),
      },
    ],
    redirect: () => {
      return "/mypage/items";
    },
  },
  {
    name: "item",
    path: "/item",
    component: Item,
    children: [
      {
        name: "item.create",
        path: "create",
        component: () => import("../components/item/ItemCreate.vue"),
      },
      {
        name: "item.detail",
        path: "detail/:id",
        component: () => import("../components/item/ItemDetail.vue"),
      },
      {
        name: "item.purchase",
        path: "purchase/:id",
        component: () => import("../components/item/ItemPurchase.vue"),
      },
    ],
  },
  {
    name: "escrow",
    path: "/escrow",
    component: Escrow,
    children: [
      {
        name: "escrow.purchase.detail",
        path: "purchase/detail",
        component: () => import("@/components/escrow/PurchaseTxDetail.vue"),
      },
      {
        name: "escrow.sale.detail",
        path: "sale/detail",
        component: () => import("@/components/escrow/SaleTxDetail.vue"),
      },
      {
        name: "escrow.history",
        path: "history/:id",
        component: () => import("@/components/escrow/EscrowHistory.vue")
      }
    ],
  },
  {
    name: "explorer",
    path: "/explorer",
    component: Explorer,
    children: [
      {
        name: "explorer.dashboard",
        path: "dashboard",
        component: () => import("../components/explorer/Dashboard.vue"),
      },
      {
        name: "explorer.block",
        path: "blocks",
        component: () => import("../components/explorer/BlockListView.vue"),
      },
      {
        name: "explorer.block.detail",
        path: "block/:blockNumber",
        component: () => import("../components/explorer/BlockDetail.vue"),
      },
      {
        name: "explorer.tx",
        path: "txes",
        component: () => import("../components/explorer/TxListView.vue"),
      },
      {
        name: "explorer.tx.detail",
        path: "tx/:hash",
        component: () => import("../components/explorer/TxDetail.vue"),
      },
    ],
  },
  {
    name:"performanceDetail",
    path: "/performanceDetail/:pid",
    component: PerformanceDetail,
    // props: (route) => ({pid: route.pis}),
    children: [
      {
        name: "performanceDetail.Detail",
        path: "Detail",
        component: PDetail
      },
      {
        name: "performanceDetail.Location",
        path: "Location",
        component: PLocation
      }
    ],
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

router.beforeEach((to, from, next) => {
  let isSigned = store.state.isSigned;
  let isAvailableToGuest =
    ["/", "/login", "/register", "/findpw", "/SignupUser", "/SignupBiz","/concert","/musical","/sports","/chat"].includes(to.path) ||
    to.path.startsWith("/explorer") ||to.path.startsWith("/performanceDetail/") ;

  // 로그인도 하지 않았고 게스트에게 허용된 주소가 아니라면 로그인 화면으로 이동한다.
  if (!isSigned && !isAvailableToGuest) {
    alert("로그인을 하신 뒤에 사용이 가능합니다.");
    next("/login");
  } else {
    next();
  }
});

export default router;
