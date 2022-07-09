package dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import models.*;


public class UserDB {

    public List<User> getAll() throws Exception {
     EntityManager em = DBUtil.getEmFactory().createEntityManager();;
        try{
            List<User> users = em.createNamedQuery("User.findALl", User.class).getResultList();
            return users;
            }finally {
              em.close();
            }
       
    }

    public User get(String email) throws Exception {
       EntityManager em = DBUtil.getEmFactory().createEntityManager();
       
        try {
           User user = em.find(User.class, email);
           return user;    
        } finally {
            em.close();
        }
    }

    public void insert(User user) throws Exception {
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
           trans.begin();
           user.getRole().getUserList().add(user);
           em.persist(user);
           em.merge(user);
           trans.commit();
        }catch(Exception e){
                trans.rollback();
        } finally {
            em.close();
        }

    }

    public void update(User user) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        String sql = "UPDATE user SET active=?, first_name=?, last_name=?, password=?, role=? WHERE email=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, user.getActive());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getPassword());
            statement.setInt(5, user.getRole().getId());
            statement.setString(6, user.getEmail());
            statement.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }

    public void delete(User user) throws Exception {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement statement = null;
        String sql = "DELETE FROM user WHERE email=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.executeUpdate();
        } finally {
            DBUtil.closePreparedStatement(statement);
            pool.freeConnection(connection);
        }
    }
}
