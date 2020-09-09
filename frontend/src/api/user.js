// userService.js
import { createInstance } from "./index.js";

const instance = createInstance();

function findById(id, success, fail) {
  instance
    .get("/api/users/" + id)
    .then(success)
    .catch(fail);
}

function signup(email, name, password, success, fail) {
  const user = {
    email: email,
    name: name,
    password: password
  };

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
export { findById, signup, login, update, findpw, sendEmail };
