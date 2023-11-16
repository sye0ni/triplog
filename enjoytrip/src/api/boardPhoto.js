import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/board/photo";

function listPhoto(page, success, fail) {
    local.get(`${url}`, { params: page }).then(success).catch(fail);
}


export {
    listPhoto
};
