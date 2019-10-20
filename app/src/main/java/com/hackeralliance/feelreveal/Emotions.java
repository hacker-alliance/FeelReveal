package com.hackeralliance.feelreveal;

import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.HashMap;

public enum  Emotions {
    HAPPY(new long[]{0,100,0}), SURPRISE(new long[]{0,100,0}), NEUTRAL(new long[]{0,100,0}), CONTEMPT(new long[]{0,100,0}), DISGUST(new long[]{0,100,0}), FEAR(new long[]{0,100,0}), ANGER(new long[]{0,100,0});
    private long[] pattern;
    Emotions(long[] pattern){
        this.pattern = pattern;
    }
    public void vibrationPattern(){

    }
    public static HashMap<Emotions, Double> parse(Emotion emote){
            HashMap<Emotions, Double> emotion = new HashMap<>();
            emotion.put(Emotions.HAPPY,emote.happiness);
            emotion.put(Emotions.SURPRISE, emote.surprise);
            emotion.put(Emotions.NEUTRAL, emote.neutral);
            emotion.put(Emotions.CONTEMPT, emote.disgust);
            emotion.put(Emotions.DISGUST, emote.disgust);
            emotion.put(Emotions.FEAR, emote.fear);
            emotion.put(Emotions.ANGER,emote.anger);
            return emotion;
    }

}
