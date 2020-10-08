import { createInstance } from "./index.js";

const instance = createInstance();
const config = { 
  headers : { "jwt-auth-token" : localStorage.getItem("jwt-auth-token")
}};
function findAll(success, fail) {
    instance
      .get("/api/performance")
      .then(success)
      .catch(fail);
}

function findById(pid, success, fail) {
    instance
      .get("/api/performance/" + pid)
      .then(success)
      .catch(fail);
}
function finddateById(pid, success, fail) {
  instance
    .get("/api/performance/date/" + pid)
    .then(success)
    .catch(fail);
}
function findpriceById(pid, success, fail) {
  instance
    .get("/api/performance/price/" + pid)
    .then(success)
    .catch(fail);
}

function create(performanceDetail, success, fail, final) {
    instance
      .post("/api/performance", JSON.stringify(performanceDetail),config)
      .then(success)
      .catch(fail)
      .finally(final);
}

function imgUpload(file, success, fail){
    instance
      .post("/api/file", file)
      .then(success)
      .catch(fail)
}

function submit(sid, success, fail){
    instance
      .delete("/api/performanceSubmission/"+sid)
      .then(success)
      .catch(fail);
}
export{ findAll, findById, finddateById, findpriceById, create, imgUpload, submit }
