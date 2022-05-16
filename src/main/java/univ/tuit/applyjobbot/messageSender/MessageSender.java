package univ.tuit.applyjobbot.messageSender;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

public interface MessageSender {

    void sendMessage(SendMessage sendMessage);

    void sendEditMessage(EditMessageText editMessageText);

    void sendLocation(SendLocation sendLocation);

    void sendFile(SendDocument sendDocument);
}
