package com.eomcs.pms.handler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BoardListHandler implements Command {

  @Override
  public void service() throws Exception {
    System.out.println("[게시글 목록]");

    try (Connection con = DriverManager.getConnection( //
        "jdbc:mysql://localhost:3306/studydb?user=study&password=1111");
        PreparedStatement stmt = con.prepareStatement( //
            "select no,title,writer,cdt,vw_cnt from pms_board order by no desc");
        ResultSet rs = stmt.executeQuery()) {

      while (rs.next()) {
        System.out.printf("%d, %s, %s, %s, %d\n", 
            rs.getInt("no"), 
            rs.getString("title"), 
            rs.getString("writer"),
            rs.getDate("cdt"),
            rs.getInt("vw_cnt"));
      }
    }
  }
}






