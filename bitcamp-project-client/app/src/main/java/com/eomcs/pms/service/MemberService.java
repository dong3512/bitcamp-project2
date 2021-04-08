package com.eomcs.pms.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.MemberDao;
import com.eomcs.pms.domain.Member;

public class MemberService {

  SqlSession sqlSession;

  MemberDao memberDao;

  public MemberService(SqlSession sqlSession, MemberDao memberDao) {
    this.sqlSession = sqlSession;
    this.memberDao = memberDao;
  }

  public int add(Member member) throws Exception {
    int count = memberDao.insert(member);
    sqlSession.commit();
    return count;
  }

  public List<Member> list() throws Exception {
    return memberDao.findAll();
  }

  public Member detail(int no) throws Exception {
    return memberDao.findByNo(no);
  }

  public int update(Member member) throws Exception {
    int count = memberDao.update(member);
    sqlSession.commit();
    return count;
  }

  public int delete(int no) throws Exception{
    int count = memberDao.delete(no);
    sqlSession.commit();
    return count;
  }

  public List<Member> findByName(String name) throws Exception {
    return memberDao.findByName(name);
  }

}
