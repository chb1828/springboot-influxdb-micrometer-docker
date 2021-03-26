package com.example.demo.controller;
import com.example.demo.dto.TblStatDaysDTO;
import com.example.demo.service.InfluxService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

	private final InfluxService influxService;

	/**
	 * localhost:8080 으로 접속했을때 influxDB에 IOS 데이터가 추가된다.
	 * @return
	 */
	@GetMapping("/")
	public String index() {

		TblStatDaysDTO dto = TblStatDaysDTO.builder()
				.os("ios")
				.op("45004")
				.build();
		influxService.write(dto);
		influxService.print();
		return "데이터가 추가되었습니다.";
	}



}
