package com.jerryl;

import com.alibaba.fastjson.JSON;
import com.jerryl.auth.dao.Page;
import com.jerryl.auth.dao.PageRequest;
import com.jerryl.auth.dao.mapper.UserMapper;
import com.jerryl.auth.dao.model.UserInfo;
import com.jerryl.auth.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SsoApplicationTests {

	@Autowired
	UserService userService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void pageTest(){
	    //构建pageRequest对象，设置页码page和每页的记录数size。
		PageRequest request = new PageRequest(1, 2);
		//设置排序规则
        request.setSorts(
                new PageRequest.Sort[]{
                        new PageRequest.Sort("id","DESC")});
        //得到page对象
        Page<UserInfo> userInfoPage = userService.selectPage(request);

        //序列化后输出
		String json = JSON.toJSONString(userInfoPage);
		System.out.println(json);
	}

	@Test
    public void pageByConditionTest(){
        PageRequest request = new PageRequest(1, 2);
        request.setSorts(
                new PageRequest.Sort[]{
                        new PageRequest.Sort("id","DESC")});
        Page<UserInfo> userInfoPage = userService.selectByNickName(request
                , "AAAAA");

        String json = JSON.toJSONString(userInfoPage);
        System.out.println(json);
    }
}
