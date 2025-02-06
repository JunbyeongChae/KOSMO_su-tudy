import axios from "axios";

//http://localhost:8000/api/board/boardList
export const boardListDB = async (board) => {
  console.log(JSON.stringify(board));
  try {
    const res = await axios({
      method: "get",
      url: process.env.REACT_APP_SPRING_IP + "api/board/boardList",
      params: board
    });
    //스프링에서 응답이 성공적으로 나오면 -200OK
    return res;
  } catch (error) {
    throw error;
  }//////////////////////////////////////end of try-catch
}//////////////////////////////////////////end of boardListDB

//http://localhost:8000/api/board/boardInsert
export const boardInsertDB = async (board) => {
  console.log(JSON.stringify(board));
  try {
    const res = await axios({
      method: "post",
      url: process.env.REACT_APP_SPRING_IP + "api/board/boardInsert",
      data: board
    });
    return res;
  } catch (error) {
    throw error;
  }//////////////////////////////////////end of try-catch
}//////////////////////////////////////////end of boardInsertDB

//http://localhost:8000/api/board/boardUpdate
export const boardUpdateDB = async (board) => {
  console.log(JSON.stringify(board));
  try {
    const res = await axios({
      method: "put",
      url: process.env.REACT_APP_SPRING_IP + "api/board/boardUpdate",
      data: board
    });
    return res;
  } catch (error) {
    throw error;
  }//////////////////////////////////////end of try-catch
}//////////////////////////////////////////end of boardUpdateDB

//http://localhost:8000/api/board/boardDelete?b_no=
export const boardDeleteDB = async (b_no) => {
  console.log(b_no);
  try {
    const res = await axios({
      method: "delete",
      url: process.env.REACT_APP_SPRING_IP + "api/board/boardDelete?b_no=" + b_no
    });
    return res;
  } catch (error) {
    throw error;
  }//////////////////////////////////////end of try-catch
}//////////////////////////////////////////end of boardDeleteDB