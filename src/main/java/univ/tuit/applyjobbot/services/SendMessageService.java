package univ.tuit.applyjobbot.services;


public interface SendMessageService<T> {

    void start(T t);

    void restart(T t);

    void register(T t);

    void registerJob(T t, Integer id);

    void jobList(T t, Integer id);

    void applyList(T t);

    void mainPage(T t, Integer id);

    void help(T t);
}
