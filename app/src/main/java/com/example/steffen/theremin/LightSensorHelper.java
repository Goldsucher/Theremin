package com.example.steffen.theremin;

public class LightSensorHelper {

    private boolean calibrationStatus;
    private int minValue;
    private int maxValue;
    private int avarageValue;

    @SuppressWarnings("WeakerAccess")
    public LightSensorHelper(){
        maxValue = 0;
        calibrationStatus = false;
    }

    private boolean getCalibrationStatus() {

        return calibrationStatus;
    }

    private void setCalibrationStatus(boolean calibrationStatus) {
        this.calibrationStatus = calibrationStatus;
    }

    public int getMaxValue() {

        return maxValue;
    }

    private void setMaxValue(int maxValue) {

        this.maxValue = maxValue;
    }

    public boolean isCalibrated (){

        return getCalibrationStatus();
    }

    public void updateCalibrationStatus(boolean calibrationStatus){
        setCalibrationStatus(calibrationStatus);
    }

    public int calculateMaxValue(int[] values) {

        setMaxValue(values[0]);

        for (int v:values) {
            if(v > getMaxValue()) {
                setMaxValue(v);
            }
        }

        return getMaxValue();
    }
}

