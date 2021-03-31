package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Task;

public interface TaskDao {

  // 이제 메서드들은 인스턴스 필드에 들어있는 Connection 객체를 사용해야 하기 때문에
  // 스태틱 메서드가 아닌 인스턴스 메서드로 선언해야 한다.
  int insert(Task task) throws Exception;

  List<Task> findAll() throws Exception;

  List<Task> findByProjectNo(int projectNo) throws Exception ;

  Task findByNo(int no) throws Exception ;

  int update(Task task) throws Exception ;

  int delete(int no) throws Exception ;
}













