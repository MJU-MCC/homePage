package com.example.mccHomePage.Member.message;

public abstract class MemberMessage {
    /*
    * 추상 클래스로 선언하는 이유는 굳이 객체를 생성할 필요가 없어서이다.
    */

    /*
    * static으로 한 이유는 객체를 생성하지 않으니 안에 필드를 사용하기 위해서 static을 쓴다.
    * 그렇다면 이 클래스를 사용하는 이유는 응답을 한곳에서 관리해야 하드코딩을 방지 할 수 있다.
    */

    public final static String SIGN_SUCCESS = "회원가입 성공하였습니다.";
    public final static String SIGN_DUPLICATE_MEMBERNUMBER = "현재 존재하는 학번입니다.";
    public final static String SIGN_RECHECK_MEMBERNUMBER = "입력한 학번을 다시 확인해주세요";
    public final static String SIGN_FAIL = "회원가입 실패 하였습니다.";

    public final static String NOT_AUTHENTICATE = "인증이 필요합니다.";
    public final static String NOT_AUTHORIZATION = "관리자가 아닙니다.";

}
