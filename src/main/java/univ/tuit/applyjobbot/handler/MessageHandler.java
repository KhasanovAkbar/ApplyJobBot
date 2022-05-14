package univ.tuit.applyjobbot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobbot.cache.Cache;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.services.SendMessageServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private final Cache<Jobs> cache;

    @Autowired
    private SendMessageServiceImpl sendMessageService;


    public MessageHandler(Cache<Jobs> cache) {
        this.cache = cache;
    }

    @Override
    public void choose(Message message) {
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();
        String answer = message_text;

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text, answer);

            switch (message.getText()) {
                case "/start":
                    sendMessageService.start(message);
                    //    cache.add(user);
                    break;

                case "/restart":
                    sendMessageService.restart(message);
                    //     cache.add(user);
                    break;

                case "Register":
                    sendMessageService.register(message);
                    break;
                default:
                    List<Jobs> all = cache.getAll();
                    Jobs lastJob = new Jobs();
                    for (Jobs job:all){
                        if (job.getUserId().equals(user_id)){
                            lastJob = job;
                            break;
                        }
                    }
                    if (message.getFrom().getId().equals(cache.findBy(user_id, lastJob.getId()).getUserId())){
                        sendMessageService.registerJob(message, lastJob.getId());
                    }

            //    case "List":

                /*default:
                    List<BotUser> all = cache.getAll();
                    BotUser lastUser = new BotUser();
                    for (BotUser botUser : all) {
                        if (botUser.getUserId().equals(user_id)) {
                            lastUser = botUser;
                            break;
                        }
                    }
                    if (message.getFrom().getId().equals(cache.findBy(user_id, lastUser.getId()).getUserId()) && lastUser.isPhoneNumber()) {
                        sendMessageService.registerUser(message, lastUser.getId());
                    }else {
                        sendMessageService.others(message);
                    }*/
            }
        }

    }

    public static void log(String first_name, String last_name, String user_id, String txt, String bot_answer) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
        System.out.println("Bot answer: \n Text - " + bot_answer);
    }

}
