package com.eomcs.pms.service.impl;

import java.util.HashMap;
import java.util.List;
import com.eomcs.mybatis.TransactionCallback;
import com.eomcs.mybatis.TransactionManager;
import com.eomcs.mybatis.TransactionTemplate;
import com.eomcs.pms.dao.ProjectDao;
import com.eomcs.pms.dao.TaskDao;
import com.eomcs.pms.domain.Member;
import com.eomcs.pms.domain.Project;
import com.eomcs.pms.service.ProjectService;

// 서비스 객체
// - 비즈니스 로직을 담고 있다.
// - 업무에 따라 트랜잭션을 제어하는 일을 한다.
// - 서비스 객체의 메서드는 가능한 업무 관련 용어를 사용하여 메서드를 정의한다.
//
public class DefaultProjectService implements ProjectService {

  TransactionTemplate transactionTemplate;

  ProjectDao projectDao;
  TaskDao taskDao;

  public DefaultProjectService(TransactionManager txManager, ProjectDao projectDao, TaskDao taskDao) {
    this.transactionTemplate = transactionTemplate;
    this.projectDao = projectDao;
    this.taskDao = taskDao;
  }

  // 등록 업무 
  @Override
  public int add(Project project) throws Exception {
    return (Integer) transactionTemplate.execute(new TransactionCallback() {
      @Override
      public Object doInTransaction() throws Exception {
        // 트랜잭션으로 묶어서 실행할 작업을 기술한다.
        // 1) 프로젝트 정보를 입력한다.
        int count = projectDao.insert(project);

        // 2) 멤버를 입력한다.
        HashMap<String,Object> params = new HashMap<>();
        params.put("projectNo", project.getNo());
        params.put("members", project.getMembers());

        projectDao.insertMembers(params);
        return count;
      }

    });

    txManager.beginTransaction();
    try {


      // 조회 업무
      @Override
      public List<Project> list() throws Exception {
        return projectDao.findByKeyword(null);
      }

      // 상세 조회 업무
      @Override
      public Project get(int no) throws Exception {
        return projectDao.findByNo(no);
      }

      // 변경 업무
      @Override
      public int update(Project project) throws Exception {
        transactionTemplate.execute(new TransactionCallbak() {
          public Object doInTransaction() throws Exception {
            int count = projectDao.update(project);
            projectDao.deleteMembers(project.getNo());

            HashMap<String,Object> params = new HashMap<>();
            params.put("projectNo", project.getNo());
            params.put("members", project.getMembers());

            projectDao.insertMembers(params);
            return count;
          }
        });
      }

      // 삭제 업무
      @Override
      public int delete(int no) throws Exception {
        return (int) transactionTemplate.execute(new TransactionCallback() {
          @Override
          public Object doInTransaction() throws Exception {
            // 1) 프로젝트의 모든 작업 삭제
            taskDao.deleteByProjectNo(no);

            // 2) 프로젝트 멤버 삭제
            projectDao.deleteMembers(no);

            // 3) 프로젝트 삭제
            return projectDao.delete(no);
          }

        });
      }
    }
  }

  // 찾기
  @Override
  public List<Project> search(String title, String owner, String member) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("title", title);
    params.put("owner", owner);
    params.put("member", member);

    return projectDao.findByKeywords(params);
  }

  @Override
  public List<Project> search(String item, String keyword) throws Exception {
    HashMap<String,Object> params = new HashMap<>();
    params.put("item", item);
    params.put("keyword", keyword);

    return projectDao.findByKeyword(params);
  }

  @Override
  public int deleteMembers(int projectNo) throws Exception {
    return projectDao.deleteMembers(projectNo);
  }

  @Override
  public int updateMembers(int projectNo, List<Member> members) throws Exception {
    txManager.beginTransaction();
    try {
      projectDao.deleteMembers(projectNo);

      HashMap<String,Object> params = new HashMap<>();
      params.put("projectNo", projectNo);
      params.put("members", members);

      int count = projectDao.insertMembers(params);
      txManager.commit();
      return count;

    } catch (Exception e) {
      txManager.rollback();
      throw e;
    }
  }
}







