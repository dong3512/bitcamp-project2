package com.eomcs.pms.table;

import java.io.File;
import java.sql.Date;
import java.util.List;
import com.eomcs.pms.domain.Board;
import com.eomcs.util.JsonFileHandler;
import com.eomcs.util.Request;
import com.eomcs.util.Response;

public class BoardTable implements DataTable{

  File jsonFile = new File("boards.json");
  List<Board> list;

  public BoardTable() {
    list = JsonFileHandler.loadObjects(jsonFile, Board.class);
  }

  @Override
  public void service(Request request, Response response) throws Exception{
    Board board = null;
    String[] fields = null;

    switch (request.getCommand()) {
      case "board/insert":
        fields = request.getData().get(0).split(",");
        board = new Board();

        if(list.size() > 0) {
          board.setNo(list.get(list.size() - 1).getNo() + 1);
        }else {
          board.setNo(1);
        }
        board.setTitle(fields[0]);
        board.setContent(fields[1]);
        board.setWriter(fields[2]);
        board.setRegisteredDate(new Date(System.currentTimeMillis()));

        list.add(board);

        JsonFileHandler.saveObjects(jsonFile, list);
        break;
    }


  }
}
