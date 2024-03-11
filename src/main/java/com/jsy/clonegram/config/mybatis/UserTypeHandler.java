package com.jsy.clonegram.config.mybatis;

import com.jsy.clonegram.dao.Grade;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * MyBatis에서 Enum을 사용하기 위해서 TypeHandler를 사용해야하는데 이를 구현하는 클래스
 */
@MappedTypes(Grade.class)
public class UserTypeHandler implements TypeHandler<Grade> {
    @Override
    public void setParameter(PreparedStatement ps, int i, Grade parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public Grade getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnum(code);
    }

    @Override
    public Grade getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnum(code);
    }

    @Override
    public Grade getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnum(code);
    }

    private Grade getCodeEnum(String code) {
        return switch (code) {
            case "USER000" -> Grade.Admin;
            case "USER001" -> Grade.Bronze;
            case "USER002" -> Grade.Silver;
            case "USER003" -> Grade.Gold;
            case "USER004" -> Grade.Platinum;
            case "USER005" -> Grade.Diamond;
            default -> null;
        };
    }
}
