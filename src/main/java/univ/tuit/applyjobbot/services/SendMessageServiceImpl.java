package univ.tuit.applyjobbot.services;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import univ.tuit.applyjobbot.cache.Cache;
import univ.tuit.applyjobbot.domain.Apply;
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
    private final Cache<Apply> applyCache;

    static Jobs jobs = new Jobs();

    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();


    public SendMessageServiceImpl(MessageSender messageSender, Cache<Jobs> cache, Cache<Requirement> requirementCache, Cache<Apply> applyCache) {
        this.messageSender = messageSender;
        this.cache = cache;
        this.requirementCache = requirementCache;
        this.applyCache = applyCache;
    }


    @Override
    public void start(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Assalom alaykum " + message.getFrom().getFirstName() +
                " Xush kelibsiz!\n" +
                "\n" +
                "/help yordam buyrugi orqali nimalarga qodir ekanligimni bilib oling! ");
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttons());
        messageSender.sendMessage(sendMessage);
    }

    @Override
    public void restart(Message message) {
        //restart bot
        start(message);
    }

    @Override
    public void register(Message message) {
        long chat_id = message.getChatId();
        jobs = new Jobs();
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
        jobs = cache.findBy(chat_id, id);
        if (jobs.getTechnology().equals("Register") && jobs.isCompanyName()) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCDA Texnologiya:\n" +
                            "\n" +
                            "Talab qilinadigan texnologiyalarni kiriting?\n" +
                            "Texnologiya nomlarini vergul bilan ajrating. Masalan, \n" +
                            "\n" +
                            "Java, C++, C#")
                    .chatId(String.valueOf(chat_id))
                    .build());
            jobs.setCompanyName(message.getText());
            jobs.setTechnology(message.getText());
            jobs.setIsTechnology(true);

        } else if (jobs.getTerritory().equals("Register") && jobs.isTechnology()) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83C\uDF10 Hudud: \n" +
                            "\n" +
                            "Qaysi hududdansiz?\n" +
                            "Viloyat nomi, Toshkent shahar yoki Respublikani kiriting.")
                    .chatId(String.valueOf(chat_id))
                    .build());
            jobs.setTechnology(message.getText());
            jobs.setTerritory(message.getText());

            //generate Job id
            jobs.setJobId(generateJobId(jobs.getCompanyName()));
            jobs.setIsTerritory(true);

        } else if (jobs.isTerritory() && jobs.getState().equals(State.NONE.toString())) {
            jobs.setTerritory(message.getText());
            addRequirement(chat_id);
            jobs.setIsTerritory(false);

        } else if (jobs.isRequirements() && jobs.getState().equals(State.REQUIREMENT.toString())) {

            //add requirement
            String requirement = message.getText();
            Requirement r = new Requirement();
            r.setName(requirement);
            r.setJob(jobs);
            requirementCache.add(r);

            SendMessage sm = new SendMessage();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Ha");
            row1.add(KeyboardButton.builder().text("Yo'q").build());
            keyboardRow.clear();
            keyboardRow.add(row1);
            markup.setKeyboard(keyboardRow);
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            sm.setText("Davom etishni xohlaysizmi?");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            jobs.setState(State.NONE.toString());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Ha") && jobs.getState().equals(State.NONE.toString())) {
            addRequirement(chat_id);

        } else if (message.getText().equals("Yo'q") && jobs.getState().equals(State.NONE.toString())) {

            markup = new ReplyKeyboardMarkup();
            keyboardRow = new ArrayList<>();

            List<Requirement> byJob = requirementCache.findByJob(jobs);
            StringBuilder sb = new StringBuilder();
            for (Requirement requirement : byJob) {
                sb.append("\n-").append(requirement.getName()).append(":");
            }

            messageSender.sendMessage(SendMessage.builder().text(
                            "\uD83C\uDF93 Id: " + jobs.getJobId() + "\n" +
                                    "\uD83C\uDFE2 Idora: " + jobs.getCompanyName() + "\n" +
                                    "\uD83D\uDCDA Texnologiya: " + jobs.getTechnology() + "\n" +
                                    "\uD83C\uDDFA\uD83C\uDDFF Telegram: @apply11_bot\n" +
                                    "\uD83C\uDF10 Hudud: " + jobs.getTerritory() + "\n" +
                                    "‼️ Qo`shimcha: " + sb)
                    .parseMode("HTML")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());

            SendMessage sm = new SendMessage();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("Ha");
            row1.add(KeyboardButton.builder().text("Yo'q").build());
            keyboardRow.add(row1);
            markup.setKeyboard(keyboardRow);
            markup.setOneTimeKeyboard(true);
            markup.setResizeKeyboard(true);
            sm.setText("Barcha ma'lumotlar to'g'rimi?");
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setReplyMarkup(markup);
            messageSender.sendMessage(sm);
            jobs.setState(State.CHECKED.toString());

        } else if (message.getText().equals("Ha") && jobs.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Arizangiz ko'rib chiqish uchun adminga yuborildi");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            jobs.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            jobs.setIsCompanyName(false);
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Yo'q") && jobs.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Qabul qilinmadi " +
                    "\n/start so`zini bosing. E'lon berish qaytadan boshlanadi");
            sm.setChatId(String.valueOf(message.getChatId()));
            jobs.setState(State.DENIED.toString());
            jobs.setIsCompanyName(false);
            jobs.setIsTechnology(false);
            jobs.setIsTerritory(false);
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
        }
        cache.update(jobs);
    }

    @Override
    public void jobList(Message message, Integer id) {
        List<Jobs> byUser = cache.findByUserId(message.getFrom().getId());
        SendMessage sm = new SendMessage();
        if (byUser.size() == 0) {
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setText("Hozirga sizda ro'yxatdan o'tgan ishlar mavjud emas");
        } else {
            keyboardRow.clear();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("\uD83C\uDFE0️ Bosh sahifa");
            keyboardRow.add(row1);
            for (Jobs job : byUser) {
                KeyboardRow row = new KeyboardRow();
                if (job.getState().equals(State.COMPLETED.toString())) {
                    row.add("Id: " + job.getJobId() + " Idora: " + job.getCompanyName() + " Texnologiya: " + job.getTechnology());
                    keyboardRow.add(row);
                }

            }
            markup.setKeyboard(keyboardRow);
            markup.setResizeKeyboard(true);
            markup.setOneTimeKeyboard(true);
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setText("Ro'yxatdan o'tgan ishlar");
            sm.setReplyMarkup(markup);
        }
        if (jobs.isCompanyName()) {
            jobs.setState(State.DENIED.toString());
            jobs.setIsCompanyName(false);
            jobs.setIsTechnology(false);
            jobs.setIsTerritory(false);
            cache.update(jobs);
        }
        messageSender.sendMessage(sm);
    }

    @Override
    public void applyList(Message message) {
        String text = message.getText().trim();
        String[] all = text.split(" ");
        String s = all[1];
        List<Apply> byJobId = applyCache.findByJobId(s);
        List<Apply> applies = new ArrayList<>();
        for (Apply apply : byJobId) {
            if (apply.getState().equals(State.COMPLETED.toString()))
                applies.add(apply);
        }
        if (applies.size() == 0)
            messageSender.sendMessage(SendMessage.builder()
                    .text("Bu ish bo'yicha hozirda nomzodlar mavjud emas")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());
        else
            for (Apply apply : byJobId) {
                messageSender.sendMessage(SendMessage.builder().text(
                                "\uD83C\uDF93 Id: " + apply.getJobId() + "\n" +
                                        "\uD83D\uDC68\u200D\uD83D\uDCBC Xodim: " + apply.getName() + "\n" +
                                        "\uD83D\uDD51 Yosh: " + apply.getAge() + "\n" +
                                        "\uD83C\uDDFA\uD83C\uDDFF Telegram: @" + apply.getUsername() + "\n" +
                                        "\uD83D\uDCDE Aloqa: " + apply.getPhoneNumber() + "\n" +
                                        "\uD83D\uDD17 CV link: " + "<a href =" + "\"https://api.telegram.org/file/bot" + apply.getToken() + "/" + apply.getFilePath() + "\">" + apply.getJobId() + " " + apply.getName() + " cv" + "</a>")
                        .parseMode("HTML")
                        .chatId(String.valueOf(message.getChatId()))
                        .build());
            }
    }

    @Override
    public void mainPage(Message message, Integer id) {
        jobs = cache.findBy(message.getChatId(), id);
        messageSender.sendMessage(SendMessage.builder()
                .text("Bosh sahifa")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(buttons())
                .build());
        if (jobs.isCompanyName()) {
            jobs.setState(State.DENIED.toString());
            jobs.setIsCompanyName(false);
            jobs.setIsTechnology(false);
            jobs.setIsTerritory(false);
            cache.update(jobs);
        }
    }

    @Override
    public void help(Message message) {
        messageSender.sendMessage(SendMessage.builder()
                .text("Bu bot bir narsa bir narsa")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(buttons())
                .build());
    }

    private String generateJobId(String company) {
        String result;
        String[] companyName = company.trim().split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : companyName) {
            sb.append(s);
        }
        String s = sb.toString();
        Date date = new Date();
        String c1 = s;
        if (s.length() > 3) {
            c1 = s.substring(0, 1) + s.substring(s.length() / 2, s.length() / 2 + 1) + s.substring(s.length() - 1, s.length());
        }
        result = c1.toUpperCase() + String.format("%02d", date.getHours()) + String.format("%02d", date.getSeconds());
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
        keyboardRow.clear();
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
        jobs.setIsRequirements(true);
        jobs.setState(State.REQUIREMENT.toString());

    }
}
