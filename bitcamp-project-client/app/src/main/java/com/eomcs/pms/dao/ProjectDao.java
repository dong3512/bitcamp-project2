package com.eomcs.pms.dao;

import java.util.List;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;

public interface ProjectDao {


  // 이제 메서드들은 인스턴스 필드에 들어있는 Connection 객체를 사용해야 하기 때문에
  // 스태틱 메서드가 아닌 인스턴스 메서드로 선언해야 한다.
  int insert(Project project) throws Exception;

  List<Project> findAll() throws Exception;

  Project findByNo(int no) throws Exception ;

  int update(Project project) throws Exception;

  int delete(int no) throws Exception ;

  int insertMember(int projectNo, int memberNo) throws Exception ;

  List<Member> findAllMembers(int projectNo) throws Exception ;

  int deleteMembers(int projectNo) throws Exception ;
}












