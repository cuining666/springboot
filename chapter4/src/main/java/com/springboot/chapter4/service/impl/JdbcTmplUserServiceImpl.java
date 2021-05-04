package com.springboot.chapter4.service.impl;

import com.springboot.chapter4.enumeration.SexEnum;
import com.springboot.chapter4.pojo.User;
import com.springboot.chapter4.service.JdbcTmplUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

@Service
public class JdbcTmplUserServiceImpl implements JdbcTmplUserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //获取映射关系
    private RowMapper<User> getUserMapper() {
        RowMapper<User> userRowMapper = (ResultSet rs, int rownum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUserName(rs.getString("user_name"));
            int sexId = rs.getInt("sex");
            SexEnum sexEnum = SexEnum.getEnumById(sexId);
            user.setSex(sexEnum);
            user.setNote(rs.getString("note"));
            return user;
        };
        return userRowMapper;
    }

    @Override
    public User getUserById(Long id) {
        // 执行sql
        String sql = "select id, user_name, sex, note from t_user where id = ?";
        // 参数
        Object[] params = new Object[]{id};
        User user = jdbcTemplate.queryForObject(sql, getUserMapper(), params);
        return user;
    }

    @Override
    public User getUserById2(Long id) {
        User result = jdbcTemplate.execute((Statement stmt) -> {
            String sql1 = "select count(*) total from t_user where id=" + id;
            ResultSet rs1 = stmt.executeQuery(sql1);
            while (rs1.next()) {
                int total = rs1.getInt("total");
                System.out.println(total);
            }

            String sql2 = "select id, user_name, sex, note from t_user where id=" + id;
            ResultSet rs2 = stmt.executeQuery(sql2);
            User user = null;
            while (rs2.next()) {
                int rowNum = rs2.getRow();
                user = getUserMapper().mapRow(rs2, rowNum);
            }
            return user;
        });
        return result;
    }

    @Override
    public User getUserById3(Long id) {
        User result = jdbcTemplate.execute((Connection conn) -> {
            String sql1 = "select count(*) total from t_user where id=?";
            PreparedStatement ps1 = conn.prepareStatement(sql1);
            ps1.setLong(1, id);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                System.out.println(rs1.getInt("total"));
            }

            String sql2 = "select id, user_name, sex, note from t_user where id=?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setLong(1, id);
            ResultSet rs2 = ps2.executeQuery();
            User user = null;
            while (rs2.next()) {
                int rowNum = rs2.getRow();
                user = getUserMapper().mapRow(rs2, rowNum);
            }
            return user;
        });
        return result;
    }

    @Override
    public List<User> findUsers(String userName, String note) {
        String sql = "select id, user_name, sex, note from t_user "
                + "where user_name like concat('%',?,'%') "
                + "and note like concat('%',?,'%')";
        Object[] params = new Object[]{userName, note};
        List<User> users = jdbcTemplate.query(sql, getUserMapper(), params);
        return users;
    }

    @Override
    public int insertUser(User user) {
        String sql = "insert into t_user (user_name, sex, note) values(?, ?, ?)";
        return jdbcTemplate.update(sql, user.getUserName(), user.getSex().getId(), user.getNote());
    }

    @Override
    public int updateUser(User user) {
        String sql = "update t_user set user_name=?, sex=?, note=? where id=?";
        return jdbcTemplate.update(sql, user.getUserName(), user.getSex().getId(),user.getNote(), user.getId());
    }

    @Override
    public int deleteUserById(Long id) {
        String sql = "delete from t_user where id=?";
        return jdbcTemplate.update(sql, id);
    }
}
