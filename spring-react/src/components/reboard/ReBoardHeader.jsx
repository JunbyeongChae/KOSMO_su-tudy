import React, { useEffect, useState } from 'react'
import { BButton } from '../../styles/FormStyles'
import { useNavigate } from 'react-router'
import { boardDeleteDB } from '../../service/dbLogic'


const ReBoardHeader = ({board, b_no, page}) => {
    const navigate = useNavigate()
    const[b_hit, setBhit] = useState(0)
    //boardDetail한 뒤 조회수가 1 증가된 결과가 즉시 화면에 반영되도록 수정할 때
    //리액트에서는 state가 변하면 새로 렌더링 한다.
    useEffect(() => {
        setBhit(board.B_HIT)
    },[board])
    const boardDelete = async() => {
        await boardDeleteDB(b_no)
        //삭제가 되면 목록 페이지로 간다.
        navigate('/reboard')
    }
    const boardList = () => {
        navigate(`/reboard?page=${page}`)
    }
    const boardUpdate = () => {
        navigate(`/reboard/update/${b_no}`)
    }
    return (
        <>
            <div style={{display: 'flex', flexDirection: 'column', width: '100%'}}>
                <div style={{display: 'flex', justifyContent:"space-between"}}>
                <div style={{overflow: "auto"}}>
                    <span style={{marginBottom:'15px', fontSize: "30px", display:"block"}}>
                    {board.b_title}
                    </span>
                </div>
                {
                    <div style={{display: 'flex', justifyContent: 'flex-end'}}>
                    <BButton style={{margin:'0px 10px 0px 10px'}} onClick={()=>{navigate(`/reboard/update/${b_no}`)}}>
                        수정
                    </BButton>
                    <BButton style={{margin:'0px 10px 0px 10px'}} onClick={boardDelete}>
                        삭제
                    </BButton>
                    <BButton style={{margin:'0px 10px 0px 10px'}} onClick={boardList}>
                        목록
                    </BButton>
                    </div>
                }
                </div>
                <div style={{display: 'flex', justifyContent: 'space-between', fontSize: '14px'}}>
                <div style={{display: 'flex', flexDirection: 'column'}}>
                    <span>작성자 : {board.MEM_NAME}</span>
                    <span>작성일 : {board.B_DATE}</span>
                </div>
                <div style={{display: 'flex', flexDirection: 'column', marginRight:'10px'}}>
                    <div style={{display: 'flex'}}>
                    <span style={{marginRight:'5px'}}>조회수 :</span>
                    <div style={{display: 'flex', justifyContent: 'flex-end', width:'30px'}}>{board.B_HIT}</div>
                    </div>
                </div>
                </div>
            </div>      
            <hr style={{height: "2px"}}/>
        </>
    )
}

export default ReBoardHeader