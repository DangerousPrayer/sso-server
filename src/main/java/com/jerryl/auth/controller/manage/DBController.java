package com.jerryl.auth.controller.manage;

import com.jerryl.auth.Config;
import com.jerryl.auth.common.ToWeb;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by liuruijie on 2017/4/12.
 */
@RestController
@RequestMapping("db")
public class DBController {

    @Autowired
    DataSource dataSource;

    @PostMapping("init")
    public Object initDB(String key) throws SQLException, FileNotFoundException {
        if(Config.DB_INIT_KEY.equals(key)){
            Connection connection = dataSource.getConnection();
            ScriptRunner runner = null;
            try {
                runner = new ScriptRunner(connection);
                String path = DBController.class.getClassLoader().getResource("sql/boot.sql").getPath();
                runner.runScript(new FileReader(path));
            } finally {
                if (runner!=null) runner.closeConnection();
            }
            return ToWeb.buildResult().back();
        }else{
            return ToWeb.buildResult().msg("口令错误!").refresh();
        }
    }
}
