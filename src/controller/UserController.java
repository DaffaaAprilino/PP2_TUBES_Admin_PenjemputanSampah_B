package controller;

import mapper.UserMapper;
import model.User;
import org.apache.ibatis.session.SqlSession;
import util.MyBatisUtil;

import java.util.List;

public class UserController {
  public List<User> getAllUsers() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      UserMapper mapper = session.getMapper(UserMapper.class);
      return mapper.getAllUsers();
    }
  }

  public static User getUserById(int userId) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      UserMapper mapper = session.getMapper(UserMapper.class);
      return mapper.getUserById(userId);
    }
  }

  public void addUser(User user) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      UserMapper mapper = session.getMapper(UserMapper.class);
      mapper.insertUser(user);
      session.commit();
    }
  }

  public void updateUser(User user) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      UserMapper mapper = session.getMapper(UserMapper.class);
      mapper.updateUser(user);
      session.commit();
    }
  }

  public void deleteUser(int userId) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      UserMapper mapper = session.getMapper(UserMapper.class);
      mapper.deleteUser(userId);
      session.commit();
    }
  }

}
