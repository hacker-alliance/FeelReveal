package com.hackeralliance.feelreveal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.Face;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;

public class FaceRecognition {

    // Add your Face endpoint to your environment variables.
    private final String apiEndpoint = "https://FacialRecogAzure.cognitiveservices.azure.com/face/v1.0/detect?";
    // Add your Face subscription key to your environment variables.
    private final String subscriptionKey = "d5be6590c2ce476c8180a9c02583b7ce";

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient(apiEndpoint, subscriptionKey);

    public void detectAndFrame(final Bitmap imageBitmap, final TextView text, final Vibrator v) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    // returnFaceAttributes:
                                    new FaceServiceClient.FaceAttributeType[] {
                                            FaceServiceClient.FaceAttributeType.Emotion }


                            );
                            if (result == null){
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            Log.i("TEST",result.length + "");
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog

                    }
                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress

                    }
                    @Override
                    protected void onPostExecute(Face[] result) {
                        //TODO: update face frames

                        if(!exceptionMessage.equals("")){
                            System.out.println(exceptionMessage);
                        }
                        if (result == null) return;
                        if(result.length == 0) return;

                        HashMap<Emotions,Double> emotions = Emotions.parse(result[0].faceAttributes.emotion);
                        double max = -9999;
                        Emotions emo = null;
                        for(Emotions emote:emotions.keySet()){
                            Log.i("FACE",emote.name() + ": " + emotions.get(emote));
                            if(max < emotions.get(emote)){
                                max = emotions.get(emote);
                                emo = emote;

                            }
                        }
                        Log.i("FACEVALUE", max + "");
                        text.setText(emo.name());
                        emo.triggerVibration(v);


//                        ImageView imageView = findViewById(R.id.imageView1);
//                        imageView.setImageBitmap(
//                                drawFaceRectanglesOnBitmap(imageBitmap, result));
//                        imageBitmap.recycle();
                    }
                };

        detectTask.execute(inputStream);
    }


}
