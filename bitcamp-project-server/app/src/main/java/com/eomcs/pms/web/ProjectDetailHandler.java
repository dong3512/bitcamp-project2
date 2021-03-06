package com.eomcs.pms.web;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

@SuppressWarnings("serial")
@WebServlet("/project/detail")
public class ProjectDetailHandler extends HttpServlet {

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    ProjectService projectService = (ProjectService) request.getServletContext().getAttribute("projectService");

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();

    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>프로젝트 상세</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>프로젝트 상세보기</h1>");

    try {
      int no = Integer.parseInt(request.getParameter("no"));

      Project project = projectService.get(no);
      if (project == null) {
        out.println("<p>해당 번호의 프로젝트가 없습니다.</p>");
        out.println("</body>");
        out.println("</html>");
        return;
      }

      out.println("<form action='update' method='post'>");
      out.printf("번호: <input type='text' name='no' value='%d' readonly><br>\n", project.getNo());
      out.printf("제목: <input type='text' name='title' value='%s'><br>\n", project.getTitle());
      out.printf("내용: <textarea name='content' rows='10' cols='60'>%s</textarea><br>\n", project.getContent());
      out.printf("시작일: <input type='date' name='startDate' value='%s'><br>\n", project.getStartDate());
      out.printf("종료일: <input type='date' name='endDate' value='%s'><br>\n", project.getEndDate());
      out.printf("관리자: %s<br>\n", project.getOwner().getName());
      out.println("팀원: <br>");

      //      MemberService memberService = (MemberService) request.getServletContext().getAttribute("memberService");
      //      List<Member> members = memberService.list(null);
      //      for (Member m : members) {
      //        out.printf("  <input type='checkbox' name='member' value='%d' %s>%s<br>\n", 
      //            m.getNo(), contain(project.getMembers(), m.getNo()) ? "checked" : "", m.getName());
      //      }

      // 프로젝트 팀원 출력은 다른 서블릿에게 맡긴다.
      // 프로젝트 팀원을 출력할 때 기존 멤버의 경우 선택된 상태로 출력해야 한다.
      // 이를 위해 프로젝트 기존 멤버 정보를 해당 서블릿에게 넘긴다.
      request.setAttribute("members", project.getMembers());
      request.getRequestDispatcher("/project/member/list").include(request, response);

      out.println("<input type='submit' value='변경'> ");
      out.printf("<a href='delete?no=%d'>삭제</a>\n", project.getNo());
      out.println("</form>");

    } catch (Exception e) {
      request.setAttribute("exception",e);
      request.getRequestDispatcher("/error");
      return;
    }
    out.println("<p><a href='list'>목록</a></p>");

    out.println("</body>");
    out.println("</html>");
  }


}








