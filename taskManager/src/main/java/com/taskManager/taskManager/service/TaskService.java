package com.taskManager.taskManager.service;

import com.taskManager.taskManager.model.Task;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private final Map<UUID, Task> taskStore = new HashMap<>();

    /**
     * Creates a new task for the specified user.
     *
     * @param userId      The ID(username) of the user creating the task.
     * @param title       The title of the task.
     * @param description The description of the task.
     * @param dueDate     The due date for the task.
     * @return The created task.
     * @throws IllegalArgumentException if the due date is in the past.
     */
    public Task createTask(String userId, String title, String description, LocalDate dueDate) {
        // Validate that the due date is not in the past
        if (dueDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Due date cannot be in the past");
        }
        // Create a new task and store it in the task store
        Task task = new Task(userId, title, description, dueDate);
        taskStore.put(task.getId(), task);
        return task;
    }

    /**
     * Retrieves all tasks for a specific user.
     *
     * @param userId The ID of the user whose tasks are to be retrieved.
     * @return A list of tasks belonging to the user.
     */
    public List<Task> getAllTasks(String userId) {
        // Filter tasks by the user ID and return as a list
        return taskStore.values().stream()
                .filter(task -> task.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a task by its ID for a specific user.
     *
     * @param id     The unique identifier of the task.
     * @param userId The ID of the user requesting the task.
     * @return The requested task.
     * @throws NoSuchElementException if the task is not found or the user is not authorized.
     */
    public Task getTaskById(UUID id, String userId) {
        // Retrieve the task by ID and verify ownership
        Task task = taskStore.get(id);
        if (task == null || !task.getUserId().equals(userId)) {
            throw new NoSuchElementException("Task not found or not authorized user");
        }
        return task;
    }

    /**
     * Updates a task for a specific user.
     *
     * @param id          The unique identifier of the task.
     * @param userId      The ID of the user updating the task.
     * @param title       The updated title of the task.
     * @param description The updated description of the task.
     * @param dueDate     The updated due date of the task.
     * @return The updated task.
     * @throws NoSuchElementException if the task is not found.
     */
    public Task updateTask(UUID id, String userId, String title, String description, String priority, LocalDate dueDate) {
        // Fetch the task by ID and authorized user
        Task task = getTaskById(id, userId);
        if (!taskStore.containsValue(task)) {
            throw new NoSuchElementException("Task not found");
        }

        Task.Priority parsedPriority;
        try {
            parsedPriority = Task.Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid priority value. Allowed values: HIGH, MEDIUM, LOW");
        }

        // Update task details
        task.setTitle(title);
        task.setDescription(description);
        task.setPriority(parsedPriority);
        task.setDueDate(dueDate);
        task.setUpdatedAt(LocalDateTime.now());
        task.setStatus(Task.Status.IN_PROGRESS);
        return task;
    }

    /**
     * Deletes a task for a specific user.
     *
     * @param id     The unique identifier of the task to delete.
     * @param userId The ID of the user deleting the task.
     * @throws NoSuchElementException if the task is not found.
     */
    public void deleteTask(UUID id, String userId) {
        // Fetch the task by ID and verify ownership
        Task task = getTaskById(id, userId);
        // Remove the task from the task store
        if (!taskStore.containsValue(task)) {
            throw new NoSuchElementException("Task not found");
        }
        taskStore.remove(task.getId());
    }

    /**
     * Marks a task as complete for a specific user.
     *
     * @param id     The unique identifier of the task to mark as complete.
     * @param userId The ID of the user marking the task as complete.
     * @return The updated task with its status set to COMPLETED.
     * @throws NoSuchElementException if the task is not found.
     */
    public Task markTaskAsComplete(UUID id, String userId) {
        // Fetch the task by ID and verify ownership
        Task task = getTaskById(id, userId);
        // Update the task's status to completed
        task.setStatus(Task.Status.COMPLETED);
        return task;
    }
}
