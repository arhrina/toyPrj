package com.anymobi.ulsan.controller;

import com.anymobi.ulsan.config.MapperConfig;
import com.anymobi.ulsan.service.TestService;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private TestService testService;

    @PostConstruct
    @RequestMapping(value = "/")
    public String test() {
        return "index";
    }

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testSelect() {
        List<String> testResult = testService.selectAll();
        String t;
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : testResult) {
            stringBuilder.append(s);
        }
        t = stringBuilder.toString();
        return t;
    }
}
