package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.model.Action;
import com.jerryl.auth.dao.model.RolePrivileges;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface ActionMapper {
    @Select("select actions from sys_role_actions where module_id=#{moduleId}")
    List<String> findActionsByModule(@Param("moduleId") Integer moduleId);

    @Select("select role_id,module_id,actions from sys_role_actions where role_id=#{roleId}")
    List<RolePrivileges> findActionsByRole(@Param("roleId") Integer roleId);

    @Insert("insert into sys_role_actions (role_id,module_id,actions) values (#{roleId},#{moduleId},#{actions})")
    int bindRoleActions(@Param("roleId") Integer roleId
            , @Param("moduleId") Integer moduleId
            , @Param("actions") String actionIds);
    @Update("update sys_role_actions set actions=#{actions} where role_id=#{roleId} and module_id=#{moduleId}")
    int updateRoleActions(@Param("roleId") Integer roleId
            , @Param("moduleId") Integer moduleId
            , @Param("actions") String actions);
    @Delete("delete from sys_role_actions where role_id=#{roleId} and module_id=#{moduleId}")
    int unbindAllByRole(@Param("roleId") Integer roleId, @Param("moduleId") Integer moduleId);

    @SelectProvider(type = ActionSqlProvider.class, method = "findByRoles")
    List<RolePrivileges> findActionsByModuleRoles(@Param("moduleId") Integer moduleId, @Param("roleIds") List<Integer> roleIds);

    @Select("select * from sys_action")
    List<Action> findAll();

    class ActionSqlProvider{
        public String findByRoles(@Param("moduleId")Integer moduleId,@Param("roleIds")List<Integer> roleIds){

            return new SQL(){
                {
                    SELECT("*").FROM("sys_role_actions");
                    WHERE("module_id = #{moduleId}").AND();
                    for(int i = 0;i<roleIds.size();i++){
                        WHERE("role_id=#{roleIds["+i+"]}");
                        if(i<roleIds.size()-1){
                            OR();
                        }
                    }
                }
            }.toString();
        }
    }
}
