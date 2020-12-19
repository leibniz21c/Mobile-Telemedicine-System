< UI Front 구현 참고사항 >
1. 변수 이름과 id 설정 사항 :
    뷰의 경우 : 1) xml id 값 : "액티비티_뷰의 종류_동작목적"         e.g. 로그인 액티비티의 로그인 버튼의 id    : @+id/login_btn_login
              2) java source에서 변수의 이름 : "뷰의 종류_동작목적" e.g. 로그인 액티비티의 로그인 버튼의 변수명  : Button btn_login;

2. 자바 소스내의 변수 선언 구역
    뷰의 경우 : 언제든지 inner class 사용을 염두하여 액티비티 클래스의 멤버로 선언합니다.