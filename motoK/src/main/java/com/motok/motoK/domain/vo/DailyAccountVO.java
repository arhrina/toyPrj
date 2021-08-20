package com.motok.motoK.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DailyAccountVO {
    private LocalDateTime createdDate;
    private Long 매출;
    private Long 지출;
}
