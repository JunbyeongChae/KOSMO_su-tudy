import React, { useCallback, useEffect, useState } from 'react';
import Header from '../include/Header';
import { BButton, CommentArea, ContainerDiv, FormDiv, HeaderDiv } from '../../styles/FormStyles';
import Footer from '../include/Footer';
import ReBoardHeader from './ReBoardHeader';
import { useParams } from 'react-router';
import { boardDetailDB, reCommentDeleteDB, reCommentInsertDB, reCommentUpdateDB } from '../../service/dbLogic';

//게시글 상세보기 - 댓글보기와 댓글 쓰기 화면 포함
const ReBoardDBDetail = () => {
  //상세보기 이므로 한 건에 대한 조회 결과 이다.
  const { b_no } = useParams();
  console.log('b_no : ' + b_no); //1번 혹은 9번
  //?쿼리스트링 가져오는 코드 : 페이징 처리(현재 페이지 정보 기억)
  //location앞에 window가 상위 객체임. - 카카오-global참조
  const queryParams = new URLSearchParams(window.location.search);
  //만일 page값이 null, undefined 일 때 1반환
  const page = queryParams.get('page') || 1;
  //댓글 내용을 담을 훅
  const [comment, setComment] = useState();
  //한 건조회한 결과를 담을 훅 - 훅이 변하면 화면을 다시 렌더링 한다. - SPA - a태그
  //이른, 게으른 한 번 생각해 보기 -> location.href 사용하면 SPA가 아니다.
  const [board, setBoard] = useState({
    B_NO: 0, //DB가져온 값을 담는 변수 -대문자 인 이유는 myBatis디폴트 toUppercase
    B_TITLE: '',
    EMAIL: '',
    B_CONTENT: '',
    B_DATE: '',
    B_HIT: 0
  });
  //이 글에 대한 댓글을 담을 훅 선언
  const [comments, setComments] = useState([]);
  const handleComment = useCallback((e) => {
    setComment(e.target.value); //훅 상태값이 변한다. -> 변할 때 마다 ReBoardWrite()호출된다.-> 그 때마다 함수도 새로 생성됨.
  }, []);
  const boardDetail = async () => {
    const res = await boardDetailDB(b_no);
    console.log(res.data); //[{},comments:[{},{},{},,,]]
    setBoard(res.data[0]);
    //댓글이 존재할 경우만 훅에 초기화
    if (res.data[1]) {
      setComments(res.data[1].comments);
    }
  };
  //이 화면이 렌더링 될 때 훅 초기화 하기
  useEffect(() => {
    boardDetail();
  }, [b_no]);
  const commentInsert = async () => {
    if (!comment) return alert('댓글을 작성하세요.');
    const cmt = {
      b_no: b_no,
      email: localStorage.getItem('email'),
      bc_comment: comment
    };
    await reCommentInsertDB(cmt);
    setComment('');
    console.log('댓글이 등록되었습니다.');
    boardDetail();
  };

  const commentUpdate = async (bc_no) => {
    console.log('commentUpdate  bc_no : ' + bc_no);
    //insert here
    const cmt = {
      b_no: b_no, //부모글 번호
      bc_email: localStorage.getItem('email'), //작성자
      bc_comment: comment, //수정된 내용
      bc_no: bc_no //한 건에 대한 수정 처리 -pk
    };
    await reCommentUpdateDB(cmt);
    console.log('답변이 수정되었습니다.');
  }; //end of commentUpdate

  const commentDelete = async (bc_no) => {
    console.log('commentDelete  bc_no : ' + bc_no);
    //insert here
    await reCommentDeleteDB(bc_no);
    console.log('답변이 삭제되었습니다.');
    window.location.reload();
  }; //end of commentDelete

  return (
    <>
      <Header />
      <ContainerDiv>
        <HeaderDiv>
          <h3>게시글 상세보기</h3>
        </HeaderDiv>
        <FormDiv>
          <ReBoardHeader board={board} b_no={b_no} page={page} />
          <section>
            {/* QuillEditor사용하면 <p></p> */}
            <div dangerouslySetInnerHTML={{ __html: board.B_CONTENT }}></div>
          </section>
          <hr style={{ height: '2px' }} />
          <div>
            <h3>댓글작성</h3>
            <div style={{ display: 'flex' }}>
              <textarea
                style={{ width: '100%', resize: 'none', border: '1px solid lightgray', borderRadius: '10px', padding: '5px' }}
                onChange={(e) => {
                  setComment(e.target.value);
                }}
              />
              <BButton
                style={{ height: '60px', marginLeft: '10px' }}
                onClick={() => {
                  commentInsert();
                }}>
                작성
              </BButton>
            </div>
          </div>
          <hr style={{ height: '2px' }} />
          {/*  댓글 목록 보기  */}
          <div>
            {/* 댓글이 존재하지 않으면 undefined */}
            {comments &&
              comments.map((item, index) => (
                <div key={index}>
                  <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '10px' }}>
                    <div style={{ display: 'flex', flexDirection: 'column', fontSize: '16px' }}>
                      <span>작성일 : {item.BC_DATE}</span>
                      <span>작성자 : {item.MEM_NICKNAME}</span>
                    </div>
                    <div>
                      <BButton>답변</BButton>
                      <BButton onClick={() => commentUpdate(`${item.BC_NO}`)}>수정</BButton>
                      <BButton onClick={() => commentDelete(`${item.BC_NO}`)}>삭제</BButton>
                    </div>
                  </div>
                  <div>
                    <CommentArea
                      defaultValue={item.BC_COMMENT}
                      onChange={(event) => {
                        setComment(event.target.value);
                      }}
                    />
                  </div>
                </div>
              ))}
          </div>
          {/*  댓글 목록 보기  */}
        </FormDiv>
      </ContainerDiv>
      <Footer />
    </>
  );
};

export default ReBoardDBDetail;
