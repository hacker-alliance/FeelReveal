package com.hackeralliance.feelreveal;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
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

    private void createNotificationChannel(Context c) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //CharSequence name = getString(R.string.channel_name);
            //String description = getString(R.string.channel_description);
            CharSequence name = "FeelReveal";
            String description = "FeelRevealDescription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("FeelRevealChannel", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = c.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        //}
    }

    public void notifyEmotion(String emotion, Context context) {
        createNotificationChannel(context);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "FeelRevealChannel")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(emotion)
                .setContentText(emotion)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        //NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        //NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (android.app.NotificationManager) context.getSystemService(NotificationManager.class);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1337, builder.build());
    }
    public void detectAndFrame(final Bitmap imageBitmap, final TextView text, final Vibrator v, final Context c) {
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

                        String display = "";
                        double max = -9999;
                        Emotions emo = null;
                        HashMap<Emotions,Double> emotions;
                        if(result.length > 1){
                                display += "(GRP)";
                                emotions = Emotions.parse(result);
                        }else{
                            emotions = Emotions.parse(result[0].faceAttributes.emotion);
                        }

                        for(Emotions emote:emotions.keySet()){
                            Log.i(display + "FACE",emote.name() + ": " + emotions.get(emote));
                            if(max < emotions.get(emote)){
                                max = emotions.get(emote);
                                emo = emote;

                            }
                        }

                        Log.i("FACEVALUE", max + "");
                        display += "(" + result.length +") \n" + emo.name();
                        text.setText(display);
                        emo.triggerVibration(v);
                        notifyEmotion(emo.name() + " " + result.length8, c);

//                        ImageView imageView = findViewById(R.id.imageView1);
//                        imageView.setImageBitmap(
//                                drawFaceRectanglesOnBitmap(imageBitmap, result));
//                        imageBitmap.recycle();
                    }
                };

        detectTask.execute(inputStream);
    }


}
