package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface RoleMapper {
    String tableName = "sys_role";

    @Select("select * from " +tableName+ " where id = #{id}")
    Role findOne(@Param("id") Integer id);

    @Select("select role_id from sys_passport_role where passport_id = #{passportId}")
    List<Integer> findIdsByPassport(@Param("passportId") String passportId);

    @SelectProvider(type = RoleSqlProvider.class, method = "findAll")
    List<Role> findAll(@Param("page") PageRequest pageRequest);

    @Select("select count(id) from "+tableName)
    int countAll();

    class RoleSqlProvider extends PageSqlProvider{
        @Override
        protected SQL preSql() {
            return new SQL().FROM(tableName);
        }
    }
}
