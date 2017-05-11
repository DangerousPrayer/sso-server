package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface UserMapper extends PageMapper<UserInfo>{
    String tableName = "sys_user";

    @Select("SELECT * FROM "+tableName+" where id=#{id}")
    UserInfo findOne(@Param("id") Long id);

    @InsertProvider(type = UserSqlProvider.class, method = "insertSelective")
    @SelectKey(statement="SELECT LAST_INSERT_ID() AS id", keyProperty="user.id", before=false, resultType=long.class)
    int insertOne(@Param("user") UserInfo userInfo);

    @UpdateProvider(type = UserSqlProvider.class, method = "updateSelective")
    int updateOne(@Param("user") UserInfo userInfo);

    @Delete("delete from "+tableName+" where id=#{id}")
    int deleteOne(@Param("id") Long id);

    @Delete("delete from "+tableName+" where passport_id=#{id}")
    int deleteByPassportId(@Param("id") String passportId);

    @SelectProvider(type = UserSqlProvider.class, method = "findAll")
    List<UserInfo> findAll(@Param("page") PageRequest pageRequest);

    @SelectProvider(type = UserSqlProvider.class, method = "countAll")
    Long countAll();

    @SelectProvider(type = UserSqlProvider.class, method = "findByNickName")
    List<UserInfo> findByNickName(@Param("page") PageRequest pageRequest
            , @Param("nickName") String nickName);

    @SelectProvider(type = UserSqlProvider.class, method = "countByNickName")
    long countByNickName(@Param("nickName") String nickName);

    class UserSqlProvider extends PageSqlProvider{
        public String updateSelective(@Param("user") UserInfo userInfo){
            return new SQL(){{
                UPDATE(tableName);
                if(userInfo.getNickName()!=null){
                    SET("nick_name = #{user.nickName}");
                }
                if(userInfo.getEmail()!=null){
                    SET("email = #{user.email}");
                }
                if(userInfo.getPhone()!=null){
                    SET("phone = #{user.phone}");
                }
            }}.toString();
        }

        public String insertSelective(@Param("user") UserInfo userInfo){
            return new SQL(){{
                INSERT_INTO(tableName);
                if(userInfo.getNickName()!=null){
                    VALUES("nick_name","#{user.nickName}");
                }
                if(userInfo.getEmail()!=null){
                    VALUES("email","#{user.email}");
                }
                if(userInfo.getPhone()!=null){
                    VALUES("phone","#{user.phone}");
                }
                if (userInfo.getPassportId()!=null){
                    VALUES("passport_id","#{user.passportId}");
                }
            }}.toString();
        }


        @Override
        protected SQL preSql() {
            return new SQL().FROM(tableName);
        }

        public String findByNickName(@Param("page") PageRequest pageRequest
                , @Param("nickName") String nickName){
            return findByCase(pageRequest
                    , preSql().SELECT("*")
                            .WHERE("nick_name = #{nickName}"));
        }

        public String countByNickName(@Param("nickName") String nickName){
            return countByCase(preSql()
                    .WHERE("nick_name = #{nickName}"));
        }
    }
}
