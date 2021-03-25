package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Project;
import com.eomcs.util.Prompt;

public class ProjectUpdateHandler implements Command {

  MemberValidator memberValidator;

  public ProjectUpdateHandler( MemberValidator memberValidator) {
    this.memberValidator = memberValidator;
  }

  @Override
  public void service() throws Exception {
    System.out.println("[프로젝트 변경]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,title,content,startdate,enddate from pms_project where no=?");
        PreparedStatement stmt2 = con.prepareStatement( 
            "update pms_project set title=?, content=?, startdate=?, enddate=?, owner=?, members=? where no = ?")) {

      Project p = new Project();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 프로젝트가 없습니다.");
          return ;
        }

        p.setNo(no);
        p.setTitle(rs.getString("title"));
        p.setContent(rs.getString("content"));
        p.setStartDate(rs.getDate("startdate"));
        p.setEndDate(rs.getDate("enddate"));
        p.setOwner(rs.getString("owner"));
        p.setMembers(rs.getString("members"));
      }

      p.setTitle(Prompt.inputString(String.format("프로젝트명(%s)? ", p.getTitle())));
      p.setContent(Prompt.inputString(String.format("내용(%s)? ", p.getContent())));
      p.setStartDate(Prompt.inputDate(String.format("시작일(%s)? ", p.getStartDate())));
      p.setEndDate(Prompt.inputDate(String.format("종료일(%s)? ", p.getEndDate())));

      String owner = memberValidator.inputMember(String.format("만든이(%s)?(취소: 빈 문자열) ", p.getOwner()));
      if (owner == null) {
        System.out.println("프로젝트 변경을 취소합니다.");
        return;
      }

      p.setMembers(memberValidator.inputMembers(
          String.format("팀원(%s)?(완료: 빈 문자열) ", p.getMembers())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("프로젝트 변경을 취소하였습니다.");
        return;
      }

      stmt2.setString(1, p.getTitle());
      stmt2.setString(2, p.getContent());
      stmt2.setDate(3, p.getStartDate());
      stmt2.setDate(4, p.getEndDate());
      stmt2.setString(5, p.getOwner());
      stmt2.setString(6, p.getMembers());
      stmt2.setInt(7, p.getNo());
      stmt2.executeUpdate();

      System.out.println("프로젝트을 변경하였습니다.");
    }
  }
}








