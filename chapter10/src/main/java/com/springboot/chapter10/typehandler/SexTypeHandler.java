package com.springboot.chapter10.typehandler;

import com.springboot.chapter10.enumeration.SexEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 声明JdbcType为整型
@MappedJdbcTypes(JdbcType.INTEGER)
// 声明JavaType为SexEnum
@MappedTypes(value = SexEnum.class)
public class SexTypeHandler extends BaseTypeHandler<SexEnum> {
    /**
     * 设置非空性别参数
     * @param preparedStatement
     * @param idx
     * @param sex
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int idx, SexEnum sex, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(idx, sex.getId());
    }

    /**
     * 通过列名读取性别
     * @param resultSet
     * @param col
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, String col) throws SQLException {
        int sex = resultSet.getInt(col);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /**
     * 通过下标读取性别
     * @param resultSet
     * @param idx
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(ResultSet resultSet, int idx) throws SQLException {
        int sex = resultSet.getInt(idx);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }

    /**
     * 通过存储过程读取性别
     * @param callableStatement
     * @param idx
     * @return
     * @throws SQLException
     */
    @Override
    public SexEnum getNullableResult(CallableStatement callableStatement, int idx) throws SQLException {
        int sex = callableStatement.getInt(idx);
        if(sex != 1 && sex != 2) {
            return null;
        }
        return SexEnum.getEnumById(sex);
    }
}
