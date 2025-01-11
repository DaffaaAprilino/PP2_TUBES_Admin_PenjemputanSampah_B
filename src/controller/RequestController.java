package controller;

import mapper.RequestMapper;
import model.Request;
import org.apache.ibatis.session.SqlSession;
import util.MyBatisUtil;

import java.util.List;

public class RequestController {

  // Mendapatkan semua permintaan
  public List<Request> getAllRequests() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      return mapper.getAllRequests();
    }
  }

  // Mendapatkan permintaan berdasarkan ID
  public static Request getRequestById(int requestId) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      return mapper.getRequestById(requestId);
    }
  }

  // Menambahkan permintaan baru
  public void addRequest(Request request) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      mapper.insertRequest(request);
      session.commit();
    }
  }

  // Memperbarui permintaan
  public void updateRequest(Request request) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      mapper.updateRequest(request);
      session.commit();
    }
  }

  // Menghapus permintaan
  public void deleteRequest(int id) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      mapper.deleteRequest(id);
      session.commit();
    }
  }

  public void updateStatus(int requestId, String status) {
    try (SqlSession session = MyBatisUtil.getSession()) {
      RequestMapper mapper = session.getMapper(RequestMapper.class);
      mapper.updateStatus(requestId, status);
      session.commit();
    }
  }

  public int getTotalRequests() {
    try (SqlSession session = MyBatisUtil.getSession()) {
      return session.getMapper(RequestMapper.class).getTotalRequests();
    }
  }

}
