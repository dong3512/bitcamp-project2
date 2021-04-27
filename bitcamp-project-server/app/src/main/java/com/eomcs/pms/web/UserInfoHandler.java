package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.MemberService;

@WebServlet("/userInfo")
public class UserInfoHandler extends HttpServlet {

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{

    MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAttribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    out.printf("사용자번호: %d\n", member.getNo());
    out.printf("이름: %s\n", member.getName());
    out.printf("이메일: %s\n", member.getEmail());
    out.printf("사진: %s\n", member.getPhoto());
  }
}






