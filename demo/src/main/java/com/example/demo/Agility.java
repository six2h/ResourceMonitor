package com.example.demo;

public class Agility {
    double CpuTemperature;
    double CpuVoltage;
    int[] FanSpeeds;
    public Agility(double cpuTemperature, double cpuVoltage, int[] fanSpeeds){
        CpuTemperature = cpuTemperature;
        CpuVoltage = cpuVoltage;
        FanSpeeds = fanSpeeds;
    }
    public double getCpuTemperature(){
        return CpuTemperature;
    }
    public double getCpuVoltage(){
        return CpuVoltage;
    }
    public int[] getFanSpeeds(){
        return FanSpeeds;
    }
}
