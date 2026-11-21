package com.connection.pool.springconnectionpool.controller;

import com.connection.pool.springconnectionpool.service.ConnectionPoolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionPoolController {

    private final ConnectionPoolService connectionPoolService;

    public ConnectionPoolController(ConnectionPoolService connectionPoolService) {
        this.connectionPoolService = connectionPoolService;
    }

    @GetMapping("/api/sleep")
    public String sleep() {
        connectionPoolService.sleep();
        return "ok";
    }
}
