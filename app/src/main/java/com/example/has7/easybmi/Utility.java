package com.example.has7.easybmi;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

/**
 * Created by has7 on 12/5/2017.
 */

public class Utility {
    public static double calculateBmi(String height, String weight) {
        float h = Float.parseFloat(height);
        float w = Float.parseFloat(weight);
        float heightInMeters = convertCmHeightToMeter(h);
        float heightsquare = heightInMeters * heightInMeters;
        float bmi = w / heightsquare;
        Log.d(Utility.class.getSimpleName(), "BMI: " + bmi);

        double roundOff = Math.round(bmi * 100.0) / 100.0;

        return roundOff;
    }
    private static float convertCmHeightToMeter(float height) {
        return height / 100;
    }



    public static String bmiMessage(double bmi) {
        if (bmi <= 15) {
            return "Very severely underweight";
        } else if (bmi > 15 && bmi <= 16) {
            return "Severely underweight";
        } else if (bmi > 16 && bmi <= 18) {
            return "Underweight";
        } else if (bmi > 18 && bmi <= 25) {
            return "Normal healthy";
        } else if (bmi > 25 && bmi <= 30) {
            return "Overweight";
        } else if (bmi > 30 && bmi <= 35) {
            return "Moderately obese";
        } else if (bmi > 35 && bmi <= 40) {
            return "Severely obese";
        } else {
            return "Very severely obese";
        }
    }

    public static Bitmap waterMark(Bitmap bitmap, String watermark, int color) {
        //get source image width and height
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int msglength = watermark.length();
        int posX = (w/2) - msglength;
        Log.d(Utility.class.getSimpleName(), w + "  " + msglength + "  " + posX);

        //Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        //create canvas object
        Canvas canvas = new Canvas(bitmap);
        //draw bitmap on canvas
        canvas.drawBitmap(bitmap, 0, 0, null);
        //create paint object
        Paint paint = new Paint();
        //apply color
        paint.setColor(color);
        //set transparency
        paint.setAlpha(250);
        //set text size
        paint.setTextSize(40);
        paint.setAntiAlias(true);
        //set should be underlined or not
        paint.setUnderlineText(false);
        //draw text on given location
        canvas.drawText(watermark, posX, 900, paint);

        return bitmap;
    }
}
