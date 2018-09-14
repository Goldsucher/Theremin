package com.example.steffen.theremin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btPlay;
    ImageButton btPause;
    Button btPlus;
    Button btMinus;

    TextView tone;

    int currentScaleIndex = 0;

    ToneGeneratorHelper toneGeneratorHelper = new ToneGeneratorHelper();
    com.example.steffen.theremin.ToneGenerator toneGenerator = new com.example.steffen.theremin.ToneGenerator();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btPlay = (ImageButton) findViewById(R.id.playButton);
        btPause = (ImageButton) findViewById(R.id.pauseButton);
        btPlus = (Button) findViewById(R.id.plusButton);
        btMinus = (Button) findViewById(R.id.minusButton);
        tone = (TextView) findViewById(R.id.currentTone);

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

                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:{

                        btPlay.setVisibility(View.GONE);
                        btPause.setVisibility(View.VISIBLE);
                        toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                        toneGenerator.sampleRate = toneGeneratorHelper.getSampleRate();
                        toneGenerator.play();
                        showTone();
                        Log.i("Key", "Name: " + toneGeneratorHelper.getScale()[currentScaleIndex][1] + " Frequenz: " + toneGeneratorHelper.getScale()[currentScaleIndex][0]);

                        break;
                    }

                    // Wenn Button losgelassen
                    case MotionEvent.ACTION_UP:{
                        btPause.setVisibility(View.GONE);
                        btPlay.setVisibility(View.VISIBLE);
                        toneGenerator.stop();

                        break;
                    }
                }
                return false;
            }

        });

        btPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if( currentScaleIndex < toneGeneratorHelper.getScale().length - 1){
                    currentScaleIndex++;
                    toneGenerator.stop();
                    toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                    toneGenerator.play();
                    showTone();
                }
            }
        });


        btMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if( currentScaleIndex > 0){
                    currentScaleIndex--;
                    toneGenerator.stop();
                    toneGenerator.m_ifreq = Integer.parseInt(toneGeneratorHelper.getScale()[currentScaleIndex][0]);
                    toneGenerator.play();
                    showTone();
                }
            }
        });
    }

    public void showTone(){
        tone.setVisibility(View.VISIBLE);
        tone.setText(toneGeneratorHelper.getScale()[currentScaleIndex][1]);
    }
}
