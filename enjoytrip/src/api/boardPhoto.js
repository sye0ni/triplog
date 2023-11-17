import { localAxios, localPostAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const local2 = localPostAxios();

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
    local2.post(`${url}`, photo)
        .then(success).catch(fail);
}

export {
    listPhoto, getSigun, writePhoto
};
