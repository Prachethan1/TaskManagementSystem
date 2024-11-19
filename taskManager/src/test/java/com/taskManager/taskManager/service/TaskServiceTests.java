package com.taskManager.taskManager.service;

import com.taskManager.taskManager.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class TaskServiceTests {

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskService();
    }

    @Test
    void createTaskTest1() {

        /**
         * Success testcase - due date is valid
         */
        String title = "Reading";
        String description = "Reading java books";
        LocalDate dueDate = LocalDate.now().plusDays(1); // Valid due date
        String userId = "Ram";
        Task task = taskService.createTask(userId, title, description, dueDate);

        assertNotNull(task);
        assertEquals(title, task.getTitle());
        assertEquals(description, task.getDescription());
        assertEquals(Task.Priority.MEDIUM, task.getPriority());
        assertEquals(dueDate, task.getDueDate());
        assertEquals(Task.Status.PENDING, task.getStatus());
        assertNotNull(task.getId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void createTaskTest2() {

        /**
         * Failure testcase - while creating task the due date cannot be past date
         */
        String title = "Writing";
        String description = "Need to complete writing a book";
        LocalDate dueDate = LocalDate.now().minusDays(1); // Past date
        String userId = "Ram";
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> taskService.createTask(userId, title, description, dueDate)
        );

        assertEquals("Due date cannot be in the past", exception.getMessage());
    }


}
