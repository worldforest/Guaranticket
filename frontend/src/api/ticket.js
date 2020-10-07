import { createInstance } from "./index.js";

const instance = createInstance();
const config = { 
    headers : { "jwt-auth-token" : localStorage.getItem("jwt-auth-token")
}};

function findAll(pid,date,time,success,fail){
    instance
    .get("api/ticket/pid/"+pid+"/date/"+date+"/time/"+time)
    .then(success)
    .catch(fail);
}

function pay(pid, seatNumber, date, time, grade, price, success, fail){
    const ticket = {
        pid : pid,
        seatNumber : seatNumber,
        date : date,
        time : time,
        grade : grade,
        price : price
    };
    instance
    .post("/api/ticket",JSON.stringify(ticket),config)
    .then(success)
    .catch(fail);
}

function ticketDetail(tid, success,fail){
    instance
    .get("/api/ticket/tid/"+tid)
    .then(success)
    .catch(fail);
}

function setContratAddress(tid, contractAddress, success, fail){
    const param = {
        contractAddress : contractAddress
    };
    instance
    .put("/api/ticket/contract/" + tid, JSON.stringify(param), config)
    .then(success)
    .catch(fail);
}

export{ setContratAddress, findAll, pay, ticketDetail }