package univ.tuit.applyjobbot.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import univ.tuit.applyjobbot.cache.Cache;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.domain.State;
import univ.tuit.applyjobbot.messageSender.MessageSender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class SendMessageServiceImpl implements SendMessageService<Message> {

    private final MessageSender messageSender;
    private final Cache<Jobs> cache;
    private final Cache<Requirement> requirementCache;

    static Jobs jobs = new Jobs();
    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();

    Jobs byUserId = new Jobs();

    public SendMessageServiceImpl(MessageSender messageSender, Cache<Jobs> cache, Cache<Requirement> requirementCache) {
        this.messageSender = messageSender;
        this.cache = cache;
        this.requirementCache = requirementCache;
    }


    @Override
    public void start(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Hello " + message.getFrom().getFirstName() + "\nLorem ipsum");
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        keyboardRow.clear();
        sendMessage.setReplyMarkup(buttons());
        messageSender.sendMessage(sendMessage);
    }

    @Override
    public void restart(Message message) {
        //restart bot
    }

    @Override
    public void register(Message message) {
        long chat_id = message.getChatId();

        info(message, chat_id);
        SendMessage sm = new SendMessage();
        sm.setText("Xodim topish uchun ariza berish\n" +
                "\n" +
                "Hozir sizga birnecha savollar beriladi. \n" +
                "Har biriga javob bering. \n" +
                "Oxirida agar hammasi to`g`ri bo`lsa, HA tugmasini bosing va arizangiz Adminga yuboriladi.");
        sm.setChatId(String.valueOf(message.getChatId()));
        messageSender.sendMessage(sm);

        messageSender.sendMessage(SendMessage.builder()
                .text("\uD83C\uDF93 Idora nomi?")
                .chatId(String.valueOf(message.getChatId()))
                .build());
        jobs.setCompanyName(message.getText());
        jobs.setIsCompanyName(true);
        cache.update(jobs);

    }

    @Override
    public void registerJob(Message message, Integer id) {

        long chat_id = message.getChatId();
        byUserId = cache.findBy(chat_id, id);
        if (byUserId.getTechnology().equals("Register") && byUserId.isCompanyName()) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCDA Texnologiya:\n" +
                            "\n" +
                            "Talab qilinadigan texnologiyalarni kiriting?\n" +
                            "Texnologiya nomlarini vergul bilan ajrating. Masalan, \n" +
                            "\n" +
                            "Java, C++, C#")
                    .chatId(String.valueOf(chat_id))
                    .build());
            byUserId.setCompanyName(message.getText());
            byUserId.setTechnology(message.getText());
            byUserId.setIsTechnology(true);

        } else if (byUserId.getTerritory().equals("Register") && byUserId.isTechnology()) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83C\uDF10 Hudud: \n" +
                            "\n" +
                            "Qaysi hududdansiz?\n" +
                            "Viloyat nomi, Toshkent shahar yoki Respublikani kiriting.")
                    .chatId(String.valueOf(chat_id))
                    .build());
            byUserId.setTechnology(message.getText());
            byUserId.setTerritory(message.getText());

            //generate Job id
            byUserId.setJobId(generateJobId(byUserId.getCompanyName()));
            byUserId.setIsTerritory(true);

        } else if (byUserId.isTerritory() && byUserId.getState().equals(State.NONE.toString())) {
            byUserId.setTerritory(message.getText());
            addRequirement(chat_id);
            byUserId.setIsTerritory(false);

        } else if (byUserId.isRequirements() && byUserId.getState().equals(State.REQUIREMENT.toString())) {

            //add requirement
            String requirement = message.getText();
            Requirement r = new Requirement();
            r.setName(requirement);
            r.setJobId(byUserId.getJobId());
            requirementCache.add(r);

            SendMessage sm = new SendMessage();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Yes");
            row1.add(KeyboardButton.builder().text("No").build());
            keyboardRow.clear();
            keyboardRow.add(row1);
            markup.setKeyboard(keyboardRow);
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            sm.setText("Davom etishni xohlaysizmi?");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            byUserId.setState(State.NONE.toString());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Yes") && byUserId.getState().equals(State.NONE.toString())) {
            addRequirement(chat_id);

        } else if (message.getText().equals("No") && byUserId.getState().equals(State.NONE.toString())) {

            markup = new ReplyKeyboardMarkup();
            keyboardRow = new ArrayList<>();

            List<Requirement> byJobId = requirementCache.findByJobId(byUserId.getJobId());
            StringBuilder sb = new StringBuilder();
            for (Requirement requirement : byJobId) {
                sb.append(requirement.getName()).append(", ");
            }

            messageSender.sendMessage(SendMessage.builder().text(
                            "\uD83C\uDF93 Id: " + byUserId.getJobId() + "\n" +
                            "\uD83C\uDFE2 Idora: " + byUserId.getCompanyName() + "\n" +
                            "\uD83D\uDCDA Texnologiya: " + byUserId.getTechnology() + "\n" +
                            "\uD83C\uDDFA\uD83C\uDDFF Telegram: @" + byUserId.getUsername() + "\n" +
                            "\uD83C\uDF10 Hudud: " + byUserId.getTerritory() + "\n" +
                            "‼️ Qo`shimcha: " + sb)
                    .parseMode("HTML")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());

            SendMessage sm = new SendMessage();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Yes");
            row1.add(KeyboardButton.builder().text("No").build());
            keyboardRow.add(row1);
            markup.setKeyboard(keyboardRow);
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            sm.setText("Is information correct?");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            messageSender.sendMessage(sm);
            byUserId.setState(State.CHECKED.toString());

        } else if (message.getText().equals("Yes") && byUserId.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Your data sent to admin" +
                    "\n<b>Thank you for registration</b>");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            byUserId.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Yes") && byUserId.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Your data sent to admin" +
                    "\n<b>Thank you for registration</b>");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            byUserId.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("No") && byUserId.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Your data denied " +
                    "\nClick /start. The announcement will start again");
            sm.setChatId(String.valueOf(message.getChatId()));
            byUserId.setState(State.DENIED.toString());
            byUserId.setIsCompanyName(false);
            byUserId.setIsTechnology(false);
            byUserId.setIsTerritory(false);
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
        }
        cache.update(byUserId);
    }

    private String generateJobId(String companyName) {
        String result;
        String c1 = companyName.trim().substring(0, 1).toUpperCase();
        Date date = new Date();
        result = c1 + date.getDate() + date.getHours() + date.getMinutes() + date.getSeconds();
        return result;
    }

    public static void info(Message message, long user_id) {
        String username = message.getFrom().getUserName();

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        String format = dateFormat.format(date);

        jobs.setUserId(user_id);
        jobs.setUsername(username);
        jobs.setState(State.NONE.toString());
        jobs.setRegistrationTime(format);
    }

    private ReplyKeyboardMarkup buttons() {

        KeyboardRow row1 = new KeyboardRow();
        row1.add("Register");
        row1.add(KeyboardButton.builder().text("List").build());

        keyboardRow.add(row1);

        markup.setKeyboard(keyboardRow);
        markup.setResizeKeyboard(true);
        markup.setOneTimeKeyboard(true);
        return markup;
    }

    void addRequirement(Long chat_id) {
        messageSender.sendMessage(SendMessage
                .builder().text("\uD83C\uDF10 Qo'shimcha talablar: \n")
                .chatId(String.valueOf(chat_id))
                .build());
        byUserId.setIsRequirements(true);
        byUserId.setState(State.REQUIREMENT.toString());

    }
}
