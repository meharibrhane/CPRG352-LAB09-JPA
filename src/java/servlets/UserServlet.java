package servlets;


import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;
import models.User;
import services.RoleService;
import services.UserService;



public class UserServlet extends HttpServlet {

  
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        UserService allUser = new UserService();
        RoleService allRole = new RoleService();
        HttpSession session =request.getSession();  
        
        String actionAttribute = request.getParameter("action");
        
        if(actionAttribute != null && actionAttribute.equals("Edit") ){
            

            String editEmail = request.getParameter("editEmail");
            String editFirstName = request.getParameter("editFirstName");
            String editLastName = request.getParameter("editLastName");
        
            session.setAttribute("editEmail", editEmail);

            request.setAttribute("editEmail", editEmail);
            request.setAttribute("editFirstName", editFirstName);
            request.setAttribute("editLastName", editLastName);
        }
        else if(actionAttribute != null && actionAttribute.equals("Delete") ){
            
            String editEmail = request.getParameter("deleteEmail");
            
            try {
                allUser.delete(editEmail);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        
        
        
        try {
            List users = allUser.getAll();
            List roles = allRole.getAll();
            session.setAttribute("users", users);
            session.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        
        if(action.equals("Add")){
            try {
                addUser(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(action.equals("Edit")){
            try {
                editUser(request, response);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        UserService allUser = new UserService();
        RoleService allRole = new RoleService();
        HttpSession session =request.getSession();
        try {
            List users = allUser.getAll();
            List roles = allRole.getAll();
            session.setAttribute("users", users);
            session.setAttribute("roles", roles);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
        return;
        
   

    }
    
    protected void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String role = request.getParameter("roleOption");
        
        UserService allUser = new UserService();
        RoleService allRole = new RoleService();
        
        List<Role> roles = allRole.getAll();
        int roleID = 0;
        for(Role r: roles){
            if(r.getRoleName().equals(role)){
                roleID = r.getRoleId();
            }
                
        }
        
        allUser.insert(email, true, firstname, lastname, password, roleID);
    }
    
    protected void editUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception{
        
        HttpSession session = request.getSession();  
        
        String editEmail = (String) session.getAttribute("editEmail");
        
        String editedEmail = request.getParameter("Editedemail");
        String Editedfirstname = request.getParameter("Editedfirstname");
        String Editedlasttname = request.getParameter("Editedlastname");
        String Editedrole = request.getParameter("Editedrole");
        
        User editedUSer = null;
        int roleID = 0;
        
        UserService editUser = new UserService();
        RoleService allRole = new RoleService();
        
        List<Role> roles = allRole.getAll();
        List <User> users = editUser.getAll();
        
        for (User u: users){
            if(u.getEmail().equals(editEmail)){
                editedUSer = u;
            }
                
        }
        
        for(Role r: roles){
            if(r.getRoleName().equals(Editedrole)){
                roleID = r.getRoleId();
            }
                
        }
        
        editUser.update(editedEmail, true, Editedfirstname, Editedlasttname, editedUSer.getPassword(), roleID);
    }

    
}