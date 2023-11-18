import { localAxios, localFileAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const localFile = localFileAxios();

const url = "/board/photo";

function listPhoto(page, success, fail) {
    local.get(`${url}`, { params: page }).then(success).catch(fail);
}

// 시군 받아오기 
function getSigun(success, fail) {
    local.get(`${url}/sigun`).then(success).catch(fail);
}

// 게시글 등록 
function writePhoto(photo, success, fail) {
    localFile.post(`${url}`, photo)
        .then(success).catch(fail);
}

// 게시글 단건 조회 
function getPhoto(photoId, success, fail) {
    local.get(`${url}/${photoId}`).then(success).catch(fail);
}

// /board/photo/{photoid}/comment -> 댓글 조회 
function getComments(photoId, success, fail) {
    local.get(`${url}/${photoId}/comment`).then(success).catch(fail);
}

// /board/photo/{photoid}
function modifyPhoto(photoid, content, success, fail) {
    local
        .put(`${url}/${photoid}`, JSON.stringify(content))
        .then(success)
        .catch(fail);
}

// /{photoid}/comment
function writeComment(photoid, comment, success, fail) {
    local
        .post(`${url}/${photoid}/comment`, JSON.stringify(comment))
        .then(success)
        .catch(fail);
}

// /{photoid}
function deletePhoto(photoid, success, fail) {
    local.delete(`${url}/${photoid}`).then(success).catch(fail);
}

// /boards/photo/{photoid}/comment/{commentid}
function modifyComment(photoid, commentid, comment, success, fail) {
    local.put(`${url}/${photoid}/comment/${commentid}`, JSON.stringify(comment)).then(success).catch(fail);
}

// /boards/photo/{photoid}/comment/{commentid}
function deleteComment(photoid, commentid, success, fail) {
    local.delete(`${url}/${photoid}/comment/${commentid}`).then(success).catch(fail);
}


export {
    listPhoto, getSigun, writePhoto, getPhoto, getComments, modifyPhoto, writeComment, deletePhoto, modifyComment, deleteComment
};
