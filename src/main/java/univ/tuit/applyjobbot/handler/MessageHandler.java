package univ.tuit.applyjobbot.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.services.JobService;
import univ.tuit.applyjobbot.store.SendMessageStoreLogic;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Component
public class MessageHandler implements Handler<Message> {

    @Autowired
    private JobService<Jobs> jobService;

    @Autowired
    private SendMessageStoreLogic sendMessageService;

    @Override
    public void choose(Message message) {
        String user_first_name = message.getChat().getFirstName();
        String user_last_name = message.getChat().getLastName();
        long user_id = message.getChat().getId();
        String message_text = message.getText();

        if (message.hasText()) {
            log(user_first_name, user_last_name, Long.toString(user_id), message_text);
            List<Jobs> all = jobService.getAll();
            Jobs lastJob = new Jobs();
            for (Jobs job : all) {
                if (job.getUserId().equals(user_id)) {
                    lastJob = job;
                    break;
                }
            }
            switch (message.getText()) {
                case "/start":
                    sendMessageService.start(message);
                    break;

                case "/restart":
                    sendMessageService.restart(message);
                    break;
                case "/help":
                    sendMessageService.help(message);
                    break;
                case "Register":
                    sendMessageService.register(message);
                    break;
                case "List":
                    sendMessageService.jobList(message, lastJob.getId());
                    break;
                case "\uD83C\uDFE0️ Bosh sahifa":
                    sendMessageService.mainPage(message, lastJob.getId());
                    break;
                default:
                    if (message.getFrom().getId().equals(jobService.findByUserIdAndId(user_id, lastJob.getId()).getUserId())) {
                        String[] text = message.getText().split(" ");

                        if (!text[0].equals("Id:"))
                            sendMessageService.registerJob(message, lastJob.getId());
                        else sendMessageService.applyList(message);
                    }
            }
        }

    }

    public static void log(String first_name, String last_name, String user_id, String txt) {
        System.out.println("\n ----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Message from " + first_name + " " + last_name + ". (id = " + user_id + ") \n Text - " + txt);
    }

}
