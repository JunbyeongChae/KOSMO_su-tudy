import React from 'react';
import ReactDOM from 'react-dom/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'react-quill/dist/quill.snow.css';
import App from './App'
import { BrowserRouter } from 'react-router';
import AuthLogic from './service/authLogic';
import { app } from './service/firebase';
import { Provider } from 'react-redux';
import { legacy_createStore } from 'redux';
import rootReducer from './redux/rootReducer';
import { setAuth } from './redux/userAuth/action';

//firebase.js안에 export 선언한 변수 app 사용한다.
const authLogic = new AuthLogic(app);//파라미터에 firebase app이 필요하다. - 생성자 함수의 파라미터 활용한다.
//여러개의 reducer를 조합하여 사용하기 위해 선언함. - rootReducer 이다.
let store = legacy_createStore(rootReducer);
//매번 props로 넘겨야 하는 상태 정보를 store에 초기화 한다.
store.dispatch(setAuth(authLogic.getUserAuth(), authLogic.getGoogleAuthProvider()))
//파이프연산자는 앞에가 참이면 뒤에 조건을 따지지 않는다.
//리덕스에서 상태값이 null이거나 undefinded 이면 Store is empty출력하라
console.log(store.getState() || 'Store is empty')
const root = ReactDOM.createRoot(document.querySelector('#root'));
root.render(
  <>
    <BrowserRouter>
    {/* 리덕스 설정하기 */}
      <Provider store={store}>
        <App />
      </Provider>
    </BrowserRouter>
  </>
);

