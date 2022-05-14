package univ.tuit.applyjobbot.processor;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobbot.handler.CallBackQueryHandler;
import univ.tuit.applyjobbot.handler.MessageHandler;


@Component
public class DefaultProcessor implements Processor{

    private final CallBackQueryHandler callBackQueryHandler;
    private final MessageHandler messageHandler;

    public DefaultProcessor(CallBackQueryHandler callBackQueryHandler, MessageHandler messageHandler) {
        this.callBackQueryHandler = callBackQueryHandler;
        this.messageHandler = messageHandler;
    }

    @Override
    public void executeQuery(Message message) {
        messageHandler.choose(message);

    }

    @Override
    public void executeCallBackQuery(CallbackQuery callbackQuery) {
        callBackQueryHandler.choose(callbackQuery);
    }
}
