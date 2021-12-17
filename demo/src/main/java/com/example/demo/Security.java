package com.example.demo;

public class Security {
    int NetworkIFaces;
    int ProcessCount;

    public Security(int size, int processCount) {
        NetworkIFaces = size;
        ProcessCount = processCount;
    }
    public int getNetworkIFaces(){
        return NetworkIFaces;
    }
    public int getProcessCount(){
        return ProcessCount;
    }
}
