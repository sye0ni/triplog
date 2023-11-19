import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/plan";

function gugun(param, success, fail) {
  local
    .get(`${url}/detail/attraction/gugun`, { params: param })
    .then(success)
    .catch(fail);
}

function registPlan(param, success, fail) {
  local.post(`${url}`, param).then(success).catch(fail);
}

function getWishlist(userid, success, fail) {
  local.get(`${url}/wishlist/${userid}`).then(success).catch(fail);
}

function getAttractionList(param, success, fail) {
  local
    .get(`${url}/detail/attraction`, { params: param })
    .then(success)
    .catch(fail);
}

function modifyWishlist(param, success, fail) {
  local.put(`${url}/wishlist`, param).then(success).catch(fail);
}

export { gugun, registPlan, getWishlist, getAttractionList, modifyWishlist };
