package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageMapper;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.AuthHost;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;
import java.util.Set;

/**
 * Created by liuruijie on 2017/5/11.
 */
public interface AuthHostMapper extends PageMapper<AuthHost>{
    String tableName = "sso_auth_host";

    @SelectProvider(type = AuthHostSqlProvider.class, method = "findAll")
    List<AuthHost> findAll(@Param("page") PageRequest request);

    @SelectProvider(type = AuthHostSqlProvider.class, method = "countAll")
    Long countAll();

    @Insert("insert into "+tableName+" (host_auth_key, host_ip) values(#{host.hostAuthKey}, #{host.hostIp})")
    @SelectKey(statement="SELECT LAST_INSERT_ID() AS host_id", keyProperty="host.hostId", before=false, resultType=int.class)
    void insertOne(@Param("host")AuthHost host);

    @UpdateProvider(type = AuthHostSqlProvider.class, method = "updateSelective")
    void updateOne(@Param("host")AuthHost host);

    @Select("select * from "+tableName+" where host_auth_key=#{key}")
    AuthHost selectByAuthKey(@Param("key")String authKey);

    @Select("select host_ip from "+tableName)
    Set<String> selectHostIps();

    class AuthHostSqlProvider extends PageSqlProvider{
        @Override
        protected SQL preSql() {
            return new SQL().FROM(tableName);
        }

        public String updateSelective(@Param("host")AuthHost host){
            return new SQL(){
                {
                    UPDATE(tableName);
                    if(host.getHostIp()!=null){
                        SET("host_ip=#{host.hostIp}");
                    }
                    if(host.getHostAuthKey()!=null){
                        SET("host_auth_key=#{host.hostAuthKey}");
                    }
                    if(host.getAuthTime()!=null){
                        SET("auth_time=#{host.authTime}");
                    }
                    if(host.getStatus()!=null){
                        SET("status=#{host.status}");
                    }
                }
            }.toString();
        }
    }
}
