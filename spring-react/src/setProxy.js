const { createProxyMiddleware } = require("http-proxy-middleware")


module.exports = function(app){
  app.use(
    '/api',//api가붙은 요청에 대해서는 CORS이슈 피한다.
    createProxyMiddleware({
      target: 'http://localhost:8000',//spring boot backend URL
      changeOrigin: true //원격요청의 Origin헤더를 대상 서버의 URL로 변경한다.
    })
  )
}
/*
  Mode.js환경에서 사용할 수 있는 http-proxy-middleware 라이브러리를를 사용하여 프록시 미들웨어를 설정하는 스크립트이다.
  이 환경을 통해 프론트엔드 어플리케이션(리액트)에서 특정 api요청을 프록시 서버를 통해 백엔드로 전달 가능.
  파일명은 src/setProxy.js로 설정하면 알아서 읽어낸다.

  ex) 프론트쪽에서는 http://localhost:8000/api/xxxx로 요청하면 백엔드측 SpringBoot서버로 연결이 된다.
*/