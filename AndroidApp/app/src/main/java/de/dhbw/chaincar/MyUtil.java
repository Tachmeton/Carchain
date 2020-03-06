package de.dhbw.chaincar;

import android.content.Context;

public class MyUtil {
    public static String toTimeString(Context context, float val){
        int whole = (int)val;
        int frac = (int)(((val - whole) * 100f) * .6f);

        return context.getString(R.string.time)
                .replace("{1}", String.valueOf(whole < 10 ? "0" + whole : whole))
                .replace("{2}", String.valueOf(frac < 10 ? "0" + frac : frac));
    }

    public static float toTimeRange(float val){
        int whole = (int)val;
        int frac = (int)(((val - whole) * 100f) * .6f);

        return whole + frac;
    }
}
