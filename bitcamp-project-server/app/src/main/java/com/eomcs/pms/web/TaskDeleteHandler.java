package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Task;
import com.eomcs.pms.service.TaskService;

@SuppressWarnings("serial")
@WebServlet("/task/delete")
public class TaskDeleteHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    TaskService taskService = (TaskService) request.getServletContext().getAttribute("taskService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>작업 삭제</title>");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Task task = taskService.get(no);
      if (task == null) {
        throw new Exception("해당 번호의 작업이 없습니다.");
      }

      taskService.delete(no);

      out.println("<meta http-equiv='Refresh' content='1;url=list'>");
      out.println("</head>");
      out.println("<body>");
      out.println("<h1>작업 삭제</h1>");
      out.println("<p>작업을 삭제하였습니다.</p>");

    } catch (Exception e) {
      request.setAttribute("exception",e);
      request.getRequestDispatcher("/error");
      return;
    }

    out.println("</body>");
    out.println("</html>");
  }
}
