import { createUserWithEmailAndPassword, EmailAuthProvider, getAuth, GoogleAuthProvider, sendEmailVerification, signInWithPopup } from "firebase/auth"
//클래스 선언 - 바닐라 스크립트에서도 클래스 선언이 가능함.
class AuthLogic {
    //생성자 함수 선언하기 - 초기화 - 메소드오버로딩이나 메소드 오버라이딩 지원안함.
    //모듈화 사용 - ECMAScript2015
    //this가 붙은 auth와 googleProvider는 전역변수 이다. 외부에서 사용함.
    //매번 props로 넘기는 것이 비효율적이라서 하나만 리덕스로 관리해 보자.
    constructor(){
        this.auth = getAuth()
        this.googleProvider = new GoogleAuthProvider()// 서비스 제공 업체에 대한 정보 담은 클래스
    }
    //getter함수 - 외부(authoLogic.js가 아닌 다른 js파일)에서 사용하고 싶을 때 
    //LoginPage.jsx에서 구글 로그인 버튼을 누르면 그 때 auth가 필요하다.
    //auth를 props로 처리하는게 불편하니 리덕스에 담자
    getUserAuth = () => {
        return this.auth
    }
    //구글인증제공자 정보 반환받기 -getter함수
    getGoogleAuthProvider = () => {
        return this.googleProvider
    }
}//end of AuthLogic

export default AuthLogic
//로그인 화면에서 호출할 함수 구현
//구글 로그인 버튼 클릭시 호출되는 함수로 파라미터인 auth와 googleProvider는 
//props로 받지 않고 리덕스 활용해서 직접 받아낸다.
//자바스크립트는 기본적으로 동기적 처리를 한다. 외부에 클라우드 서비스를 활용하면
//필연적으로 지연이 발생할 것이고 이것을 효율적으로 처리하는 방법은 기다림동안
//다른 업무를 처리할 수 있도록 코딩을 전개하는 것이다.
//표준에서 제공되는 protype Promise사용하여 성공시 resolve콜백호출이
//실패시에는 reject콜백호출이 일어난다.
export const loginGoogle = (auth, googleProvider) => {
    return new Promise((resolve, reject)=>{
        //파라미터로 받은 값이 구글에서 제공하는 signInWithPopup파라미터로 넘어간다.
        signInWithPopup(auth, googleProvider)
        .then(result => {
            const user = result.user //user가 라벨값임
            console.log(user)
            localStorage.setItem('uid', user.uid)
            localStorage.setItem('email', user.email)
            resolve(user)
        })
        .catch(e => reject(e))
    })
}//end of 구글 로그인 - 로그인 화면에 구글 로그인 추가
//사실 이미 구글 firebase에서는 비동기 지원하고 있음. - Promise안해도됨
export const onAuthChange = (auth) => {
    //바닐라 스크립트에서 제공하는 프로토타입 비동기 처리 객체
    //파라미터 - 콜백함수 - 일급함수 -> 자동으로 담아줌 - 상태체크하고 그 상태값이 확인되면 그 정보 담아준다.
    return new Promise(resovle => {
        //firebase 웹로그인에서 제공하는 API이다. - 현재 네가 열은 크롬에 로그인이되어 있어? 체크
        //uid있음. ->회원가입 유도 함.
        //그럼 네 이메일로 오라클 서버에  가서 member250120테이블에서 이메일이 존재하는지를 체크할께
        auth.onAuthStateChanged(user => {
            resovle(user)//파라미터에 담긴 user는 구글이 쥐고 있는 정보임 - 구글로 로그인을 했어 - user.emailVerified
        })
    })
}

export const logout = (auth) => {
    return new Promise((resolve, reject) => {
        auth.signOut().catch((e) => reject(alert(e+": 로그아웃 오류입니다.")))
        //insert here - localStorage에 있는 모든 값 삭제하기
        localStorage.removeItem("email")
        localStorage.removeItem("uid")
        resolve()
    })
}//end of logout
//회원가입 화면에서 가입하기 버튼을 누르면 구글계정에 대한 uid값을 받아오기
export const linkEmail = (auth, user) => {
    return new Promise((resovle, reject) => {
        const credential = EmailAuthProvider.credential(user.email, user.password)
        console.log(auth.currentUser.uid) //회원가입시 MEM_UID컬럼에 들어갈 값
        resovle(auth.currentUser.uid)
    })
}//end of linkEmail

export const signupEmail = (auth, user) => {
    console.log(user)
    return new Promise((resolve, reject) => {
        createUserWithEmailAndPassword(auth, user.email, user.password)
        .then((userCredential) => {
            sendEmail(userCredential.user).then(() => {
                resolve(userCredential.user.uid)
            })
        })
        .catch((e)=> reject(e))
    })
}//end of signupEmail

export const sendEmail = (user) => {
    return new Promise((resolve, reject) => {
        sendEmailVerification(user)
        .then(() => {
            resolve("해당 이메일에서 인증메시지를 확인 후 다시 로그인 해주세요")
        })
        .catch(e => reject(e+": 인증 메일 오류 입니다."))
    })
}//end of sendEmail