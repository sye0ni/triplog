import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/plan";

function gugun(param, success, fail) {
  local.get(`${url}/detail/attraction/gugun`, { params: param }).then(success).catch(fail);
}

function registPlan(param, success, fail) {
  local.post(`${url}`, param).then(success).catch(fail);
}

export { gugun, registPlan };
