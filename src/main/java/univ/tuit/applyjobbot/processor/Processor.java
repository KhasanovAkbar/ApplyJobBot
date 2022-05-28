package univ.tuit.applyjobbot.processor;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Processor {

    SendMessage executeQuery(Message message);

    SendMessage executeCallBackQuery(CallbackQuery callbackQuery);

    default SendMessage processor(Update update) {
        SendMessage sm = null;
        if (update.hasMessage()) {
            sm = executeQuery(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            sm = executeCallBackQuery(update.getCallbackQuery());
        }
        return sm;
    }
}
