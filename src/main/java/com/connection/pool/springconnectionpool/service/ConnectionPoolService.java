package com.connection.pool.springconnectionpool.service;

import org.springframework.stereotype.Service;

@Service
public class ConnectionPoolService {

    private final ConnectionTxService connectionTxService;

    public ConnectionPoolService(ConnectionTxService connectionTxService) {
        this.connectionTxService = connectionTxService;
    }

    public void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException(e);
        }
        connectionTxService.save();
    }

}
