package org.company.app.data.manager;

import org.company.app.data.entity.UserEntity;
import org.company.app.util.BaseManager;
import org.company.app.util.MysqlDatabase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserEntityManager extends BaseManager
{
    public UserEntityManager(MysqlDatabase database)
    {
        super(database);
    }

    public void add(UserEntity user) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "INSERT INTO users_small(login, password, age, job) values(?,?,?,?)";
            PreparedStatement s = c.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            s.setString(1, user.getLogin());
            s.setString(2, user.getPassword());
            s.setInt(3, user.getAge());
            s.setString(4, user.getJob());
            s.executeUpdate();

            ResultSet keys = s.getGeneratedKeys();
            if(keys.next()) {
                user.setId(keys.getInt(1));
                return;
            }

            throw new SQLException("User not added");
        }
    }

    public UserEntity getById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM users_small WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getString("job")

                );
            }

            return null;
        }
    }

    public UserEntity getByLogin(String login) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM users_small WHERE login=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, login);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getString("job")

                );
            }

            return null;
        }
    }

    public UserEntity getByLoginAndPass(String login, String pass) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM users_small WHERE login=? AND password=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, login);
            s.setString(2, pass);

            ResultSet resultSet = s.executeQuery();
            if(resultSet.next()) {
                return new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getString("job")

                );
            }

            return null;
        }
    }

    public List<UserEntity> getAll() throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "SELECT * FROM users_small";
            Statement s = c.createStatement();
            ResultSet resultSet = s.executeQuery(sql);

            List<UserEntity> users = new ArrayList<>();
            while(resultSet.next()) {
                users.add(new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("login"),
                        resultSet.getString("password"),
                        resultSet.getInt("age"),
                        resultSet.getString("job")
                ));
            }
            return users;
        }
    }

    public int update(UserEntity user) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "UPDATE users_small SET login=?, password=?, age=?, job=? WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setString(1, user.getLogin());
            s.setString(2, user.getPassword());
            s.setInt(3, user.getAge());
            s.setString(4, user.getJob());
            s.setInt(5, user.getId());

            return s.executeUpdate();
        }
    }

    public int deleteById(int id) throws SQLException
    {
        try(Connection c = database.getConnection())
        {
            String sql = "DELETE FROM users_small WHERE id=?";
            PreparedStatement s = c.prepareStatement(sql);
            s.setInt(1, id);

            return s.executeUpdate();
        }
    }

    public int delete(UserEntity user) throws SQLException
    {
        return deleteById(user.getId());
    }
}
