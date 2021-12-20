package com.why.transportsecurity.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccidentMapperTest {

    @Autowired
    private AccidentMapper accidentMapper;

    @Test
    void getAccidentInfoById() {
        System.out.println(accidentMapper.getAccidentInfoById(2));
    }

    @Test
    void getAccidentInfoByVId() {
        System.out.println(accidentMapper.getAccidentInfoByVId(1));
    }

    @Test
    void getAllAccidentInfoSolve() {
    }

    @Test
    void getAllAccidentInfoUnSolve() {
    }

    @Test
    void updateState() {
    }

    @Test
    void deleteAccident() {
    }

    @Test
    void insertAccident() {
    }
}