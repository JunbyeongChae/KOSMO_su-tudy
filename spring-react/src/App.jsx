import { Route, Routes } from "react-router" 
import  HomePage from "./components/pages/HomePage";
import LoginPage from "./components/auth/LoginPage";
import ReBoardDBList from "./components/reboard/ReBoardDBList";
import ReBoardDBDetail from "./components/reboard/ReBoardDBDetail";
import ReBoardDBWrite from "./components/reboard/ReBoardDBWrite";
import CalendarTest from "./components/schedule/CalendarTest";
import LoginSuccess from "./components/auth/LoginSuccess";
import ReBoardDBUpdate from "./components/reboard/ReBoardDBUpdate";


const App = () => {
  return (
    <>
      <Routes>
          <Route path="/" exact={true} element={<HomePage />}/>
          <Route path="/schedule" exact={true} element={<CalendarTest />}/>
          <Route path="/login" exact={true} element={<LoginPage />}/>
          {/* http://localhost:3000/login-success?email="+kakaoProfile.getKakao_account().getEmail() */}
          <Route path="/login-success" exact={true} element={<LoginSuccess />}/>
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