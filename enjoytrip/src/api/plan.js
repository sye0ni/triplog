import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/plan";

function gugun(param, success, fail) {
  local.get(`${url}/detail/attraction/gugun`, { params: param }).then(success).catch(fail);
}

function registPlan(param, success, fail) {
  local.post(`${url}`, param).then(success).catch(fail);
}

function getWishlist(userid, success, fail) {
  local.get(`${url}/wishlist/${userid}`).then(success).catch(fail);
}

function getAttractionList(param, success, fail) {
  local.get(`${url}/detail/attraction`, { params: param }).then(success).catch(fail);
}

function modifyWishlist(param, success, fail) {
  local.put(`${url}/wishlist`, param).then(success).catch(fail);
}

function registPlanNthDetail(param, success, fail) {
  local.post(`${url}/detail`, param).then(success).catch(fail);
}

function getPlan(planId, param, success, fail) {
  console.log("getplan", planId, param, "11111111");
  local.get(`${url}/${planId}`, { params: param }).then(success).catch(fail);
}

// userId
function getPlanList(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function getSidoGugunName(param, success, fail) {
  local.get(`${url}/location/name`, { params: param }).then(success).catch(fail);
}

function deletePlan(planId, success, fail) {
  console.log("delete : ", planId);
  local.delete(`${url}/${planId}`).then(success).catch(fail);
}

function modifyPlan(planId, param, success, fail) {
  console.log("modify : ", planId, param);
  local.put(`${url}/${planId}`, param).then(success).catch(fail);
}

function deleteWish(param, success, fail) {
  console.log("delete wish: ", param);
  local.delete(`${url}/wishlist`, { data: param }).then(success).catch(fail);
}

function getAttractionListById(param, success, fail) {
  local.get(`${url}/attractionList`, { params: param }).then(success).catch(fail);
}

export {
  gugun,
  registPlan,
  getWishlist,
  getAttractionList,
  modifyWishlist,
  registPlanNthDetail,
  getPlan,
  getPlanList,
  getSidoGugunName,
  deletePlan,
  modifyPlan,
  deleteWish,
  getAttractionListById,
};
