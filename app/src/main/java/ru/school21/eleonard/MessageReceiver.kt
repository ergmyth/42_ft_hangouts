package ru.school21.eleonard

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

//todo Запилить приём сообщений через стандартное приложение сообщений
class MessageReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.hasExtra("title") && intent.hasExtra("text")) {

            intent.action = Constants.SP_BROADCAST_ACTION_MESSAGE_TO_CHAT
            BaseApp.getInstance().sendBroadcast(intent)
        }
    }
}