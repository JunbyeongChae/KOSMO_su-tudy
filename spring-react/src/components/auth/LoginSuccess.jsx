import React, { useEffect } from 'react'
import { useNavigate } from 'react-router'

const LoginSuccess = () => {
    const navigate = useNavigate();
    console.log('LoginSuccess')
    useEffect(()=>{
        const urlParams = new URLSearchParams(window.location.search) //기억
        const email = urlParams.get("email") //8000번에서 쿼리스트링으로 넘긴 이메일을 받아온다.
        if(email){
            localStorage.setItem("email", email)
            //메인페이지로 이동하기
            navigate("/")
        }else{
            console.error("로그인 실패: 토큰이 없어서....")
            navigate("/login")
        }
    },[navigate])//최초 한 번만 호출된다.
    //useEffect에서 의존성 배열 자체를 생략하면 매번 호출이 된다. - 사용하지 않습니다
    // useCallback, useMemo에서도 사용됩니다.
    //의존성 배열에 있는 값이 바뀔때만 새로 함수나 변수를 생성해준다. - 재조정
    return (
        <div>로그인 중.......</div>
    )
}

export default LoginSuccess