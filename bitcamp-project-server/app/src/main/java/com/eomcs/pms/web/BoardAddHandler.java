package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.service.BoardService;

@SuppressWarnings("serial")
@WebServlet("/board/add")
public class BoardAddHandler extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    Board b = new Board();

    b.setTitle(request.getParameter("title"));
    b.setContent(request.getParameter("content"));

    // 작성자는 로그인 사용자이다.
    HttpServletRequest httpRequest = request;
    Member loginUser = (Member) httpRequest.getSession().getAttribute("loginUser");
    b.setWriter(loginUser);

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>게시글 등록</title>");

    try {
      boardService.add(b);

      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>게시글 등록</h1>");
      out.println("<p>게시글을 등록했습니다.</p>");

    } catch (Exception e) {
      request.setAttribute("exception",e);
      request.getRequestDispatcher("/error");
      return;
    }

    out.println("</body>");
    out.println("</html>");
  }
}






