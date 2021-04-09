package com.eomcs.pms.service;

import java.util.List;
import com.eomcs.pms.domain.Board;

public interface BoardService {

  int add(Board board) throws Exception;

  List<Board> list() throws Exception ;

  Board get(int no) throws Exception ;

  int update(Board board) throws Exception;

  // 게시글 삭제 업무
  int delete(int no) throws Exception ;

  // 게시글 검색 업무
  List<Board> search(String keyword) throws Exception;
}







