package com.example.TODO.Dao;

import com.example.TODO.Entity.Todo;
import com.example.TODO.Entity.User;
import com.example.TODO.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodoDao {
    static List<HashMap<String, Todo>> todoList = new ArrayList<>();

    static File file = FileUtils.getFile("todo.json");
    static ObjectMapper mapper = new ObjectMapper();

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserDao.class);

    public static List<HashMap<String, Todo>> readAll() throws IOException {
        FileUtils.touch(file);
        todoList = mapper.readValue(file,todoList.getClass());
        return todoList;
    }

    public static void addTodo(String email, Todo todo)
    {
        HashMap<String, Todo> temporary= new HashMap<>();
        temporary.put(email, todo);
        todoList.add(temporary);
    }

    public static void commitTodo() throws IOException {
        FileUtils.touch(file);
        mapper.writeValue(file, todoList);
    }

    public static List<HashMap<String,Todo>> getAllTodoByUser(String email) throws IOException {
        readAll();
        List<HashMap<String, Todo>> userTodoList = new ArrayList<>();
        for (HashMap<String,Todo> todos :  todoList) {
            if (!todos.containsKey(email))
            {
                logger.error("No Todo List Found For User");
            }
            else
            {
                userTodoList.add(todos);
            }
        }
        return userTodoList;
    }

    public static Todo getTodoByUserBasedOnTitle(String email, String title) throws IOException {
        List<HashMap<String, Todo>> userTodoList = getAllTodoByUser(email);
        Todo todo = new Todo();
        for (HashMap<String,Todo> todos :  userTodoList) {

            if(todos.get(email).title.equals(title))
            {
                todo = todos.get(email);

            }
        }
        return todo;
    }

    public static Todo getTodoByUserBasedOnDate(String email, String date) throws IOException {
        List<HashMap<String, Todo>> userTodoList = getAllTodoByUser(email);
        Todo todo = new Todo();
        for (HashMap<String,Todo> todos :  userTodoList) {

            if(todos.get(email).date.equals(date))
            {
                todo = todos.get(email);

            }
        }
        return todo;
    }


    public static List<HashMap<String,Todo>> getTodosOfUserBasedOnStatus(String email, Status status) throws IOException {
        List<HashMap<String, Todo>> userTodoList = getAllTodoByUser(email);
        List<HashMap<String , Todo>> todoBasedOnStatus =new ArrayList<>();

        for (HashMap<String,Todo> todos :  userTodoList) {
            if(todos.get(email).getStatus().equals(status))
            {
                todoBasedOnStatus.add(todos);
            }
        }
        return todoBasedOnStatus;
    }


    public static void deleteTodo(String email, final String title) throws IOException {
        Todo todo =getTodoByUserBasedOnTitle(email,title);
        todoList.removeIf(n -> (n.get(email).equals(todo)));
        todo.setStatus(Status.DELETED);
        addTodo(email,todo);
    }

    public static void updateTodo(String email, String title, Todo updatedTodo) throws IOException {
        Todo todo =getTodoByUserBasedOnTitle(email,title);
        todoList.removeIf(n -> (n.get(email).equals(todo)));
        addTodo(email,updatedTodo);
    }

    public static void completeTodo(String email, final String title) throws IOException {
        Todo todo =getTodoByUserBasedOnTitle(email,title);
        todoList.removeIf(n -> (n.get(email).equals(todo)));
        todo.setStatus(Status.COMPLETED);
        addTodo(email,todo);
    }

}
