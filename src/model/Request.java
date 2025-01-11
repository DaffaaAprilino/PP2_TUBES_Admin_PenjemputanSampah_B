package model;

import java.sql.Timestamp;

public class Request {
  private int id;
  private int userId;
  private String description;
  private String status;
  private Timestamp createdAt;

  // Constructor
  public Request() {
  }

  public Request(int id, int userId, String description, String status, Timestamp createdAt) {
    this.id = id;
    this.userId = userId;
    this.description = description;
    this.status = status;
    this.createdAt = createdAt;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Timestamp createdAt) {
    this.createdAt = createdAt;
  }
}
