package com.example.demo.service;

import com.example.demo.dto.AccessDTO;
import com.example.demo.dto.Agency;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class InfluxService {

    private static final String DB_URL = "http://localhost:8086/";
    private final String DB_NAME = "boot";
    private final String DB_USERANME = "admin";
    private static final String DB_PASSWORD = "secret";

    private InfluxDB db;

    @PostConstruct
    private void tryToConnecting() {
        try{
            db = InfluxDBFactory.connect(DB_URL, DB_USERANME, DB_PASSWORD);
            System.out.println(db.ping());
            db.ping();
            createDB();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void createDB() {
        Query query = new Query("CREATE DATABASE " + DB_NAME);
        QueryResult ask = db.query(query);  //List + Map형태의 객체(CRUD 전부)
        db.setDatabase(DB_NAME);
    }

    public void write(AccessDTO dto) {
        // Write points to InfluxDB.
        Agency data = Arrays.stream(Agency.values())
                .filter(op -> op.getKey().equalsIgnoreCase(dto.getOp()))
                .findFirst()
                .orElse(Agency.ETC);

        db.write(Point.measurement("tbl_day")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("test","test")
                .addField("os",dto.getOs())
                .addField("op",dto.getOp())
                .build());
    }

    public List<AccessDTO> get(String date) {
        String firstDay="";
        String lastDay="";
        LocalDate today = LocalDate.now();
        if(date.equals("today")) {
            firstDay = today.atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            lastDay = today.atTime(23,59,59).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }else if(date.equals("month")) {
            firstDay = today.withDayOfMonth(1).toString();
            lastDay = today.withDayOfMonth(today.lengthOfMonth()).toString();
        }else if(date.equals("year")){
            firstDay = today.with(TemporalAdjusters.firstDayOfYear()).toString();
            lastDay = today.with(TemporalAdjusters.lastDayOfYear()).toString();
        }else {
            throw new InvalidParameterException("잘못된 요청입니다. parameter의 값은 항상 today,month,year 이여야 합니다.");
        }

        QueryResult queryResult = db.query(new Query("SELECT * FROM tbl_day where time >= '"+firstDay+"' and time <= '"+lastDay+"'"));

        List<AccessDTO> data = queryResult.getResults().get(0).getSeries().get(0).getValues().stream().map(s-> {
            AccessDTO access = AccessDTO.builder()
                    .time(s.get(0).toString())
                    .op(s.get(1).toString())
                    .os(s.get(2).toString())
                    .build();
            return access;
        }).collect(Collectors.toList());

        return data;
    }


    public void close() {
        db.close();
    }
}
