package com.hackeralliance.feelreveal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Vibrator;
import android.util.Log;
import android.widget.TextView;

// Matt's webcam is 30FPS

public class CameraController {
    long FPS = 30;
    long seconds = 0;
    double initTime = -1;
    int frameCounter = 0;
    double temp = 0;
    private FaceRecognition faceRecognition;
    public CameraController() {
        faceRecognition = new FaceRecognition();
    }

    // Called on every Camera frame
    public void onFrame(Bitmap frame, TextView text, Vibrator v, Context c) {
        frameCounter += 1;
        Log.i("TEST", frameCounter + "");
        if (initTime == -1) {
            initTime = System.currentTimeMillis();
        } else if (frameCounter == FPS) {

            seconds++;
            double secondsElapsed = (System.currentTimeMillis()-initTime)/1000.0;
            FPS = Math.round(FPS/secondsElapsed);


            frameCounter = 0;
            initTime = System.currentTimeMillis();
            Log.i("FRAMES", seconds + "");
            if(seconds >= 3){
                faceRecognition.detectAndFrame(frame,text, v, c);
                seconds = 0;
            }
        }
    }

    // Called every second
    private void onSecond(Bitmap frame) {

    }
}
