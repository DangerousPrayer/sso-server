package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.ModuleRegister;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

/**
 * Created by liuruijie on 2017/5/11.
 */
public interface ModuleRegisterMapper extends PageMapper<ModuleRegister>{
    String tableName = "sso_module_register";

    @SelectProvider(type = ModuleRegisterSqlProvider.class, method = "findAll")
    List<ModuleRegister> findAll(@Param("page") PageRequest request);

    @SelectProvider(type = ModuleRegisterSqlProvider.class, method = "countAll")
    Long countAll();

    @Insert("insert into "+tableName+" (meta_data_url, host_id)" +
            " values(#{metaDataUrl}, #{hostId})")
    void insertOne(@Param("metaDataUrl") String metaDataUrl
            , @Param("hostId") Integer hostId);

    @UpdateProvider(type = ModuleRegisterSqlProvider.class, method = "updateSelective")
    void updateOne(@Param("register")ModuleRegister register);

    @Select("select host_id from "+ tableName+" where host_id=#{hostId} and status=1 group by host_id")
    Integer checkCompleted(@Param("hostId") Integer hostId);

    class ModuleRegisterSqlProvider extends PageSqlProvider{
        @Override
        protected SQL preSql() {
            return new SQL().SELECT(tableName);
        }

        public String updateSelective(@Param("register")ModuleRegister register){
            return new SQL(){
                {
                    UPDATE(tableName);
                    if(register.getHostId()!=null){
                        SET("host_id=#{register.hostId}");
                    }
                    if(register.getMetaDataUrl()!=null){
                        SET("meta_data_url=#{register.metaDataUrl}");
                    }
                    if(register.getStatus()!=null){
                        SET("status=#{register.status}");
                    }
                }
            }.toString();
        }
    }
}
