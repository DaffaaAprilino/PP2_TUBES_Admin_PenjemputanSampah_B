package mapper;

import model.Assignment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AssignmentMapper {

  // Mendapatkan semua penugasan
  @Select("SELECT * FROM assignments")
  List<Assignment> getAllAssignments();

  // Mendapatkan penugasan berdasarkan ID
  @Select("SELECT * FROM assignments WHERE id = #{id}")
  Assignment getAssignmentById(int id);

  // Menambahkan penugasan baru
  @Insert("INSERT INTO assignments (request_id, courier_id, status) VALUES (#{requestId}, #{courierId}, #{status})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  void insertAssignment(Assignment assignment);

  // Memperbarui penugasan
  @Update("UPDATE assignments SET request_id = #{requestId}, courier_id = #{courierId}, status = #{status} WHERE id = #{id}")
  void updateAssignment(Assignment assignment);

  // Menghapus penugasan
  @Delete("DELETE FROM assignments WHERE id = #{id}")
  void deleteAssignment(int id);

  // Menerima penugasan
  @Update("UPDATE assignments SET accepted_at = NOW(), status = 'picked_up' WHERE id = #{id}")
  void acceptAssignment(int id);

  // Menambahkan koleksi (berat dan poin)
  @Update("UPDATE assignments SET weight = #{weight}, points = #{points} WHERE id = #{id}")
  void addCollection(@Param("id") int assignmentId, @Param("weight") double weight, @Param("points") int points);

  // Mendapatkan penugasan berdasarkan rentang tanggal
  @Select("SELECT * FROM assignments WHERE collected_at BETWEEN #{startDate} AND #{endDate}")
  List<Assignment> getAssignmentsByDateRange(@Param("startDate") String startDate, @Param("endDate") String endDate);

  // Memperbarui waktu pengumpulan
  @Update("UPDATE assignments SET collected_at = NOW() WHERE id = #{id}")
  void updateCollectedAt(@Param("id") int assignmentId);

  @Select("SELECT COUNT(*) FROM assignments")
  int getTotalAssignments();

  @Select("SELECT SUM(weight) FROM assignments WHERE weight IS NOT NULL")
  double getTotalWeightCollected();

  @Select("SELECT SUM(points) FROM assignments WHERE points IS NOT NULL")
  int getTotalPoints();

}
