package mapper;

import model.Request;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface RequestMapper {
  @Select("SELECT * FROM requests")
  List<Request> getAllRequests();

  @Select("SELECT * FROM requests WHERE id = #{id}")
  Request getRequestById(int id);

  @Insert("INSERT INTO requests (user_id, description, status, created_at) VALUES (#{userId}, #{description}, #{status}, #{createdAt})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertRequest(Request request);

  @Update("UPDATE requests SET user_id = #{userId}, description = #{description}, status = #{status} WHERE id = #{id}")
  void updateRequest(Request request);

  @Delete("DELETE FROM requests WHERE id = #{id}")
  void deleteRequest(int id);

  @Update("UPDATE requests SET status = #{status} WHERE id = #{id}")
  void updateStatus(@Param("id") int id, @Param("status") String status);

  @Select("SELECT COUNT(*) FROM requests")
  int getTotalRequests();

}
