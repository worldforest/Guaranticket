import { createInstance } from "./index.js";

const instance = createInstance();

function findAll(pid,date,time,success,fail){
    instance
    .get("api/ticket/pid/"+pid+"/date/"+date+"/time/"+time)
    .then(success)
    .catch(fail);
}

function create(body, success, fail){
    instance
    .post("/api/ticket",JSON.stringify(body))
    .then(success)
    .catch(fail);
}

function ticketDetail(tid, success,fail){
    instance
    .get("/api/ticket/tid/"+tid)
    .then(success)
    .catch(fail);
}
export{ findAll,create,ticketDetail }