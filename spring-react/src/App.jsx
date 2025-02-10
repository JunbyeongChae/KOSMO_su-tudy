import { Route, Routes } from "react-router" 
import  HomePage from "./components/pages/HomePage";
import LoginPage from "./components/auth/LoginPage";
import ReBoardDBList from "./components/reboard/ReBoardDBList";
import ReBoardDBDetail from "./components/reboard/ReBoardDBDetail";
import ReBoardDBUpdate from "./components/reboard/ReBoardDBUpdate";
import ReBoardDBWrite from "./components/reboard/ReBoardDBWrite";
import { useEffect } from "react";
import { boardDeleteDB } from "./service/dbLogic";

const App = () => {
  useEffect(()=>{
    const aysncDB = async() => {
/*
      const board = {
        "gubun":null, //제목:b_title, 내용:b_content, 작성자:MEM_NICKNAME
        "keyword":null
      }
*/
      //await boardListDB(board);
      const board = {
        "b_title":"글제목",
        "b_content":"글내용",
        "email":"abc@hot.com"
      }
      //await boardInsertDB(board);
      //await boardUpdateDB(board);
      await boardDeleteDB(1);
    }
    aysncDB()
  },[])
  return (
    <>
      <Routes>
          <Route path="/" exact={true} element={<HomePage />}/>
          <Route path="/login" exact={true} element={<LoginPage />}/>
          <Route path="/reboard" exact={true} element={<ReBoardDBList />}/>
          <Route path="/reboard/:b_no" exact={true} element={<ReBoardDBDetail />}/>
          <Route path="/reboard/update/:b_no" exact={true} element={<ReBoardDBUpdate />}/>
          <Route path="/reboard/write" exact={true} element={<ReBoardDBWrite />}/>
      </Routes>
    </>
  );
}

export default App;
/*
  - http://localhost:3000/index.html
  - <div id="root"></div>
  - root에 대한 정보는 index.js에서 참조 한다.
  document.querySelector("#root")
  - index.js에서 App import한다
  - App.jsx의 return에 있는 태그가 화면 출력된다.
  - 그런데 이번에는 App.jsx에 메뉴를 클릭했을 때 보여줄 페이지에
  대한 링크를 걸어 준다.
*/