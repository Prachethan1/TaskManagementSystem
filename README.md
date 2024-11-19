
# Task Management System


This project is a Task Management API built using Spring Boot. The API allows users to manage tasks with basic CRUD operations. Each task has properties such as title, description, due date, and status. The application supports authentication and authorization to ensure that users can only manage their own tasks.


## Features

* Task Management: Easily add, update, retrieve, modify and delete tasks.
* Properties of a task:
    - id: Unique id for a task
    - userId: Identification of a user and has username as value. For authentication
    - title: Title of a task
    - description: Description of a task
    - priority: Priority of a task(HIGH, MEDIUM, LOW) additional feature
    - duedate: Deadline of a task
    - status: Status of a task(PENDING, IN_PROGRESS, COMPLETED)
    - createdAt: Date of creation of a task
    - updatedAt: Date of updation of a task
* Data Storage: Used in-memory data store for storing each task.
* Role-Based Access Control: Define roles and permissions.Created predefined users and role. Each user can have many tasks.(RAM and LAXMAN are predefined users)
* Mappings: A user can create many tasks (1 to many relation).
* Authorized: For accessing any functions of TaskManager, one should have USER as a role.
## Technologies Used :
* Spring Boot: Backend framework for building Java-based web applications.
* In-Memory data store: For storing the tasks
* IDE/Tool : Intellij idea
## Installation :
1.Clone the repository : 
```bash
$ git clone https://github.com/Prachethan1/TaskManagementSystem.git
```

2.Import the project inside intellij idea:

* Open intellij > file > open  > existing project > browse > finish.

3.Make sure you are in the taskManager directory.

4.Run the project (by running main method is TaskManager.java) OR right click on the project > Run As > Spring Boot App.

## API Reference

- Open postman for checking each api for TaskManager.

- Go to authorization -> select -> Basic auth -> provide username and password and then hit send button.

- Predefined users: 
User1:
Username: Ram,
password: r@123

User2:
Username: Laxman,
password: l@123

#### Get all tasks

```bash
  GET /tasks
  http://localhost:8080/tasks
```
![tasks](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/getAllTasks.png?raw=true)

#### Get a task

```bash
  GET /tasks/${task_id}
  http://localhost:8080/tasks/${task_id}
```
![task](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/getById.png?raw=true)

#### Create a task

```bash
  POST /tasks
  http://localhost:8080/tasks
```
Authorization:
![create](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/createtask2.png?raw=true)

Creation of task:
![create](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/createTask.png?raw=true)

#### Update a task

```bash
  PUT /tasks/${task_id}
  http://localhost:8080/tasks/${task_id}
```
![update](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/updateTask.png?raw=true)

#### Delete a task

```bash
  DELETE /tasks/${task_id}
  http://localhost:8080/tasks/${task_id}
```
![delete](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/deleteTask.png?raw=true)

#### Mark a task as complete 

```bash
  PATCH /tasks/${task_id}/complete
  http://localhost:8080/tasks/${task_id}/complete
```
![modify](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/markAsComplete.png?raw=true)

## Testing 

##### Testcase- Create task
Passed both test case related to creation of task

![test](https://github.com/Prachethan1/TaskManagementSystem/blob/main/taskManager/Screenshots/photos/testcase.png?raw=true)


## Tech Stack

**Backend:** Java, Spring Boot, Spring Security

**Database:** In-memory data store

