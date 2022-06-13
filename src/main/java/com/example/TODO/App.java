package com.example.TODO;

import com.example.TODO.Dao.TodoDao;
import com.example.TODO.Dao.UserDao;
import com.example.TODO.Entity.Todo;
import com.example.TODO.Entity.User;
import com.example.TODO.enums.Status;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{

    private static final int OPTION_REGISTER_USER = 1;
    private static final int OPTION_USER_LOGIN = 2;
    private static final int OPTION_QUIT = 3;

    private static final int OPTION_ADD_TODO = 1;
    private static final int OPTION_VIEW_ALL_TODO = 2;
    private static final int OPTION_VIEW_ALL_ACTIVE_TODO = 3;
    private static final int OPTION_VIEW_ALL_COMPLETED_TODO = 4;
    private static final int OPTION_COMPLETE_TODO = 5;
    private static final int OPTION_DELETE_TODO = 6;
    private static final int OPTION_UPDATE_TODO = 7;
    private static final int OPTION_VIEW_TODO_BASED_ON_CRITERIA = 8;
    private static final int OPTION_CHANGE_PASSWORD = 9;
    private static final int OPTION_SIGN_OUT =10;



    private static final int OPTION_VIEW_TODO_BASED_ON_TITLE = 1;
    private static final int OPTION_VIEW_TODO_BASED_ON_DATE = 2;

    static String loggedInUserEmail;
    static boolean isUserLoggedIn;

    static Scanner scanner = new Scanner(System.in);
    static ObjectMapper mapper = new ObjectMapper();


    public static void main( String[] args ) throws IOException {

        boolean quitter=false;
        while(!quitter)
        {
            System.out.println();
            int option = homeOptions();
            System.out.println();

            if (option == OPTION_QUIT)
            {
                quitter = true;
                System.out.println("Good bye");
            }
            else if (option == OPTION_REGISTER_USER)
            {
                registerUser();
            }
            else if(option == OPTION_USER_LOGIN)
            {
                Login();
            }
            else
            {       System.out.println("Please enter a valid entry");

            }

        }
    }

    private static int homeOptions() {
        int option;

        while (true) {
            System.out.printf("%d -> Create User%n", OPTION_REGISTER_USER);
            System.out.printf("%d -> Sign in%n", OPTION_USER_LOGIN);
            System.out.printf("%d -> Quit%n", OPTION_QUIT);

            System.out.print("Please choose one of the options to continue: ");
            String input = scanner.next();

            try {
                option = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.out.println(input + " is not a valid option");
            }

            System.out.println();
        }

        return option;
    }

    private static void registerUser() throws IOException {
        String name="";
        String email="";
        String password="";

        boolean userExist=true;

        while (userExist) {
            System.out.print("Enter your name: ");
            name = scanner.next().trim();

            System.out.print("Enter your email: ");
            email = scanner.next().trim();

            if (UserDao.isUserExist(email)) {
                System.out.println("Email already used");

            }
            else{
                userExist=false;
            }
        }

        System.out.print("Choose a password: ");
        password = scanner.next().trim();

        System.out.print("confirm password: ");
        String confirmPassword=scanner.next();

        User user ;
        if(password.equals(confirmPassword))
        {
            user=new User(name,email,password);
            UserDao.addUser(user);
        }
        else
        {
            System.out.println("Passwords not the same");
            registerUser();
        }
    }

    private static void Login()
    {
        System.out.print("Enter your email: ");
        String email = scanner.next().trim();

        System.out.print("Enter your password: ");
        String password = scanner.next();

        try {
            if(UserDao.login(email,password))
            {
                mainMenuOptions();
                isUserLoggedIn=true;
                loggedInUserEmail= email;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void mainMenuOptions()
    {
        while (isUserLoggedIn) {
            System.out.println();
            int option = getLoggedInMenuOption();
            System.out.println();




            if (option == OPTION_ADD_TODO)
                addTodo();
            else if (option == OPTION_VIEW_ALL_TODO)
                viewAllTodo();
            else if (option == OPTION_VIEW_ALL_ACTIVE_TODO)
                viewAllActiveTodo();
            else if (option == OPTION_VIEW_ALL_COMPLETED_TODO)
                viewAllCompletedTodo();
            else if (option == OPTION_COMPLETE_TODO)
                completeTodo();
            else if (option == OPTION_DELETE_TODO)
                deleteTodo();
            else if (option == OPTION_UPDATE_TODO)
                updateTodo();
            else if (option == OPTION_VIEW_TODO_BASED_ON_CRITERIA) {
                int criteria = criteriaOptions();
                if(criteria == OPTION_VIEW_TODO_BASED_ON_TITLE)
                {
                    viewTodoBasedOnTitle();
                }
                if(criteria == OPTION_VIEW_TODO_BASED_ON_DATE)
                {
                    viewTodoBasedOnDate();
                }
            }
            else if (option == OPTION_CHANGE_PASSWORD)
                changePassword();
            else if (option == OPTION_SIGN_OUT) {
                System.out.println("You're now signed out!");
                isUserLoggedIn = false;
            }
            else
                System.out.println("Please choose a valid option");
        }
    }

    private static int getLoggedInMenuOption() {
        int option;

        while (true) {
            System.out.printf("%d -> Create a todo%n", OPTION_ADD_TODO);
            System.out.printf("%d -> Show all todo%n", OPTION_VIEW_ALL_TODO);
            System.out.printf("%d -> view all active todo%n", OPTION_VIEW_ALL_ACTIVE_TODO);
            System.out.printf("%d -> view all completed todo%n", OPTION_VIEW_ALL_COMPLETED_TODO);
            System.out.printf("%d -> complete todo%n", OPTION_COMPLETE_TODO);
            System.out.printf("%d -> delete todo%n", OPTION_DELETE_TODO);
            System.out.printf("%d -> update todo%n", OPTION_UPDATE_TODO);
            System.out.printf("%d -> view all todo based on criteria%n", OPTION_VIEW_TODO_BASED_ON_CRITERIA);
            System.out.printf("%d -> change password%n", OPTION_CHANGE_PASSWORD);
            System.out.printf("%d -> Sign out%n", OPTION_SIGN_OUT);

            System.out.print("Please choose one of the options to continue: ");
            String input = scanner.next();

            try {
                option = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.out.println(input + " is not a valid option");
            }

            System.out.println();
        }

        return option;
    }

    private static void addTodo()
    {
        System.out.print("Enter the title of your todo: ");
        String title = scanner.next().trim();

        System.out.print("Enter the content of the todo: ");
        String content = scanner.next();

        Todo todo= new Todo(title,content);

        TodoDao.addTodo(loggedInUserEmail,todo);
    }

    private static void viewAllTodo()
    {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TodoDao.getAllTodoByUser(loggedInUserEmail)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewAllActiveTodo()
    {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TodoDao.getTodosOfUserBasedOnStatus(loggedInUserEmail, Status.ACTIVE)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewAllCompletedTodo()
    {
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TodoDao.getTodosOfUserBasedOnStatus(loggedInUserEmail, Status.COMPLETED)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void completeTodo()
    {
        System.out.print("Enter the title of your todo: ");
        String title = scanner.next().trim();
        try {
            TodoDao.completeTodo(loggedInUserEmail,title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteTodo()
    {
        System.out.print("Enter the title of your todo: ");
        String title = scanner.next().trim();
        try {
            TodoDao.deleteTodo(loggedInUserEmail,title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void updateTodo()
    {
        System.out.print("Enter the title of your todo: ");
        String title = scanner.next().trim();

        System.out.print("Enter the new title of your todo: ");
        String newTitle = scanner.next().trim();

        System.out.print("Enter the new content of your todo: ");
        String newContent = scanner.next().trim();

        Todo todo = new Todo(newTitle,newContent);
        try {
            TodoDao.updateTodo(loggedInUserEmail,title,todo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void changePassword()
    {
        System.out.println("Enter the new password: ");
        String newPassword = scanner.next().trim();

        System.out.println("Confirm new password: ");
        String confirmNewPassword = scanner.next().trim();

        if(newPassword.equals(confirmNewPassword)) {
            try {
                UserDao.changePassword(loggedInUserEmail, newPassword);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
            changePassword();
    }

    private static int criteriaOptions()
    {
        int option;

        while (true) {

            System.out.printf("%d -> view todo based on titlee%n", OPTION_VIEW_TODO_BASED_ON_TITLE);
            System.out.printf("%d -> view todo based on date%n", OPTION_VIEW_TODO_BASED_ON_DATE);

            System.out.print("Please choose one of the options to continue: ");
            String input = scanner.next();

            try {
                option = Integer.parseInt(input);
                break;
            } catch (Exception e) {
                System.out.println(input + " is not a valid option");
            }

            System.out.println();
        }

        return option;

    }

    private static void viewTodoBasedOnTitle()
    {
        System.out.println("Enter the title of the todo: ");
        String title = scanner.next().trim();

        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TodoDao.getTodoByUserBasedOnTitle(loggedInUserEmail,title)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void viewTodoBasedOnDate()
    {
        System.out.println("Enter the day of creation of the todo (dd-format): ");
        String day = scanner.next().trim();
        System.out.println("Enter the day of creation of the todo (MM-format): ");
        String month = scanner.next().trim();
        System.out.println("Enter the day of creation of the todo (yyyy-format): ");
        String year = scanner.next().trim();

        String date =year+"-"+month+"-"+day;

        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(TodoDao.getTodoByUserBasedOnDate(loggedInUserEmail,date)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
