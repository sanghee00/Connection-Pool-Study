package com.connection.pool.springconnectionpool.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConnectionPoolService {

    @Transactional
    public void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            System.out.println("스레드 에러");
            throw new RuntimeException(e);
        }
    }

}
