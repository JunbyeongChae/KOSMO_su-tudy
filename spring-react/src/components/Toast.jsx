import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import styled from 'styled-components';
import { setToastFalse } from '../redux/toastStatus/action';
import "./toast.css"


const ToastDiv = styled.div`
    position: fixed;
    top: 50%;
    left: 50%;
    padding: 11px;
    min-width: 350px;
    transform: translate(-50%, -50%);
    /* transition: transform 1850ms ease-in; */
    /* transition:opacity 200ms linear 100ms, left 200ms 200ms; */
    justify-content: center;
    text-align: center;
    //font-weight: bold;
    font-size: 18px;
    z-index: 99;
    background: rgba(0, 0, 0, 0.7);
    color: #ffffff;
    border-radius: 4px;
    border: 1px solid #000000;
    `

    const Toast = () => {

    const status = useSelector(store => store.status);
    const msg = useSelector(store => store.msg);
    const dispatch = useDispatch();

    
    useEffect(() => {
        if (status) {
        let x = document.getElementById("snackbar");
        console.log(x);
        x.className = "show";
        setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000); 
        setTimeout(() => {
            dispatch(setToastFalse()); 
        }, 2500)
        }

    }, [status, dispatch]);
    
    //CSS효과는 App.css에 추가함
    return (
        <ToastDiv id='snackbar'>{JSON.stringify(msg)}</ToastDiv>
    );
};

export default Toast;