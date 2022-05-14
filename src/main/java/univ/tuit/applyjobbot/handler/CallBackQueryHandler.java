package univ.tuit.applyjobbot.handler;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import univ.tuit.applyjobbot.messageSender.MessageSender;

@Component
public class CallBackQueryHandler implements Handler<CallbackQuery> {

    private final MessageSender messageSender;

    public CallBackQueryHandler(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public void choose(CallbackQuery callbackQuery) {

    }
}
