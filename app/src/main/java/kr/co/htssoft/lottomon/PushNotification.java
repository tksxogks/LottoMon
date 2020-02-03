package kr.co.htssoft.lottomon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


public class PushNotification extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        //그래서 Logcat으로 메세지 받았는지 확인
        Log.v("TAG", "onMessageReceived!!");

        String notiTitle = "title";
        String notiBody = "body";
        if(remoteMessage.getNotification()!=null){
            notiTitle=remoteMessage.getNotification().getTitle();
            notiBody=remoteMessage.getNotification().getBody();
            Log.w("TAG", notiTitle+" : "+notiBody);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //알림을 만들어주는 건축가 객체
        NotificationCompat.Builder builder;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("ch01", "chnull 01", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
            builder = new NotificationCompat.Builder(this, "cho1");
        }else{
            builder = new NotificationCompat.Builder(this, null);
        }
        //건축가에 알림 설정
        builder.setSmallIcon(R.drawable.common_full_open_on_phone);
        builder.setContentText(notiTitle);
        builder.setContentText(notiBody);
        builder.setAutoCancel(true);
        //알림객체 만들기
        Notification notification= builder.build();
        //알림매니져에게 알림공지 요청
        notificationManager.notify(10, notification);

    }
}
