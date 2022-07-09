
package services;
import models.Role;
import dataaccess.RoleDB;
import java.util.List;


public class RoleService {
    public List<Role> getAll() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }
}