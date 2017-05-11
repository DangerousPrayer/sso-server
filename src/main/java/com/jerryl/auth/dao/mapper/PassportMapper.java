package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.Passport;
import com.jerryl.auth.service.dto.SessionUser;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface PassportMapper {
    String tableName = "sys_passport";

    @Select("select * from "+tableName+" where id = #{id}")
    Passport findOne(String id);

    @Select("select id from "+tableName+" where id = #{id}")
    String checkExist(String id);

    @InsertProvider(type = PassportSqlProvider.class, method = "insertSelective")
    int insertOne(@Param("passport") Passport passport);

    @UpdateProvider(type = PassportSqlProvider.class, method = "updateSelective")
    int updateOne(@Param("passport") Passport passport);

    @Delete("delete from "+ tableName+" where id=#{id}")
    int delete(@Param("id") String id);

    @SelectProvider(type = PassportSqlProvider.class, method = "findAll")
    List<Passport> findAll(@Param("page") PageRequest pageRequest);

    @Select("select B.id,B.passport_id,B.nick_name from sys_passport A,sys_user B where A.id=B.passport_id " +
            "and A.id=#{passportId} and A.password=#{password} " +
            "and A.status=1")
    SessionUser findForLogin(@Param("passportId") String passportId, @Param("password") String password);

    class PassportSqlProvider extends PageSqlProvider{
        public String insertSelective(@Param("passport")Passport passport){
            return new SQL(){{
                INSERT_INTO(tableName);
                VALUES("id","#{passport.id}");
                VALUES("password", "#{passport.password}");
                if(passport.getStatus()!=null){
                    VALUES("status", "#{passport.status}");
                }
            }}.toString();
        }

        public String updateSelective(Passport passport){
            return new SQL(){
                {
                    UPDATE(tableName);
                    if(passport.getId()!=null){
                        SET("id = #{passport.id}");
                    }
                    if(passport.getPassword()!=null){
                        SET("password = #{passport.password}");
                    }
                    if(passport.getStatus()!=null){
                        SET("status = #{passport.status}");
                    }
                }
            }.toString();
        }

        @Override
        protected SQL preSql() {
            return new SQL().FROM(tableName);
        }
    }
}
