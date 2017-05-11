package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.model.Module;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.Date;
import java.util.List;

/**
 * Created by liuruijie on 2017/4/6.
 */
public interface ModuleMapper {
    String tableName = "sys_module";

    @Select("select * from "+tableName+" where id=#{id}")
    Module findOne(@Param("id") Integer id);

    @InsertProvider(type = ModuleSqlProvider.class, method = "insertSelective")
    int insertOne(@Param("module") Module module);

    @UpdateProvider(type = ModuleSqlProvider.class, method = "updateSelective")
    int updateOne(@Param("module") Module module);

    @Delete("delete from "+tableName+" where id=#{id}")
    int deleteOne(@Param("id") Integer id);

    @Select("select id from "+tableName+" where module_key=#{key}")
    Integer getIdByKey(@Param("key") String moduleKey);

    @SelectProvider(type = ModuleSqlProvider.class, method = "findAll")
    List<Module> findAll(@Param("page") PageRequest pageRequest);

    @Select("select count(id) from sys_module")
    int countAll();

    class ModuleSqlProvider extends PageSqlProvider{

        public String insertSelective(@Param("module")Module module){
            return new SQL(){
                {
                    INSERT_INTO(tableName);
                    if(module.getModuleKey()!=null){
                        VALUES("module_key", "#{module.moduleKey}");
                    }
                    if(module.getModuleName()!=null){
                        VALUES("module_name", "#{module.moduleName}");
                    }
                    if(module.getSort()!=null){
                        VALUES("sort", "#{module.sort}");
                    }
                    if(module.getUrl()!=null){
                        VALUES("url","#{module.url}");
                    }
                    if(module.getCreateDate()!=null){
                        module.setCreateDate(new Date());
                        VALUES("create_date", "#{module.createDate}");
                    }
//                    if(module.getCreatePassport()!=null){
//                        VALUES("create_passport", "#{module.createPassport}");
//                    }
                }
            }.toString();
        }

        public String updateSelective(@Param("module")Module module){
            return new SQL(){
                {
                    UPDATE(tableName);
                    if(module.getModuleKey()!=null){
                        SET("module_key=#{module.moduleKey}");
                    }
                    if(module.getModuleName()!=null){
                        SET("module_name = #{module.moduleName}");
                    }
                    if(module.getSort()!=null){
                        SET("sort = #{module.sort}");
                    }
                    if(module.getUrl()!=null){
                        SET("url = {module.url}");
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
