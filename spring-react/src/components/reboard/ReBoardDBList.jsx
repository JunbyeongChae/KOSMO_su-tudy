import React from 'react'
import Header from '../include/Header'
import { useEffect, useState } from 'react'
import Footer from '../include/Footer'
import { useNavigate } from 'react-router'
import { boardListDB } from '../../service/dbLogic'
import ReBoardDBItem from './ReBoardDBItem'

const ReBoardDBList = () => {
    const navigate = useNavigate();
    const [boards, setBoards] = useState([])
    //현재 내가 바라보는 페이지 정보
    const [currentPage, setCurrentPage] = useState(1)
    //한 페이지당 항목 수
    const itemsPerPage = 5
    //현재 페이지 출력될  item 계산 - 이 값만큼만 반복문 돌리기
    const currentItems = boards.slice((currentPage-1)*itemsPerPage, currentPage*itemsPerPage)
    //페이징 처리 결과에 따라서 화면을 매번 재렌더링 하기
    useEffect(()=> {
        //URL에서 현재 페이지 번호 가져오기
        const queryParams = new URLSearchParams(window.location.seach)
        const page = queryParams.get('page')
        //자바스크립트에서는 0이면 false 아니면 다 true
        //쿼리스트링으로 넘어오는 값은 모두 다 string -> int
        if(page) setCurrentPage(parseInt(page))
    },[window.location.search])
    //목록 페이지를 열자 마자 DB경유하는 코드는 어디서 어떻게 작성할까?
    useEffect (() => {
        const asyncDB = async() => {
            const board = { gubun: null, keyword: null}
            const res = await boardListDB(board)
            console.log(res.data)
            setBoards(res.data)
        }
        asyncDB()
    },[]) //의존성 배열에 useState넣을 때는 무한루프에 빠질 수 있다
    //게시글에 대한 조건 검색 구현
    const boardSearch = async() => {
        const gubun = document.querySelector("#gubun").value
        const keyword = document.querySelector("#keyword").value
        console.log(`${gubun}, ${keyword}`)
        const board = {gubun, keyword}
        const res = await boardListDB(board)
        console.log(res.data)
        setBoards(res.data)
        //검색시 첫 페이지로 이동하기
        setCurrentPage(1)
    }//end of boardSearch
    const boardList = async() => {
        console.log("전체조회")
        const board = {gubun: null, keyword: null}
        const res = await boardListDB(board)
        setBoards(res.data)
        setCurrentPage(1)
    }//end of boardList 
    return (

        <>
            <Header />
            <div className='container'>
                    <div className='page-header'>
                    <h2>댓글게시판<small>POJO기반</small></h2>
                    <hr />
                    </div>
                    <div className="row">
                    <div className="col-sm-3">
                    <select className="form-select" id="gubun">
                        <option value="">분류선택</option>
                        <option value="b_title">제목</option>
                        <option value="b_writer">작성자</option>
                        <option value="b_content">내용</option>
                    </select>
                    </div>
                    <div className="col-sm-6">
                    <input type="text" className="form-control" placeholder="검색어를 입력하세요" id="keyword" />
                    </div>
                    <div className="col-sm-3">
                        <button type="button" className="btn btn-danger" onClick={boardSearch}>검색</button>
                    </div>
                    </div>
        
                <table className="table table-hover">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>제목</th>
                            <th>작성자</th>
                        </tr>
                    </thead>
                    {/* 데이터셋 연동하기 */}
                    {/* props로 넘어온 상태값이 빈 깡통이면 실행하지 않기 */}
                    <tbody>
                        {Array.isArray(currentItems) && currentItems.map((board, index) => (
                            <ReBoardDBItem key={index} board={board} page={currentPage} />
                        ))}
                    </tbody>
                    {/* 데이터셋 연동하기 */}
                </table>

                <div className='d-flex justify-content-center'>
                    1 2 3
                </div>

                <hr />
                <div className='list-footer'>
                    <button className="btn btn-warning" onClick={boardList}>전체조회</button>
                    &nbsp;
                    <button  className="btn btn-success" onClick={()=> navigate('/reboard/write')}>글쓰기</button>
                </div>
                </div>
            <Footer />
        </>

    )
}

export default ReBoardDBList