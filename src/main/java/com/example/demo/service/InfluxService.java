package com.example.demo.service;

import com.example.demo.dto.TblStatDaysDTO;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

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

    public void write(TblStatDaysDTO dto) {
        // Write points to InfluxDB.
        db.write(Point.measurement("tbl_day")
                .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .tag("test","test")
                .addField("os",dto.getOs())
                .addField("op",dto.getOp())
                .build());
    }

    public void print() {
        QueryResult queryResult = db.query(new Query("SELECT * FROM tbl_day"));
        System.out.println(queryResult.getResults().get(0));
    }

    public void close() {
        db.close();
    }
}
