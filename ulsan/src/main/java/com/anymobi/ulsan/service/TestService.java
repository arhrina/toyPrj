package com.anymobi.ulsan.service;

import com.anymobi.ulsan.dao.TestDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestDao testDao;

    public List<String> selectAll() {
        return testDao.selectAll();
    }
}
