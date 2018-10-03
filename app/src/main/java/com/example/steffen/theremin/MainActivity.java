package com.example.steffen.theremin;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private int maxValue;

    int limitMax = 0;
    int limitMin = 0;
    int toleranceRange;
    boolean isPlayTone = false;


    TextView tone;
    TextView calibrationText;

    int currentScaleIndex = 7;

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
        calibrationText = (TextView) findViewById(R.id.calibration);

        lightEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                int currentValue = (int) sensorEvent.values[0];
                getSupportActionBar().setTitle("Luminosity : " + currentValue);
                if(!lightSensorHelper.isCalibrated()) {
                    if(countCalibrator > 0 && previousValue != currentValue){
                        valuesForCalibration[countCalibrator-1] = currentValue;
                        countCalibrator--;
                    } else if(countCalibrator <= 0) {

                        lightSensorHelper.updateCalibrationStatus(true);
                        maxValue = lightSensorHelper.calculateMaxValue(valuesForCalibration);
                        toleranceRange = (int) maxValue / toneGeneratorHelper.getScale().length;
                        limitMax = maxValue;
                        limitMin = maxValue - toleranceRange;
                        btPlay.setVisibility(View.VISIBLE);
                        calibrationText.setText("press and hold the Playbutton");
                    }
                    previousValue = currentValue;

                } else if (lightSensorHelper.isCalibrated() && isPlayTone) {
                    int toleranceRange = (int) maxValue / toneGeneratorHelper.getScale().length;

                    if (limitMax < currentValue && currentScaleIndex < toneGeneratorHelper.getScale().length-1 ) {
                        limitMin = limitMax;
                        limitMax = limitMax + toleranceRange;
                        if(limitMax > maxValue) {
                            limitMax = maxValue;
                        } else {
                            currentScaleIndex++;
                        }

                        toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                        showTone();

                    } else if (limitMin > currentValue && currentScaleIndex > 0) {
                        limitMax = limitMin;
                        limitMin = limitMin - toleranceRange;

                        if(limitMin < 0) {
                            limitMin = 0;
                        } else {
                            currentScaleIndex--;
                        }

                        toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                        showTone();
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

                toneGeneratorHelper.setScale((String[][]) toneGeneratorHelper.getScaleMap().get(spinnerItem));
                currentScaleIndex = 7;
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
                            calibrationText.setVisibility(View.GONE);
                            toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                            toneGenerator.sampleRate = toneGeneratorHelper.getSampleRate();
                            toneGenerator.play();
                            isPlayTone = true;
                            showTone();

                            break;
                        }

                        case MotionEvent.ACTION_UP: {
                            isPlayTone = false;
                            calibrationText.setVisibility(View.VISIBLE);
                            toneGenerator.stop();
                            tone.setVisibility(View.GONE);
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
