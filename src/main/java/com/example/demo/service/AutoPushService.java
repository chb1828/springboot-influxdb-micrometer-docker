package com.example.demo.service;

import com.example.demo.dto.TblStatDaysDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AutoPushService {

    private final InfluxService influxService;

    /**
     * 5초마다 데이터를 influxDB에 추가하게 설정한다.
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void task() {
        System.out.println("데이터가 자동적으로 추가 되었습니다.");
        TblStatDaysDTO dto = TblStatDaysDTO.builder()
                .os("android")
                .op("45002")
                .build();

        influxService.write(dto);

    }


}
