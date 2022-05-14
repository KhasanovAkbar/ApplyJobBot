package univ.tuit.applyjobbot.services;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface SendMessageService<T> {

    void start(T t);

    void restart(T t);

    void register(T t);

    void registerJob(T t, Integer id);
}
