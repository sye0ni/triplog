import { localAxios } from "../util/http-commons";

const local = localAxios(); // axios 객체
const url = "/board/photo";

function listPhoto(page, userId, success, fail) {
    local.defaults.headers["Authorization"] = `Bearer ${userId}`;
    local.get(`${url}`, { params: page }).then(success).catch(fail);
}


export {
    listPhoto
};
