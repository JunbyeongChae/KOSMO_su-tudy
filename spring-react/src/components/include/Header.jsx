import React from 'react';
import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <Navbar bg="dark" data-bs-theme="dark">
        <Container>
          <Navbar.Brand href="/">🤮🤮🤮🤮</Navbar.Brand>
          <Nav className="me-auto">
            <Link to="/" className="nav-link">
              Home
            </Link>
            <Link to="/login" className="nav-link">
              로그인
            </Link>
            <Link to="/reboard" className="nav-link">
              댓글게시판
            </Link>
          </Nav>
        </Container>
      </Navbar>
    </>
  );
};

export default Header;

/****************************************************************
페이지 이동시 href사용하면 url이 변함
- 기존요청 끊어지고 새로운 요청이 발생함 => 기존페이지데이터 잃어버림
****************************************************************/
