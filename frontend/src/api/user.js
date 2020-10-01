// userService.js
import { createInstance } from "./index.js";

const instance = createInstance();
const config = { 
  headers : { "jwt-auth-token" : localStorage.getItem("jwt-auth-token")
}};

function dispatch(success, fail){
  instance
    .get("/api/users/info" , config)
    .then(success)
    .catch(fail)
}

function findByEmail(email, success, fail) {
  instance.defaults.headers["jwt-auth-token"] = window.localStorage.getItem("jwt-auth-token");
  instance
    .get("/api/users/email/" + email)
    .then(success)
    .catch(fail);
}

function findById(id, success, fail) {
  instance.defaults.headers["jwt-auth-token"] = window.localStorage.getItem("jwt-auth-token");
  instance
    .get("/api/users/" + id)
    .then(success)
    .catch(fail);
}

function signup(email, password, name, phone, gender, birth, businessNumer, companyName, representativeName, success, fail) {
  const user = {
    email : email,
    password : password,
    name : name,
    phone : phone,
    businessNumer : businessNumer,
    companyName : companyName,
    representativeName : representativeName,
    gender : gender,
    birth : birth,
  }

  instance
    .post("/api/users", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function login(email, password, success, fail) {
  instance.defaults.headers["jwt-auth-token"] = window.localStorage.getItem("jwt-auth-token");
  const body = {
    email: email,
    password: password
  };

  instance
    .post("/api/users/login", JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function update(user, success, fail) {
  instance.defaults.headers["jwt-auth-token"] = window.localStorage.getItem("jwt-auth-token");
  instance
    .put("/api/users", JSON.stringify(user))
    .then(success)
    .catch(fail);
}

function updatePwd(originPassword, newPassword, success, fail){
  const body = {
    originPassword : originPassword,
    newPassword : newPassword
  };

  instance
    .put("/api/users/pw", JSON.stringify(body), config)
    .then(success)
    .catch(fail);
}

// 비밀번호찾기
function findpw(name, email, success, fail) {
  const user = {
    name: name,
    email: email
  };
  instance
    .post("/api/users/findpw", JSON.stringify(user))
    .then(success)
    .catch(fail)
}
// 이메일전송
function sendEmail(name, email, success, fail) {
  const user = {
    name: name,
    email: email
  };
  instance
    .post("/api/users/sendemail", JSON.stringify(user))
    .then(success)
    .catch(fail)
}

// SMS 인증
function sendSMS(phone, success, fail){
  instance
    .get("/api/users/sms?phoneNumber=" + phone)
    .then(success)
    .catch(fail)
}
export { dispatch, findByEmail, findById, signup, login, update, updatePwd, findpw, sendEmail, sendSMS };
