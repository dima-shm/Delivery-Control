package com.shm.dim.delcontrol.asyncTask;

public interface RestRequestDelegate {
    void executionFinished(int responseCode, String responseBody);
}