package com.jerryl.auth.dao.mapper;

import com.jerryl.auth.dao.PageRequest;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

/**
 * Created by liuruijie on 2017/4/6.
 * 提供默认的分页列表查询
 */
public abstract class PageSqlProvider {
    protected abstract SQL preSql();

    //默认的分页列表查询
    public String findAll(@Param("page") PageRequest request){
        return findByCase(request, preSql().SELECT("*"));
    }

    //默认的计数查询
    public String countAll(){
        return countByCase(preSql());
    }

    //用于拼接条件的分页列表查询，在子类中设置条件，sql为已拼接了条件的SQL对象。
    protected String findByCase(@Param("page") PageRequest request, SQL sql){
        if(request.getSorts()!=null&&request.getSorts().length!=0){
//            sb.append(" order by");
//            for(PageRequest.Sort sort: request.getSorts()){
//                sb.append(" ").append(sort.getField()).append(" ")
//                        .append(sort.getType()).append(",");
//            }
//            sb.deleteCharAt(sb.length()-1);
            for(int i=0;i<request.getSorts().length;i++){
                PageRequest.Sort sort = request.getSorts()[i];
                sql.ORDER_BY(sort.getField()+" "+sort.getType());
            }
        }
        String preSql = sql.toString();
        StringBuilder sb = new StringBuilder(preSql);
        sb.append(" limit #{page.start},#{page.size}");

        return sb.toString();
    }

    //用于拼接条件的计数查询，在子类中设置条件，sql为已拼接了条件的SQL对象。
    protected String countByCase(SQL sql){
        return sql.SELECT("count(*)").toString();
    }
}
