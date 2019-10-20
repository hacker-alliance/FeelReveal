package com.hackeralliance.feelreveal;

import android.graphics.Bitmap;
import android.util.Log;

// Matt's webcam is 30FPS

public class CameraController {
    long FPS = 30;
    double initTime = -1;
    int frameCounter = 0;
    double temp = 0;

    public CameraController() {}

    // Called on every Camera frame
    public void onFrame(Bitmap frame) {
        frameCounter += 1;

        if (initTime == -1) {
            initTime = System.currentTimeMillis();
        } else if (frameCounter == FPS) {
            double secondsElapsed = (System.currentTimeMillis()-initTime)/1000.0;
            FPS = Math.round(FPS/secondsElapsed);

            this.onSecond(frame);
            frameCounter = 0;
            initTime = System.currentTimeMillis();
        }
    }

    // Called every second
    private void onSecond(Bitmap frame) {

    }
}
