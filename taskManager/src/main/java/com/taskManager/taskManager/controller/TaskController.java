package com.taskManager.taskManager.controller;

import com.taskManager.taskManager.model.Task;
import com.taskManager.taskManager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }

    /**
     * Creates a new task for the authenticated user.
     *
     * @param req The task details provided by the client.
     * @param principal The authenticated user making the request.
     * @return A ResponseEntity containing the created task or an error message.
     */
    @PostMapping
    public ResponseEntity<?> createTask(@RequestBody TaskRequest req, Principal principal){
        // Validate if the request object or required fields are null
        if (req == null || req.title() == null || req.description() == null || req.dueDate() == null) {
            // Return a bad request response if any required field is missing
            return ResponseEntity.badRequest().body("Title, description, and due date are required.");
        }

        // Check if the title or description fields are empty or blank
        if (req.title().isBlank() || req.description().isBlank()) {
            // Return a bad request response if title or description is blank
            return ResponseEntity.badRequest().body("Title and description cannot be blank.");
        }

        // Ensure the due date is not in the past
        if (req.dueDate().isBefore(LocalDate.now())) {
            // Return a bad request response if the due date is in the past
            return ResponseEntity.badRequest().body("Due date cannot be in the past.");
        }

        // Retrieve the user ID from the Principal object, which represents the authenticated user
        String userId = principal.getName();

        // Call the taskService to create a new task with the given user ID, title, description, and due date
        Task task = taskService.createTask(userId, req.title(), req.description(), req.dueDate());

        // Return a successful response with the created task object
        return ResponseEntity.ok(task);
    }



    /**
     * Retrieves all tasks for the authenticated user.
     *
     * @param principal The authenticated user's information.
     * @return A ResponseEntity containing a list of tasks or a bad request response if tasks are null.
     */
    @GetMapping
    public ResponseEntity<?> getAllTasks(Principal principal) {

        // Fetch all tasks associated with the authenticated user
        List<Task> tasks = taskService.getAllTasks(principal.getName());

        // Return the list of tasks if not null, otherwise a bad request response
        return tasks != null ? ResponseEntity.ok(tasks) : ResponseEntity.badRequest().build();
    }

    /**
     * Retrieves a specific task by its ID for the authenticated user.
     *
     * @param id        The unique identifier of the task.
     * @param principal The authenticated user's information.
     * @return A ResponseEntity containing the task or a bad request response if the task is not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable UUID id, Principal principal) {
        try {
            // Fetch the task by its ID and the authenticated user
            Task task = taskService.getTaskById(id, principal.getName());
            return ResponseEntity.ok(task);
        } catch (NoSuchElementException e) {
            // Return a bad request response if the task does not exist
            return ResponseEntity.badRequest().body("No task available");
        }
    }

    /**
     * Updates a specific task for the authenticated user.
     *
     * @param id        The unique identifier of the task to update.
     * @param req       The updated task details provided by the client.
     * @param principal The authenticated user's information.
     * @return A ResponseEntity containing the updated task or a not found response if the task does not exist.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable UUID id,
                                        @RequestBody TaskRequest req, Principal principal) {
        try {
            // Update the task with the provided details
            Task updatedTask = taskService.updateTask(id, principal.getName(), req.title(), req.description(), req.priority, req.dueDate());

            return ResponseEntity.ok(updatedTask);
        } catch (NoSuchElementException e) {
            // Return a not found response if the task does not exist
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes a specific task for the authenticated user.
     *
     * @param id        The unique identifier of the task to delete.
     * @param principal The authenticated user's information.
     * @return A ResponseEntity indicating successful deletion or a not found response if the task does not exist.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable UUID id, Principal principal) {
        try {
            // Delete the task by its ID for the authenticated user
            taskService.deleteTask(id, principal.getName());

            return ResponseEntity.ok("Successfully deleted the task");
        } catch (NoSuchElementException e) {
            // Return a not found response if the task does not exist
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Marks a specific task as complete for the authenticated user.
     *
     * @param id        The unique identifier of the task to mark as complete.
     * @param principal The authenticated user's information.
     * @return A ResponseEntity containing the updated task or a not found response if the task does not exist.
     */
    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> markTaskAsComplete(@PathVariable UUID id, Principal principal) {
        try {
            // Mark the task as complete
            Task completedTask = taskService.markTaskAsComplete(id, principal.getName());

            return ResponseEntity.ok(completedTask);
        } catch (NoSuchElementException e) {
            // Return a not found response if the task does not exist
            return ResponseEntity.notFound().build();
        }
    }


    public record TaskRequest(String title, String description, String priority,
                              LocalDate dueDate){
    }
}
