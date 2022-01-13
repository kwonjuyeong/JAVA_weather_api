package com.example.final_exam1;

public class WeatherData {
    private double tmp;
    private double skycode;
    private double reh;

    public WeatherData(double tmp, double skycode, double reh) {

    }

    public void setTmp(double tmp) {
        this.tmp = tmp;
    }

    public void setSkycode(double skycode) {
        this.skycode = skycode;
    }

    public void setReh(double reh) {
        this.reh = reh;
    }

    public double getTmp() {
        return tmp;
    }

    public double getSkycode() {
        return skycode;
    }

    public double getReh() {
        return reh;
    }


    @Override
    public String toString() {
        return "WeatherData{" +
                "tmp=" + tmp +
                ", skycode=" + skycode +
                ", reh=" + reh +
                '}';
    }
}
