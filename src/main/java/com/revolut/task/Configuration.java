package com.revolut.task;

import com.beust.jcommander.Parameter;

public class Configuration {
    @Parameter(names = "-port", description = "Server port", required = false)
    private int port = 8080;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
