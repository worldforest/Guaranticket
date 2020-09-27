import { createInstance } from "./index.js";

const instance = createInstance();

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

function create(body, success, fail, final) {
    instance
      .post("/api/performance", JSON.stringify(body))
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
export{ findAll, findById, create, submit, imgUpload }