package univ.tuit.applyjobbot.services;


import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageService<T> {

    SendMessage start(T t);

    SendMessage restart(T t);

    SendMessage register(T t);

    SendMessage registerJob(T t, Integer id);

    SendMessage jobList(T t, Integer id);

    SendMessage applyList(T t);

    SendMessage mainPage(T t, Integer id);

    SendMessage help(T t);
}
