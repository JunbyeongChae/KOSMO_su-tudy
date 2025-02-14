import React, { useEffect, useState } from 'react'
import { Container, Nav, Navbar } from 'react-bootstrap'
import { useSelector } from 'react-redux'
import { Link } from 'react-router-dom'
import { logout } from '../../service/authLogic'

const Header = () => {
  //로그인 진행을 위해서 authLogic.js의 auth정보 필요함.
  const userAuth = useSelector(state => state.userAuth)
  //이메일 정보 상태 관리
  //리액트는 SPA - 부분 갱신 처리하기
  //부분 변경 처리를 위해서는 a태그나 location.href사용하지 않는다. - > Link태그를 사용할것. - > SPA
  //어떤 경우는 로그인을 보여주고 어떤 경우는 이메일 주소 와 로그아웃 버튼을 보여줘야 한다.
  //다른 화면을 굳이 새로 읽어들일 필요가 없다. -> 부분 변경 처리한다. -> 비동기 처리(ajax주로 활용)
  //리액트를 선택하는 것 만으로도 ajax를 사용할 필요가 없다.
  const email1 = "";//훅이 아니므로 이 값이 변경될 때 화면을 갱신처리 할 수 없다. -문제제기
  const [email, setEmail] = useState("");
  //생각해 보기
  //만일 권한에 따라서 메뉴를 다르게 처리해야 한다면? 어떡하지
  //굳이 훅을 사용한 이유는 뭔가요? 상태(데이터)가 변하면 화면이 변한다.
  //상태가 변하면 화면도 변해야 할 때는 변수 선언을 useState 사용한다.
  //로그인 버튼을 보여줄 때 isLoggedIn은 false일 때
  //로그아웃 버튼을 보여줄때는 isLoggedIn이 true일 때 보여주자.
  const [isLoggedIn, setLoggedIn] = useState(false) //로그인 유무 체크
  //현재 로그인 한 상태인가요?
  //리액트 컴포넌트(함수)가 열릴 때 가장 먼저 호출되는 훅은 뭐지? - useEffect
  //의존성 배열에 사용한 값에 따라서 useEffect의 호출 횟수를 제어 할 수 있다.
  useEffect(()=> {
    const email = localStorage.getItem("email")
    if(email){
      setEmail(email)
      setLoggedIn(true)
    }
  },[email])//의존성 배열을 사용할 수 있다.
  //로그아웃 이벤트 처리
  const logoutE = () => {
    logout(userAuth.auth)
    //useState도 초기화할 것.
    setEmail(null)
    setLoggedIn(false)
  }
    //아래 부분이 화면 처리부분
  return (
    <>
      <Navbar bg="dark" data-bs-theme="dark">
        <Container>
          <Navbar.Brand href="#home">Navbar</Navbar.Brand>
          <Nav className="me-auto">
            <Link to="/" className="nav-link">Home</Link>
            <Link to="/schedule" className="nav-link">스케쥴</Link>
            <Link to="/reboard" className='nav-link'>댓글게시판</Link>
          </Nav>

          <div className='d-flex'>
            <ul className='navbar-nav me-auto mb-2 mb-lg-0'>
            {!isLoggedIn && (
                <li className='nav-item' id='login'>
                <Link to="/login" className='nav-link active'>로그인</Link>
              </li>
            )}
            {isLoggedIn && (
              <>
                <li className="nav-item" id="mypage">
                  <Link to="/mypage" className="nav-link">{email}</Link>
                </li>
                <li className="nav-item" id="logout">
                  <Link to="#" className='nav-link' onClick={logoutE}>로그아웃</Link>
                </li>
                </>
              )}
            </ul>
          </div>

        </Container>
      </Navbar>    
    </>
  )
}

export default Header
/*
    페이지 이동시 href를 사용하면 URL이 변한다. 
    - 기존에 요청은 끊어지고 새로운 요청이 일어난다.:기존페이지가 쥐고 있던 데이터는 잃어버린다.
*/