package model;

import java.sql.Timestamp;

public class Assignment {

  private int id;
  private int requestId;
  private int courierId;
  private String status;
  private Timestamp acceptedAt;
  private Double weight;
  private Integer points;
  private Timestamp collectedAt;

  // Constructor default
  public Assignment() {
  }

  // Constructor dengan parameter
  public Assignment(int id, int requestId, int courierId, String status) {
    this.id = id;
    this.requestId = requestId;
    this.courierId = courierId;
    this.status = status;
  }

  // Getters and Setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRequestId() {
    return requestId;
  }

  public void setRequestId(int requestId) {
    this.requestId = requestId;
  }

  public int getCourierId() {
    return courierId;
  }

  public void setCourierId(int courierId) {
    this.courierId = courierId;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getAcceptedAt() {
    return acceptedAt;
  }

  public void setAcceptedAt(Timestamp acceptedAt) {
    this.acceptedAt = acceptedAt;
  }

  public Double getWeight() {
    return weight;
  }

  public void setWeight(Double weight) {
    this.weight = weight;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  public Timestamp getCollectedAt() {
    return collectedAt;
  }

  public void setCollectedAt(Timestamp collectedAt) {
    this.collectedAt = collectedAt;
  }
}
