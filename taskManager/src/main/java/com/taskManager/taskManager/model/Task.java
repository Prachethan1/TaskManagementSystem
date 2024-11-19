package com.taskManager.taskManager.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

    private UUID id; /* Used for unique identification of a task */
    private String userId; /* For identification of a user and it has username (for authentication) */
    private String title; /* title of task */
    private String description; /* description of a task */
    private Priority priority; /* priority of a task (additional feature) */
    private LocalDate dueDate; /* dueDate for submitting a task */
    private Status status; /* status of task which has values of pending,in_progress,completed*/
    private LocalDateTime createdAt; /* time of the creation of task */
    private LocalDateTime updatedAt; /* time of updating a task */


    public Task(String userId, String title, String description, LocalDate dueDate) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.priority = Priority.MEDIUM;
        this.dueDate = dueDate;
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }


    public enum Priority{
        HIGH,
        MEDIUM,
        LOW
    }

    public enum Status {
        PENDING,
        IN_PROGRESS,
        COMPLETED
    }

    public UUID getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        this.updatedAt = LocalDateTime.now();
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
