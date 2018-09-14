package com.example.steffen.theremin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageButton btPlay;

    private SensorManager sensorManager;
    Sensor lightSensor;
    SensorEventListener lightEventListener;
    private LightSensorHelper lightSensorHelper = new LightSensorHelper();

    private int previousValue;

    private int countCalibrator = 5;
    private int[] valuesForCalibration = new int[countCalibrator];
    //private int averageCalibrationValue;
    private int maxValue;


    TextView tone;

    int currentScaleIndex = 0;

    ToneGeneratorHelper toneGeneratorHelper = new ToneGeneratorHelper();
    com.example.steffen.theremin.ToneGenerator toneGenerator = new com.example.steffen.theremin.ToneGenerator();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btPlay = (ImageButton) findViewById(R.id.playButton);
        tone = (TextView) findViewById(R.id.currentTone);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                int currentValue = (int) sensorEvent.values[0];

                if(!lightSensorHelper.isCalibrated()) {
                    if(countCalibrator > 0 && previousValue != currentValue){
                        Log.i("Calibrator", "onSensorChanged: not calibrated ("+countCalibrator+" left)");
                        valuesForCalibration[countCalibrator-1] = currentValue;
                        countCalibrator--;
                    } else if(countCalibrator <= 0) {
                        Log.i("Calibrator", "onSensorChanged: is calibrated !!!");
                        lightSensorHelper.updateCalibrationStatus(true);
                    }
                    previousValue = currentValue;

                } else if (lightSensorHelper.isCalibrated()) {
                    btPlay.setVisibility(View.VISIBLE);
                    //averageCalibrationValue = lightSensorHelper.calculateAverageValue(valuesForCalibration);
                    maxValue = lightSensorHelper.calculateMaxValue(valuesForCalibration);
                    int toleranceRange = (int) maxValue / toneGeneratorHelper.getScale().length;
                    //Log.i("UP/DOWN", "Toleranz" +toleranceRange);
                    //Log.i("UP/DOWN", "MaxValue" + maxValue);
                    Log.i("ScaleIndex", "ScaleIndex" + currentScaleIndex);

                    if (maxValue - toleranceRange > currentValue && currentScaleIndex < toneGeneratorHelper.getScale().length-1) {
                        Log.i("UP", "UP/DOWN: UP");
                        if(maxValue -toleranceRange <0){
                            lightSensorHelper.updateMaxCalibrationValue(lightSensorHelper.calculateMaxValue(valuesForCalibration),"max");
                        } else {
                            lightSensorHelper.updateMaxCalibrationValue(toleranceRange,"up");
                        }

                        showTone();
                        currentScaleIndex++;
                        //toneGenerator.stop();
                        toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                        //toneGenerator.play();

                    } else if (maxValue + toleranceRange > currentValue && currentScaleIndex > 0) {
                        Log.i("DOWN", "UP/DOWN: DOWN");
                        if(maxValue - toleranceRange < 0){
                            lightSensorHelper.updateMaxCalibrationValue(0,"zero");
                        } else {
                            lightSensorHelper.updateMaxCalibrationValue(toleranceRange,"down");
                        }

                        showTone();
                        currentScaleIndex--;
                        //toneGenerator.stop();
                        toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                        //toneGenerator.play();

                    }
                    previousValue = currentValue;
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        Spinner keys = (Spinner) findViewById(R.id.keysSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.keys_array, android.R.layout.simple_spinner_dropdown_item);
        keys.setAdapter(adapter);
        keys.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spinnerItem=parent.getItemAtPosition(position).toString();
                Log.i("Spinner", "onItemSelected: " + spinnerItem);

                toneGeneratorHelper.setScale((String[][]) toneGeneratorHelper.getScaleMap().get(spinnerItem));
                currentScaleIndex = 0;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btPlay.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(lightSensorHelper.isCalibrated()) {

                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {

                            toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                            toneGenerator.sampleRate = toneGeneratorHelper.getSampleRate();
                            toneGenerator.play();
                            showTone();
                            Log.i("Key", "Name: " + toneGeneratorHelper.getScale()[currentScaleIndex][1] + " Frequenz: " + toneGeneratorHelper.getScale()[currentScaleIndex][0]);

                            break;
                        }

                        case MotionEvent.ACTION_UP: {

                            toneGenerator.stop();

                            break;
                        }
                    }
                }
                    return false;

            }

        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(lightEventListener, lightSensor, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(lightEventListener);
    }

    public void showTone(){
        tone.setVisibility(View.VISIBLE);
        tone.setText(toneGeneratorHelper.getScale()[currentScaleIndex][1]);
    }
}
