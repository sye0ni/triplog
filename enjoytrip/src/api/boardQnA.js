import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/board/qna";

function listArticle(param, success, fail) {
  local.get(`${url}`, { params: param }).then(success).catch(fail);
}

function getArticle(articleno, success, fail) {
  local.get(`${url}/${articleno}`).then(success).catch(fail);
}

// /board/qna/{qnaid}/comment
function getComments(articleno, success, fail) {
  local.get(`${url}/${articleno}/comment`).then(success).catch(fail);
}

// /board/qna/{qnaid}
function modifyQuestion(articleno, article, success, fail) {
  local
    .put(`${url}/${articleno}`, JSON.stringify(article))
    .then(success)
    .catch(fail);
}

// /{qnaid}/comment/{commentid}
function modifyComment(articleno, commentid, comment, success, fail) {
  local
    .put(`${url}/${articleno}/comment/${commentid}`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

// /{qnaid}/comment
function writeComment(articleno, comment, success, fail) {
  local
    .post(`${url}/${articleno}/comment`, JSON.stringify(comment))
    .then(success)
    .catch(fail);
}

// /{qnaid}
function deleteQuestion(articleno, success, fail) {
  local.delete(`${url}/${articleno}`).then(success).catch(fail);
}

// /{qnaid}/comment/{commentid}
function deleteComment(articleno, commentid, success, fail) {
  local
    .delete(`${url}/${articleno}/comment/${commentid}`)
    .then(success)
    .catch(fail);
}
function writeQuestion(article, success, fail) {
  local.post(`${url}`, JSON.stringify(article)).then(success).catch(fail);
}
export {
  listArticle,
  getArticle,
  getComments,
  modifyQuestion,
  modifyComment,
  writeComment,
  deleteQuestion,
  deleteComment,
  writeQuestion,
};
