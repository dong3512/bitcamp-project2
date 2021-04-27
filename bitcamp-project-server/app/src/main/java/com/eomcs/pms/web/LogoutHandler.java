package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Member;

@WebServlet("/logout")
public class LogoutHandler extends HttpServlet {

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException {

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    Member member = (Member) request.getSession().getAttribute("loginUser");
    if (member == null) {
      out.println("로그인 하지 않았습니다!");
      return;
    }

    request.getSession().invalidate();

    out.printf("%s 님 안녕히 가세요!\n", member.getName());
  }
}






