import { combineReducers } from "redux";
import userAuth from "./userAuth/reducer";
import { toastStatus } from "./toastStatus/state";
// 여러 reducer를 사용하는 경우 reducer를 하나로 묶어주는 메소드 이다.
// store에 저장되는 reducer는 오직 1개 이다.
const rootReducer = combineReducers({
    userAuth,
    toastStatus
})

export default rootReducer