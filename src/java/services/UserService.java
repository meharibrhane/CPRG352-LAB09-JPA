
package services;

import dataaccess.RoleDB;
import models.*;
import dataaccess.UserDB;
import java.util.List;


public class UserService {
    public List<User> getAll() throws Exception {
        UserDB userDB = new UserDB();
        List<User> users = userDB.getAll();
        return users;
     
    }
    public User get(String email) throws Exception {
        UserDB userDB = new UserDB();
        User user = userDB.get(email);
        return user;
    }
    
    public void insert(String email, boolean activity, String first_name, String last_name, String password, int roleID) throws Exception{
        User user = new User(email, activity, first_name, last_name, password);
        Role role = null;
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        for(Role r: roles){
          if(r.getRoleId() == roleID){
           role = new Role(r.getRoleId(), r.getRoleName());
          }
        } 
        user.setRole(role);
        UserDB userDB = new UserDB();
        userDB.insert(user);
    }
    
    public void update(String email, boolean activity, String first_name, String last_name, String password, int roleID) throws Exception{
       User user = new User(email, activity, first_name, last_name, password);
       Role role = new Role();
       RoleDB roleDB = new RoleDB();
       List<Role> roles = roleDB.getAll();
       for(Role r: roles){
        if(r.getRoleId() == roleID){
          role = new Role(r.getRoleId(), r.getRoleName());
        }
       }
        user.setRole(role);
        UserDB userDB = new UserDB();
        userDB.update(user);
    }
    
    public void delete(String email) throws Exception{
       
        User user = new User();
        user.setEmail(email);
        UserDB userDB = new UserDB();
        userDB.delete(user);
    }
}






