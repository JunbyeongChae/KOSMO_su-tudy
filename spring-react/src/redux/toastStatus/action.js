export const SET_MSG = "TOAST_STATUS/SET_MSG"; //시그널
export const SET_FALSE = "TOAST_STATUS/SET_FALSE"; //시그널

export function setToastMsg(msg) {
    return {
        type: SET_MSG,
        msg: msg,
        bool: true,
    };
}

export function setToastFalse() {
    return {
        type: SET_FALSE,
        msg: "",
        bool: false,
    };
}