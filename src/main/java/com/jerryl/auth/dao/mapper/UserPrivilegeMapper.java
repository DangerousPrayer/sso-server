package com.jerryl.auth.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface UserPrivilegeMapper {

    @Insert("insert into sys_passport_role (passport_id,role_id)" +
            " values (#{passportId},#{roleId})")
    int bindPassportRole(@Param("passportId") String passportId
            , @Param("roleId") Integer roleId);

    @Delete("delete from sys_passport_role where passport_id = #{passportId} and role_id = #{roleId}")
    int unbindPassportRole(@Param("passportId") String passportId
            , @Param("roleId") Integer roleId);

    @Delete("delete from sys_passport_role where passport_id = #{passportId}")
    int unbindRoleByPassportId(@Param("passportId") String passportId);

    @InsertProvider(type = UserPrivilegeSqlProvider.class, method = "insertPassportRoleBatch")
    int bindRoleBatch(@Param("passportId") String passportId
            , @Param("roleIds") List<Integer> roleIds);

    class UserPrivilegeSqlProvider{
        public String insertPassportRoleBatch(@Param("passportId") String passportId
                , @Param("roleIds")List<Integer> roleIds){
            StringBuilder sb = new StringBuilder("insert into sys_passport_role")
                    .append( "(passport_id, role_id)")
                    .append(" values ");
            for(int i = 0;i<roleIds.size();i++){
                sb.append("(#{passportId}, #{roleIds[")
                        .append(i)
                        .append("]}),");
            }
            sb.deleteCharAt(sb.length()-1);

            return sb.toString();
        }
    }
}
