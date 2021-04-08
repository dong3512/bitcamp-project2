package com.eomcs.pms.service;

import java.util.List;
import org.apache.ibatis.session.SqlSession;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.domain.Project;

public class ProjectService {

  SqlSession sqlSession;
  ProjectDao projectDao;

  public ProjectService(SqlSession sqlSession, ProjectDao projectDao) {
    this.sqlSession = sqlSession;
    this.projectDao = projectDao;
  }

  public int add(Project project) throws Exception{
    int count = projectDao.insert(project);
    sqlSession.commit();
    return count;
  }

  public List<Project> list() throws Exception {
    return projectDao.findByKeyword(null, null);
  }

  public Project detail(int no) throws Exception {
    return projectDao.findByNo(no);
  }

  public int update(Project project) throws Exception {
    int count = projectDao.update(project);
    sqlSession.commit();
    return count;
  }

  public int delete(int no) throws Exception {
    int count = projectDao.delete(no);
    sqlSession.commit();
    return count;
  }
}
