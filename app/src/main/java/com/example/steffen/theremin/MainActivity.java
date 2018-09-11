package com.example.steffen.theremin;

import android.app.Activity;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.ToneGenerator;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageButton btPlay;
    ImageButton btPause;
    Button btPlus;
    Button btMinus;
    ToneGenerator toneGenerator;

    // aktuell gespielter Ton
    TextView tone;


    // Frequenzen der Töne
    String C = "262";
    String Cis = "277";
    String D = "294";
    String Dis = "311";
    String E = "330";
    String F = "349";
    String Fis = "370";
    String G = "392";
    String Gis = "415";
    String A = "440";
    String Ais = "466";
    String B = "494";
    String C2 = "523";
    String Cis2 = "554";
    String D2 = "587";
    String Dis2 = "622";
    String E2 = "659";
    String F2 = "698";
    String Fis2 = "740";
    String G2 = "784";
    String Gis2 = "831";
    String A2 = "880";
    String Ais2 = "932";
    String B2 = "988";


    //===TONLEITERNN===
/*    String blabla[] = {
            "C-Dur", "G-Dur", "D-Dur", "A-Dur", "E-Dur", "B-Dur", "F#-Dur", "F-Dur", "Bb-Dur", "Eb-Dur", "Ab-Dur", "Db-Dur", "Gb-Dur", "A-Moll", "E-Moll", "B-Moll", "F#-Moll", "C#-Moll", "G#-Moll", "D#-Moll", "D-Moll", "G-Moll", "C-Moll", "F-Moll", "Bb-Moll", "Eb-Moll"
    };*/

    // C-Dur
    String cDur[][] = {
            {C, "C"},
            {D, "D"},
            {E, "E"},
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C"}
    };

    // A-Moll
    String aMoll[][] = {
            {A, "A"},
            {B, "B"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"},
            {F2, "F"},
            {G2, "G"},
            {A2, "A"}
    };

    //#-Tonarten
    // G-Dur
    String gDur[][] = {
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"},
            {Fis2, "F#"},
            {G2, "G"}
    };

    // E-Moll
    String eMoll[][] = {
            {E, "E"},
            {Fis, "F#"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"}
    };

    // D-Dur
    String dDur[][] = {
            {D, "D"},
            {E, "E"},
            {Fis, "F#"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {Cis2, "C#"},
            {D2, "D"}
    };

    // B-Moll
    String bMoll[][] = {
            {B, "B"},
            {Cis2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {Fis2, "F#"},
            {G2, "G"},
            {A2, "A"},
            {B2, "B"}
    };

    // A-Dur
    String aDur[][] = {
            {A, "A"},
            {B, "B"},
            {Cis2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {Fis2, "F#"},
            {Gis2, "G#"},
            {A2, "A"}
    };

    // F#-Moll
    String fisMoll[][] = {
            {Fis, "F#"},
            {Gis, "G#"},
            {A, "A"},
            {B, "B"},
            {Cis2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {Fis2, "F#"}
    };

    // E-Dur
    String eDur[][] = {
            {E, "E"},
            {Fis, "F#"},
            {Gis, "G#"},
            {A, "A"},
            {B, "B"},
            {Cis2, "C#"},
            {Dis2, "D#"},
            {E2, "E"}
    };

    // C#-Moll
    String cisMoll[][] = {
            {Cis, "C#"},
            {Dis, "D#"},
            {E, "E"},
            {Fis, "F#"},
            {Gis, "G#"},
            {A, "A"},
            {B, "B"},
            {Cis2, "C#"}
    };

    // B-Dur
    String bDur[][] = {
            {B, "B"},
            {Cis2, "C#"},
            {Dis2, "D#"},
            {E2, "E"},
            {Fis2, "F#"},
            {Gis2, "G#"},
            {Ais2, "A#"},
            {B2, "B"}
    };

    // G#-Moll
    String gisMoll[][] = {
            {Gis, "G#"},
            {Ais, "A#"},
            {B, "B"},
            {Cis2, "C#"},
            {Dis2, "D#"},
            {E2, "E"},
            {Fis2, "F#"},
            {Gis2, "G#"}
    };

    // F#-Dur
    String fisDur[][] = {
            {Fis, "G#"},
            {Gis, "A#"},
            {Ais, "B"},
            {B, "C#"},
            {Cis2, "D#"},
            {Dis2, "E"},
            {F2, "E#"}, //enharmonische Verwechslung
            {Fis2, "G#"}
    };

    // D#-Moll
    String disMoll[][] = {
            {Dis, "D#"},
            {F, "E#"}, //enharmonische Verwechslung
            {Fis, "F#"},
            {Gis, "G#"},
            {Ais, "A#"},
            {B, "B"},
            {Cis2, "C#"},
            {Dis2, "D#"}
    };

    // b-Tonarten
    // F-Dur
    String fDur[][] = {
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {Ais, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"},
            {F2, "F"}
    };

    // D-Moll
    String dMoll[][] = {
            {D, "D"},
            {E, "E"},
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {Ais, "Bb"},
            {C2, "C"},
            {D2, "D"}
    };

    // Bb-Dur
    String bbDur[][] = {
            {Ais, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {Dis2, "Eb"},
            {F2, "F"},
            {G2, "G"},
            {A2, "A"},
            {Ais2, "Bb"}
    };

    // G-Moll
    String gMoll[][] = {
            {G, "G"},
            {A, "A"},
            {Ais, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {Dis2, "Eb"},
            {F2, "F"},
            {G2, "G"}
    };

    // Eb-Dur
    String ebDur[][] = {
            {Dis, "Eb"},
            {F, "F"},
            {G, "G"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {Dis2, "Eb"}
    };

    // C-Moll
    String cMoll[][] = {
            {C, "C"},
            {D, "D"},
            {Dis, "Eb"},
            {F, "F"},
            {G, "G"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {C2, "C"}
    };

    // Ab-Dur
    String abDur[][] = {
            {Gis, "Ab"},
            {Ais, "Bb"},
            {C2, "C"},
            {Cis2, "Db"},
            {Dis2, "Eb"},
            {F2, "F"},
            {G2, "G"},
            {Gis2, "Ab"}
    };

    // F-Moll
    String fMoll[][] = {
            {F, "F"},
            {G, "G"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {C2, "C"},
            {Cis2, "Db"},
            {Dis2, "Eb"},
            {F2, "F"}
    };

    // Db-Dur
    String dbDur[][] = {
            {Cis, "Db"},
            {Dis, "Eb"},
            {F, "F"},
            {Fis, "Gb"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {C, "C"},
            {Cis2, "Db"}
    };

    // Bb-Moll
    String bbMoll[][] = {
            {Ais, "Bb"},
            {C2, "C"},
            {Cis2, "Db"},
            {Dis2, "Eb"},
            {F2, "F"},
            {Fis2, "Gb"},
            {Gis2, "Ab"},
            {Ais2, "Bb"}
    };

    // Gb-Dur
    String gbDur[][] = {
            {Fis, "Gb"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {B, "Cb"}, //enharmonische Verwechslung
            {Cis2, "Db"},
            {Dis2, "Eb"},
            {F2, "F"},
            {Fis2, "Gb"}
    };

    // Eb-Moll
    String ebMoll[][] = {
            {Dis, "Eb"},
            {F, "F"},
            {Fis, "Gb"},
            {Gis, "Ab"},
            {Ais, "Bb"},
            {B, "Cb"}, //enharmonische Verwechslung
            {Cis2, "Db"},
            {Dis2, "Eb"}
    };

    // ===TONLEITERN ENDE===

    // Tonleiter setzen
    String scale[][] = cDur;

    int duration = 1;
    int frequency = 440;
    int currentIndex = 0;

    int sampleRate = 44100; // Feel free to change this
    byte soundData[] = new byte[sampleRate * duration];


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btPlay = (ImageButton) findViewById(R.id.playButton);
        btPause = (ImageButton) findViewById(R.id.pauseButton);
        btPlus = (Button) findViewById(R.id.plusButton);
        btMinus = (Button) findViewById(R.id.minusButton);
        tone = (TextView) findViewById(R.id.currentTone);

        // Tonleiter mit Spinner auswählen (funktioniert noch nicht)
        // https://developer.android.com/guide/topics/ui/controls/spinner
        Spinner keys = (Spinner) findViewById(R.id.keysSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.keys_array, android.R.layout.simple_spinner_dropdown_item);
        keys.setAdapter(adapter);




        btPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btPlay.setVisibility(View.GONE);
                btPause.setVisibility(View.VISIBLE);
                generateSound(Integer.parseInt(scale[currentIndex][0]));
                Log.d("Key", "Name: " + scale[0][1] + " Frequenz: " + scale[0][0]);
            }
        });


        btPause.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                btPause.setVisibility(View.GONE);
                btPlay.setVisibility(View.VISIBLE);
                stopSound();

            }
        });

        btPlus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(currentIndex < scale.length - 1){
                currentIndex = currentIndex + 1;
                stopSound();
                generateSound(Integer.parseInt(scale[currentIndex][0]));
                }


            }
        });


        btMinus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(currentIndex > 0){
                    currentIndex = currentIndex - 1;
                    stopSound();
                    generateSound(Integer.parseInt(scale[currentIndex][0]));
                }


            }
        });

        toneGenerator = new ToneGenerator(AudioManager.STREAM_MUSIC, 50);
    }

    public void generateSound(int frequency){
        Log.d("Button", "Playbutton clicked");
        Log.d("Index", String.valueOf(currentIndex));


        for(int i = 0; i < soundData.length; i++) {
            byte sample = (byte)(
                    Math.sin(2 * Math.PI * frequency * i / sampleRate) *
                            255);
            soundData[i] = sample;
        }



        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT, soundData.length,
                AudioTrack.MODE_STATIC
        );


        track.write(soundData, 0, soundData.length);
        track.play();
        showTone();

    }

    public void stopSound(){
        Log.d("Button", "Pausebutton clicked");
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate,
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT, soundData.length,
                AudioTrack.MODE_STATIC
        );


        track.write(soundData, 0, soundData.length);
        track.flush();
        track.stop();
        track.release();


    }

    public void showTone(){
        tone.setVisibility(View.VISIBLE);
        tone.setText(scale[currentIndex][1]);
    }
}




