// userService.js
import { createInstance } from "./index.js";

const instance = createInstance();

function findByEmail(email, success, fail) {
  instance
    .get("/api/users/email/" + email)
    .then(success)
    .catch(fail);
}

function findById(id, success, fail) {
  instance
    .get("/api/users/" + id)
    .then(success)
    .catch(fail);
}

function signup(email, password, name, businessNumer, companyName, representativeName, gender, birth, success, fail) {
  const user = {
    email : email,
    password : password,
    name : name,
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
  instance
    .put("/api/users", JSON.stringify(user))
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
export { findByEmail, findById, signup, login, update, findpw, sendEmail };
