package com.motok.motoK.service;

import com.motok.motoK.domain.vo.DailyAccountVO;

import java.util.List;

public interface Account {
    public String 일일입력(DailyAccountVO vo);
    public void 통계계산();
}
