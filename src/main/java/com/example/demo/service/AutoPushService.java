package com.example.demo.service;

import com.example.demo.dto.AccessDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
public class AutoPushService {

    private final InfluxService influxService;

    /**
     * 5초마다 데이터를 influxDB에 추가하게 설정한다.
     */
    @Scheduled(cron = "*/5 * * * * *")
    public void task() {
        System.out.println("데이터가 자동적으로 추가 되었습니다.");
        AccessDTO dto = AccessDTO.builder()
                .os("android")
                .op("45006")
                .build();

        log.info("자동으로 데이터가 추가되었습니다.");
        influxService.write(dto);

    }


}
