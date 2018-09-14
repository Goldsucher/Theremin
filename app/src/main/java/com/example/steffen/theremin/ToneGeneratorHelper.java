package com.example.steffen.theremin;

import java.util.HashMap;

public class ToneGeneratorHelper {

    // Frequenzen der Töne
    private static final String C = "262";
    private static final String CIS = "277";
    private static final String D = "294";
    private static final String DIS = "311";
    private static final String E = "330";
    private static final String F = "349";
    private static final String FIS = "370";
    private static final String G = "392";
    private static final String GIS = "415";
    private static final String A = "440";
    private static final String AIS = "466";
    private static final String B = "494";
    private static final String C2 = "523";
    private static final String CIS2 = "554";
    private static final String D2 = "587";
    private static final String DIS2 = "622";
    private static final String E2 = "659";
    private static final String F2 = "698";
    private static final String FIS2 = "740";
    private static final String G2 = "784";
    private static final String GIS2 = "831";
    private static final String A2 = "880";
    private static final String AIS2 = "932";
    private static final String B2 = "988";

    // C-Dur
    private static final String C_DUR[][] = {
            {C, "C"},
            {D, "D"},
            {E, "E"},
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C2"}
    };

    // A-Moll
    private static final String A_MOLL[][] = {
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
    private static final String G_DUR[][] = {
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"},
            {FIS2, "F#"},
            {G2, "G"}
    };

    // E-Moll
    private static final String E_Moll[][] = {
            {E, "E"},
            {FIS, "F#"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"}
    };

    // D-Dur
    private static final String D_DUR[][] = {
            {D, "D"},
            {E, "E"},
            {F, "F#"},
            {G, "G"},
            {A, "A"},
            {B, "B"},
            {CIS2, "C#"},
            {D2, "D"}
    };

    // B-Moll
    private static final String B_MOLL[][] = {
            {B, "B"},
            {CIS2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {FIS2, "F#"},
            {G2, "G"},
            {A2, "A"},
            {B2, "B"}
    };

    // A-Dur
    private static final String A_DUR[][] = {
            {A, "A"},
            {B, "B"},
            {CIS2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {FIS2, "F#"},
            {GIS2, "G#"},
            {A2, "A"}
    };

    // F#-Moll
    private static final String FIS_MOLL[][] = {
            {FIS, "F#"},
            {GIS, "G#"},
            {A, "A"},
            {B, "B"},
            {CIS2, "C#"},
            {D2, "D"},
            {E2, "E"},
            {FIS2, "F#"}
    };

    // E-Dur
    private static final String E_DUR[][] = {
            {E, "E"},
            {FIS, "F#"},
            {GIS, "G#"},
            {A, "A"},
            {B, "B"},
            {CIS2, "C#"},
            {DIS2, "D#"},
            {E2, "E"}
    };

    // C#-Moll
    private static final String CIS_MOLL[][] = {
            {CIS, "C#"},
            {DIS, "D#"},
            {E, "E"},
            {FIS, "F#"},
            {GIS, "G#"},
            {A, "A"},
            {B, "B"},
            {CIS2, "C#"}
    };

    // B-Dur
    private static final String B_DUR[][] = {
            {B, "B"},
            {CIS2, "C#"},
            {DIS2, "D#"},
            {E2, "E"},
            {FIS2, "F#"},
            {GIS2, "G#"},
            {AIS2, "A#"},
            {B2, "B"}
    };

    // G#-Moll
    private static final String GIS_MOLL[][] = {
            {GIS, "G#"},
            {AIS, "A#"},
            {B, "B"},
            {CIS2, "C#"},
            {DIS2, "D#"},
            {E2, "E"},
            {FIS2, "F#"},
            {GIS2, "G#"}
    };

    // F#-Dur
    private static final String FIS_DUR[][] = {
            {FIS, "F#"},
            {GIS, "G#"},
            {AIS, "A#"},
            {B, "B"},
            {CIS2, "C#"},
            {DIS2, "D#"},
            {F2, "F#"}, //enharmonische Verwechslung
            {FIS2, "F#"}
    };

    // D#-Moll
    private static final String DIS_MOLL[][] = {
            {DIS, "D#"},
            {F, "E#"}, //enharmonische Verwechslung
            {FIS, "F#"},
            {GIS, "G#"},
            {AIS, "A#"},
            {B, "B"},
            {CIS2, "C#"},
            {DIS2, "D#"}
    };

    // b-Tonarten
    // F-Dur
    private static final String F_DUR[][] = {
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {AIS, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {E2, "E"},
            {F2, "F"}
    };

    // D-Moll
    private static final String D_MOLL[][] = {
            {D, "D"},
            {E, "E"},
            {F, "F"},
            {G, "G"},
            {A, "A"},
            {AIS, "Bb"},
            {C2, "C"},
            {D2, "D"}
    };

    // Bb-Dur
    private static final String BB_DUR[][] = {
            {AIS, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {DIS2, "Eb"},
            {F2, "F"},
            {G2, "G"},
            {A2, "A"},
            {AIS2, "Bb"}
    };

    // G-Moll
    private static final String G_MOLL[][] = {
            {G, "G"},
            {A, "A"},
            {AIS, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {DIS2, "Eb"},
            {F2, "F"},
            {G2, "G"}
    };

    // Eb-Dur
    private static final String EB_DUR[][] = {
            {DIS, "Eb"},
            {F, "F"},
            {G, "G"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {C2, "C"},
            {D2, "D"},
            {DIS2, "Eb"}
    };

    // C-Moll
    private static final String C_MOLL[][] = {
            {C, "C"},
            {D, "D"},
            {DIS, "Eb"},
            {F, "F"},
            {G, "G"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {C2, "C"}
    };

    // Ab-Dur
    private static final String AB_DUR[][] = {
            {GIS, "Ab"},
            {AIS, "Bb"},
            {C2, "C"},
            {CIS2, "Db"},
            {DIS2, "Eb"},
            {F2, "F"},
            {G2, "G"},
            {GIS2, "Ab"}
    };

    // F-Moll
    private static final String F_MOLL[][] = {
            {F, "F"},
            {G, "G"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {C2, "C"},
            {CIS2, "Db"},
            {DIS2, "Eb"},
            {F2, "F"}
    };

    // Db-Dur
    private static final String DB_Dur[][] = {
            {CIS, "Db"},
            {DIS, "Eb"},
            {F, "F"},
            {FIS, "Gb"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {C, "C"},
            {CIS2, "Db"}
    };

    // Bb-Moll
    private static final String BB_MOLL[][] = {
            {AIS, "Bb"},
            {C2, "C"},
            {CIS2, "Db"},
            {DIS2, "Eb"},
            {F2, "F"},
            {FIS2, "Gb"},
            {GIS2, "Ab"},
            {AIS2, "Bb"}
    };

    // Gb-Dur
    private static final String GB_DUR[][] = {
            {FIS, "Gb"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {B, "Cb"}, //enharmonische Verwechslung
            {CIS2, "Db"},
            {DIS2, "Eb"},
            {F2, "F"},
            {FIS2, "Gb"}
    };

    // Eb-Moll
    private static final String EB_MOLL[][] = {
            {DIS, "Eb"},
            {F, "F"},
            {FIS, "Gb"},
            {GIS, "Ab"},
            {AIS, "Bb"},
            {B, "Cb"}, //enharmonische Verwechslung
            {CIS2, "Db"},
            {DIS2, "Eb"}
    };

    private static final String defaultStartScale = "C_DUR";
    private static final HashMap<String,String[][]> scaleMap = new HashMap<>();

    private String scale[][];
    private int sampleRate;

    public ToneGeneratorHelper() {
        scaleMap.put("C-Dur", C_DUR);
        scaleMap.put("G-Dur", G_DUR);
        scaleMap.put("D-Dur", D_DUR);
        scaleMap.put("a-Dur", A_DUR);
        scaleMap.put("E-Dur", E_DUR);
        scaleMap.put("B-Dur", D_DUR);
        scaleMap.put("F#-Dur", F_DUR);
        scaleMap.put("Bb-Dur", FIS_DUR);
        scaleMap.put("Eb-Dur", EB_DUR);
        scaleMap.put("Ab-Dur", AB_DUR);
        scaleMap.put("Db-Dur", DB_Dur);
        scaleMap.put("Gb-Dur", GB_DUR);
        scaleMap.put("A-Moll", A_MOLL);
        scaleMap.put("E-Moll", E_Moll);
        scaleMap.put("B-Moll", B_MOLL);
        scaleMap.put("F#-Moll", FIS_MOLL);
        scaleMap.put("C#-Moll", CIS_MOLL);
        scaleMap.put("G#-Moll", GIS_MOLL);
        scaleMap.put("D#-Moll", DIS_MOLL);
        scaleMap.put("D-Moll", D_MOLL);
        scaleMap.put("G-Moll", G_MOLL);
        scaleMap.put("C-Moll", C_MOLL);
        scaleMap.put("F-Moll", F_MOLL);
        scaleMap.put("Bb-Moll", BB_MOLL);
        scaleMap.put("Eb-Moll", EB_MOLL);

        scale= scaleMap.get(defaultStartScale);
        sampleRate = 44100;

    }

    public HashMap<String, String[][]> getScaleMap() {
        return scaleMap;
    }

    public String[][] getScale() {
        return scale;
    }

    public void setScale(String[][] scale) {
        this.scale = scale;
    }

    public int getSampleRate() {
        return sampleRate;
    }

    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

}
