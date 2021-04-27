package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Board;
import com.eomcs.pms.service.BoardService;

@SuppressWarnings("serial")
@WebServlet("/board/search")
public class BoardSearchHandler extends HttpServlet {

  @Override
  public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/plain;charset=UTF-8");
    PrintWriter out = response.getWriter();

    BoardService boardService = (BoardService) request.getServletContext().getAttribute("boardService");

    try {
      String keyword = request.getParameter("keyword");

      if (keyword.length() == 0) {
        out.println("검색어를 입력하세요.");
        return;
      }

      List<Board> list = boardService.search(keyword);

      if (list.size() == 0) {
        out.println("검색어에 해당하는 게시글이 없습니다.");
        return;
      }

      for (Board b : list) {
        out.printf("%d, %s, %s, %s, %d\n", 
            b.getNo(), 
            b.getTitle(), 
            b.getWriter().getName(),
            b.getRegisteredDate(),
            b.getViewCount());
      }
    }catch (Exception e) {
      // 상세 오류 내용을 StringWriter로 출력한다.
      StringWriter strWriter = new StringWriter();
      PrintWriter printWriter = new PrintWriter(strWriter);
      e.printStackTrace(printWriter);

      // StringWriter 에 들어있는 출력 내용을 꺼내 클라이언트로 보낸다.
      out.println(strWriter.toString());
    }
  }
}





