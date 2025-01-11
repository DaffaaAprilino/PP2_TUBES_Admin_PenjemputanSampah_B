package mapper;

import model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
  @Select("SELECT * FROM users")
  List<User> getAllUsers();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User getUserById(int id);

  @Insert("INSERT INTO users (username, password, role) VALUES (#{username}, #{password}, #{role})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertUser(User user);

  @Update("UPDATE users SET username = #{username}, password = #{password}, role = #{role} WHERE id = #{id}")
  void updateUser(User user);

  @Delete("DELETE FROM users WHERE id = #{id}")
  void deleteUser(int id);

}
