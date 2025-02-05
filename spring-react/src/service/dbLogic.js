import axios from "axios";

//http://localhost:8000/api/board/boardList
export const boardListDB = (board) => {
  console.log(JSON.stringify(board));
  return new Promise((resolve, reject) => {
    try {
      const res = axios({
        method: "get",
        url: process.env.REACT_APP_SPRING_IP + "api/board/boardList",
        params: board
      })
      //스프링에서 응답이 성공적으로 나오면 -200OK
      resolve(res);
    } catch (error) {
      reject(error);
    }//////////////////////////////////////end of try-catch
  })///////////////////////////////////////end of Promise
}//////////////////////////////////////////end of boardListDB