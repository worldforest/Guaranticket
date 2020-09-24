import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import HNav from "./components/common/HNav.vue";
import HBreadcumb from "./components/common/HBreadcrumb.vue";
import vuetify from './plugins/vuetify';

// firebase 설정------------------------------------------
import * as firebase from 'firebase/app';

// Required for side-effects
require("firebase/firestore");
var firebaseConfig = {
  apiKey: "AIzaSyCfJYHRsxnrbICFf8snQmn79sEwmq2ECLM",
  authDomain: "guaranticket.firebaseapp.com",
  databaseURL: "https://guaranticket.firebaseio.com",
  projectId: "guaranticket",
  storageBucket: "guaranticket.appspot.com",
  messagingSenderId: "965268289115",
  appId: "1:965268289115:web:a1e8bb1ef52cc7e095717e",
  measurementId: "G-EVBRV2CM7D"
};
firebase.initializeApp(firebaseConfig);

// Initialize Cloud firestore through firebase
var db = firebase.firestore();

window.db=db;
//--------------------------------------------------------

Vue.config.productionTip = false;
Vue.filter("truncate", function(text, length, clamp) {
  if (text) {
    clamp = clamp || "...";
    return text.length > length ? text.slice(0, length) + clamp : text;
  }
});

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");

Vue.component("HNav", HNav);
Vue.component("HBreadcrumb", HBreadcumb);
