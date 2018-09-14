package com.example.steffen.theremin;

import java.util.Objects;

public class LightSensorHelper {

    private boolean calibrationStatus;
    private int minValue;
    private int maxValue;
    private int avarageValue;

    @SuppressWarnings("WeakerAccess")
    public LightSensorHelper(){
        minValue = 0;
        maxValue = 0;
        avarageValue = 0;
        calibrationStatus = false;
    }

    private boolean getCalibrationStatus() {

        return calibrationStatus;
    }

    private void setCalibrationStatus(boolean calibrationStatus) {
        this.calibrationStatus = calibrationStatus;
    }

    private int getMinValue() {

        return minValue;
    }

    private void setMinValue(int minValue) {

        this.minValue = minValue;
    }

    public int getMaxValue() {

        return maxValue;
    }

    private void setMaxValue(int maxValue) {

        this.maxValue = maxValue;
    }

    public int getAvarageValue() {

        return avarageValue;
    }

    private void setAvarageValue(int avarageValue) {

        this.avarageValue = avarageValue;
    }

    public boolean isCalibrated (){

        return getCalibrationStatus();
    }

    public void updateCalibrationStatus(boolean calibrationStatus){
        setCalibrationStatus(calibrationStatus);
    }

    public int calculateMinValue(int[] values) {

        setMinValue(values[0]);

        for (int v:values) {
            if(v < getMinValue()) {
                setMinValue(v);
            }
        }

        return getMinValue();
    }

    public int calculateMaxValue(int[] values) {

        setMaxValue(values[0]);

        for (int v:values) {
            if(v < getMaxValue()) {
                setMaxValue(v);
            }
        }

        return getMaxValue();
    }

    public int calculateAverageValue(int[] values) {

        setAvarageValue(values[0]);

        for (int v:values) {
            if(v < getAvarageValue()) {
                setAvarageValue(v);
            }
        }

        return getAvarageValue();
    }

    public int updateMaxCalibrationValue(int value, String operation){

        if(Objects.equals(operation, "plus")){
            setMaxValue(getMaxValue()+value);

        } else if (Objects.equals(operation, "minus")) {
            setMaxValue(getMaxValue()-value);
        } else if (Objects.equals(operation, "zero")) {
            setMaxValue(0);
        } else if (Objects.equals(operation, "max")) {
            setMaxValue(value);
        }
        return getMaxValue();
    }
}

