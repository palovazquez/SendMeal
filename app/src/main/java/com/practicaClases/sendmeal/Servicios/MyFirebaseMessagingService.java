package com.practicaClases.sendmeal.Servicios;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.practicaClases.sendmeal.MainActivity;
import com.practicaClases.sendmeal.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";
    public static final int NOTIFICATION_ID = 99;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...
        Log.d("", "From: " + remoteMessage.getFrom());

        // Pueden validar si el mensaje trae datos
        if (remoteMessage.getData().size() > 0) {
            Log.d("NOTIFICACIONES", "Payload del mensaje: " + remoteMessage.getData());
            // Realizar alguna acción en consecuencia
        }
        // Pueden validar si el mensaje trae una notificación
        if (remoteMessage.getNotification() != null) {
            Log.d("NOTIFICACIONES", "Cuerpo de la notificación del mensaje: " + remoteMessage.getNotification().getBody());
            // También pueden usar:
             remoteMessage.getNotification().getTitle();
        }
        // Cualquier otra acción que quieran realizar al recibir un mensaje de firebase, la pueden realizar aca
        // EJ:
        sendNotification(remoteMessage.getNotification().getBody(),
                remoteMessage.getNotification().getTitle());
        //TODO AGREGAR TITULO EN LA CONSOLA DE FIREBASE
    }

    // Función para crear una notificación (completar)
    private void sendNotification(String messageBody, String title) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */,
                 intent, 0);


        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, default_notification_channel_id)
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setChannelId(NOTIFICATION_CHANNEL_ID)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //CREAR CANAL DE NOTIFICACIONES
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH ;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(NOTIFICATION_ID , notificationBuilder.build());
    }

}