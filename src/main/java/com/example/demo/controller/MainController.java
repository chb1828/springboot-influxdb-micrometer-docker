package com.example.demo.controller;
import com.example.demo.dto.AccessDTO;
import com.example.demo.service.InfluxService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MainController {

	private final InfluxService influxService;

	/**
	 * localhost:8080 으로 접속했을때 influxDB에 IOS 데이터가 추가된다.
	 * @return
	 */
	@GetMapping("/")
	public String index() {

		AccessDTO dto = AccessDTO.builder()
				.os("ios")
				.op("45004")
				.build();
		influxService.write(dto);
		log.warn("수동으로 데이터가 추가 되었습니다.");
		return "데이터가 추가되었습니다.";
	}



}
