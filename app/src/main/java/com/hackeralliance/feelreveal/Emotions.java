package com.hackeralliance.feelreveal;

import android.os.Vibrator;

import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.HashMap;

public enum  Emotions {
    HAPPY((Vibrator v)->{
        long[] pattern = {0,300,100,300,100};
        v.vibrate(pattern, -1);
    }),
    SURPRISE((Vibrator v)->{
        long[] pattern = {0,300,100,300,100,300,100,300};
        v.vibrate(pattern, -1);
    }),
    NEUTRAL((Vibrator v)->{
        long[] pattern = {0,200};
        v.vibrate(pattern, -1);
    }), CONTEMPT((Vibrator v)->{
        long[] pattern = {0,300,100,300};
        v.vibrate(pattern, -1);
    }), DISGUST((Vibrator v)->{
        long[] pattern = {0,500,100,500};
        v.vibrate(pattern, -1);
    }), FEAR((Vibrator v)->{
        long[] pattern = {0,300,100,300};
        v.vibrate(pattern, -1);
    }), ANGER((Vibrator v)->{
        long[] pattern = {0,1000};
        v.vibrate(pattern,-1);
    });
    private IVibrate iVib;
    Emotions(IVibrate iVib){
        this.iVib = iVib;
    }
    public void triggerVibration(Vibrator v){
        iVib.vibrate(v);
    }
    public static HashMap<Emotions, Double> parse(Emotion emote){
            HashMap<Emotions, Double> emotion = new HashMap<>();
            emotion.put(Emotions.HAPPY,emote.happiness/4);
            emotion.put(Emotions.SURPRISE, emote.surprise);
            emotion.put(Emotions.NEUTRAL,  emote.neutral - 0.99);
            emotion.put(Emotions.CONTEMPT, emote.disgust);
            emotion.put(Emotions.DISGUST, emote.disgust);
            emotion.put(Emotions.FEAR, emote.fear);
            emotion.put(Emotions.ANGER,emote.anger);
            return emotion;
    }

}
