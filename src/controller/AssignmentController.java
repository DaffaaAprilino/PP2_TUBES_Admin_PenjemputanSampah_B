package controller;

import mapper.AssignmentMapper;
import model.Assignment;
import org.apache.ibatis.session.SqlSession;
import util.MyBatisUtil;

import java.util.List;

public class AssignmentController {

  // Mendapatkan semua penugasan
  public List<Assignment> getAllAssignments() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      return mapper.getAllAssignments();
    }
  }

  // Mendapatkan penugasan berdasarkan ID
  public Assignment getAssignmentById(int id) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      return mapper.getAssignmentById(id);
    }
  }

  // Menambahkan penugasan baru
  public void addAssignment(Assignment assignment) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.insertAssignment(assignment);
      session.commit();
    }
  }

  // Memperbarui penugasan
  public void updateAssignment(Assignment assignment) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.updateAssignment(assignment);
      session.commit();
    }
  }

  // Menghapus penugasan
  public void deleteAssignment(int id) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.deleteAssignment(id);
      session.commit();
    }
  }

  // Menerima penugasan
  public void acceptAssignment(int assignmentId) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.acceptAssignment(assignmentId);
      session.commit();
    }
  }

  // Menambahkan data koleksi (berat dan poin)
  public void addCollection(int assignmentId, double weight, int points) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.addCollection(assignmentId, weight, points);
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to add collection: " + e.getMessage());
    }
  }

  // Mendapatkan penugasan berdasarkan rentang tanggal
  public List<Assignment> getAssignmentsByDateRange(String startDate, String endDate) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      return mapper.getAssignmentsByDateRange(startDate, endDate);
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to load assignments by date range: " + e.getMessage());
    }
  }

  // Menandai penugasan sebagai "Collected"
  public void markAsCollected(int assignmentId) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      AssignmentMapper mapper = session.getMapper(AssignmentMapper.class);
      mapper.updateCollectedAt(assignmentId);
      session.commit();
    } catch (Exception e) {
      e.printStackTrace();
      throw new RuntimeException("Failed to update collected_at: " + e.getMessage());
    }
  }

  public int getTotalAssignments() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      return session.getMapper(AssignmentMapper.class).getTotalAssignments();
    }
  }

  public double getTotalWeightCollected() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      return session.getMapper(AssignmentMapper.class).getTotalWeightCollected();
    }
  }

  public int getTotalPoints() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      return session.getMapper(AssignmentMapper.class).getTotalPoints();
    }
  }

}
