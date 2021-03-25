package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.eomcs.pms.domain.Member;
import com.eomcs.util.Prompt;

public class MemberUpdateHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[회원 변경]");

    int no = Prompt.inputInt("번호? ");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement(
            "select no,name,email,photo,tel from pms_member where no=?");
        PreparedStatement stmt2 = con.prepareStatement( 
            "update pms_member set name=?, email=?, photo=?, tel=? where no = ?")) {


      Member m = new Member();

      // 1) 기존 데이터 조회
      stmt.setInt(1, no);
      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          System.out.println("해당 번호의 회원이 없습니다.");
          return ;
        }

        m.setNo(no);
        m.setName(rs.getString("name"));
        m.setEmail(rs.getString("email"));
        m.setPhoto(rs.getString("photo"));
        m.setTel(rs.getString("tel"));
      }

      // 2) 사용자에게서 변경할 데이터를 입력 받는다.
      m.setName(Prompt.inputString(String.format("이름(%s)? ", m.getName())));
      m.setEmail(Prompt.inputString(String.format("이메일(%s)? ", m.getEmail())));
      m.setPhoto(Prompt.inputString(String.format("사진(%s)? ", m.getPhoto())));
      m.setTel(Prompt.inputString(String.format("전화번호(%s)? ", m.getTel())));

      String input = Prompt.inputString("정말 변경하시겠습니까?(y/N) ");
      if (!input.equalsIgnoreCase("Y")) {
        System.out.println("회원정보 변경을 취소하였습니다.");
        return;
      }

      // 3) DBMS에게 게시글 변경을 요청한다.

      stmt2.setString(1, m.getName());
      stmt2.setString(2, m.getEmail());
      stmt2.setString(3, m.getPhoto());
      stmt2.setString(4,  m.getTel());
      stmt2.setInt(5, m.getNo());
      stmt2.executeUpdate();

      System.out.println("회원정보를 변경하였습니다.");
    }
  }
}






