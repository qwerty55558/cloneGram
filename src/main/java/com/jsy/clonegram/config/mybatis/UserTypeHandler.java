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
 *
 */
@MappedTypes(Grade.class)
public class UserTypeHandler implements TypeHandler<Grade> {

    // PreparedStatement 에 매개변수를 설정하는 메서드, 객체를 받아와 pstmt 에 해당하는 인덱스에 문자열 형태로 설정함.
    @Override
    public void setParameter(PreparedStatement ps, int i, Grade parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    // ResultSet 에서 결과를 가져오는 메서드, 열 이름에 해당하는 문자열 값을 가져와서 getCodeEnum 메서드를 사용하여 Grade 객체를 변환해주고 반환
    @Override
    public Grade getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnum(code);
    }

    // 위와 같지만 열 인덱스 번호로 가져옴
    @Override
    public Grade getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnum(code);
    }

    // 저장 프로시저의 호출 결과에서 결과를 가져오는 메서드
    @Override
    public Grade getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnum(code);
    }

    // 문자열 값을 기반으로 Grade 열거형 객체를 반환하는 메서드
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
