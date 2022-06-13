package com.example.TODO.Dao;

import com.example.TODO.Entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.OrderedBidiMap;
import org.apache.commons.collections4.bidimap.TreeBidiMap;
import org.apache.commons.io.FileUtils;

import com.fasterxml.jackson.databind.module.SimpleModule;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.*;
import java.nio.file.Paths;

public class UserDao {

    /*** I tried using apache collections but i couldn't parse it back to a map
     *  so i used normal hashmap
     *
     */
    static  HashMap<String,String> userLoginList = new HashMap<>();
    static File file = FileUtils.getFile("user.json");
    static ObjectMapper mapper = new ObjectMapper();

    static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(UserDao.class);



    public static HashMap<String,String> readAll() throws IOException {
        FileUtils.touch(file);
        userLoginList = mapper.readValue(file,userLoginList.getClass());
        return userLoginList;
    }

    public static void addUser(User user)
    {
        userLoginList.put(user.getEmail(),user.getPassword());
    }

    public static void commit() throws IOException {
        FileUtils.touch(file);
        mapper.writeValue(file, userLoginList);


    }

    public static boolean isUserExist (String email) throws IOException {
        readAll();
        if(userLoginList.containsKey(email)) {
            return true;
        }
        else
            return false;

    }

    public static boolean changePassword (String email, String newPassword) throws IOException {
        readAll();
        if(isUserExist(email))
        {
            userLoginList.put(email,newPassword);
            return true;
        }
        else
        {
            logger.error("user doesn't exist");
            return false;
        }
    }

    public static boolean login(String email, String password) throws IOException {
        readAll();
        return userLoginList.get(email).equals(password);
    }
}



