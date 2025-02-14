import React from 'react'
import Header from '../include/Header'
import { useEffect, useState } from 'react'
import Footer from '../include/Footer'
import { useNavigate } from 'react-router'
import { boardListDB } from '../../service/dbLogic'
import ReBoardDBItem from './ReBoardDBItem'
import { Pagination } from 'react-bootstrap'
//백엔드와 연동할(select) 때 - useEffect - > 조회 결과가 있다. -> json으로 받음.
//- useState 활용하여 동기화 처리함. - 화면이 실시간으로 변경됨. - 부분 변경이 가능함.
//페이지 이동할 때 useNavigate() - 화면이 번쩍하지 않고 변경된 부분이 반영된 결과화면을 볼 수 있다.
//window.location.search  - 쿼리스트링이나 hashchange이벤트 처리에 필요한 정보 얻을 수 있다.
//폼 전송 - submit이슈(기존에 알고 있던 정보를 잃어버림) - 사용자가 입력한 상태값- onChange이벤트
//이 값들도 동기화 되기를 원하거나 변경시 화면에 적용되기를 원하면 useState변수에 담을 것.
const ReBoardDBList = () => {
    const navigate = useNavigate();
    //조회 건수가 여러 건이므로 배열로 정의하였다.
    //3000번과 8000사이의 일어남. - 비동기 처리해야 함. -async, await
    const [boards, setBoards] = useState([])
    //현재 내가 바라보는 페이지 정보 - 상세 페이지에서 다시 내가 있던 자리로 돌어갈때 필요한 값이다.
    const [currentPage, setCurrentPage] = useState(1)
    //한 페이지당 항목 수 - 전체 페이지 수를 계산할 수 있다.  45
    const itemsPerPage = 5
    //현재 페이지 출력될  item 계산 - 이 값만큼만 반복문 돌리기
    const currentItems = boards.slice((currentPage-1)*itemsPerPage, currentPage*itemsPerPage)
    //페이징 처리 결과에 따라서 화면을 매번 재렌더링 하기
    useEffect(()=> {
        //URL에서 현재 페이지 번호 가져오기
        //localhost:3000/reboard?page=2
        const queryParams = new URLSearchParams(window.location.seach) //쿼리스트링으로 가져오기
        const page = queryParams.get('page') //localhost:3000/reboard?page=2
        //자바스크립트에서는 0이면 false 아니면 다 true
        //쿼리스트링으로 넘어오는 값은 모두 다 string -> int
        if(page) setCurrentPage(parseInt(page)) //현재 내가 바라보는 페이지 정보 담기
        //의존성 배열에 들어있는 멤버 변수 값이 변하면 그 때마다 실행이 반복된다.
    },[navigate]) //localhost:3000/reboard/update/12 -> 수정이 성공하면 localhost:3000/reboard?page=1
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
    const handlePageChange = (pageNumber) => {
        //현재 내가 바라보고 있는 페이지 번호를 훅에 기억하기
        //만일  상세보기를 한 후에 다시 목록을 누르면 이전에 내가 있었던 페이지 번호로 돌아가야 한다.
        setCurrentPage(pageNumber)
    }
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

                    <Pagination>
                            <Pagination.First 
                                onClick={()=> handlePageChange(1)}
                                disabled={currentPage === 1}
                            />
                            {/* 현재 내가 바라보는 페이지에서 하나 이전 페이지로 이동하기 */}
                            <Pagination.Prev 
                                onClick={()=> handlePageChange(currentPage - 1)}
                                disabled={currentPage === 1}
                            />
                {Array.from({length: Math.ceil(boards.length / itemsPerPage)}, (_, i) => i+1).map((pageNumber) => (
                            /*  숫자 버튼을 클릭하면 이동하고자 하는 페이지 번호를 파라미터로 넘김. */
                            <Pagination.Item 
                                active={currentPage === pageNumber} 
                                key={pageNumber} 
                                onClick={()=> handlePageChange(pageNumber)}
                            >
                                {pageNumber}
                            </Pagination.Item>
                ))}
                            <Pagination.Next 
                                onClick={()=> handlePageChange(currentPage + 1)}
                                disabled={currentPage === Math.ceil(boards.length / itemsPerPage)}
                            />
                            <Pagination.Last 
                                onClick={() => handlePageChange(Math.ceil(boards.length / itemsPerPage))}
                                disabled={currentPage === Math.ceil(boards.length / itemsPerPage)}
                            />
                        </Pagination>

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