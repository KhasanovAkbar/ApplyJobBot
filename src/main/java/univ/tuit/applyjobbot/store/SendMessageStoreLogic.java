package univ.tuit.applyjobbot.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import univ.tuit.applyjobbot.domain.Candidate;
import univ.tuit.applyjobbot.domain.Jobs;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.services.CandidateService;
import univ.tuit.applyjobbot.services.JobService;
import univ.tuit.applyjobbot.services.RequirementService;
import univ.tuit.applyjobbot.services.SendMessageService;
import univ.tuit.applyjobbot.domain.enums.State;
import univ.tuit.applyjobbot.messageSender.MessageSender;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Component
public class SendMessageStoreLogic implements SendMessageService<Message> {

    private final MessageSender messageSender;

    @Autowired
    JobService<Jobs> jobService;

    @Autowired
    RequirementService requirementService;

    @Autowired
    CandidateService<Candidate> candidateService;

    static Jobs job = new Jobs();

    ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
    ArrayList<KeyboardRow> keyboardRow = new ArrayList<>();

    public SendMessageStoreLogic(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    @Override
    public SendMessage start(Message message) {

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText("Assalom alaykum " + message.getFrom().getFirstName() +
                " Xush kelibsiz!\n" +
                "\n" +
                "/help yordam buyrugi orqali nimalarga qodir ekanligimni bilib oling! ");
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setReplyMarkup(buttons());
        messageSender.sendMessage(sendMessage);
        return null;
    }

    @Override
    public SendMessage restart(Message message) {
        //restart bot
        start(message);
        return null;
    }

    @Override
    public SendMessage register(Message message) {
        long chat_id = message.getChatId();
        job = new Jobs();
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
        job.setCompanyName(message.getText());
        job.setIsCompanyName(1);
        jobService.add(job);
        return null;
    }

    @Override
    public SendMessage registerJob(Message message, Integer id) {

        long chat_id = message.getChatId();
        job = jobService.findByUserIdAndId(chat_id, id);
        if (job.getTechnology().equals("Register") && job.getIsCompanyName() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83D\uDCDA Texnologiya:\n" +
                            "\n" +
                            "Talab qilinadigan texnologiyalarni kiriting?\n" +
                            "Texnologiya nomlarini vergul bilan ajrating. Masalan, \n" +
                            "\n" +
                            "Java, C++, C#")
                    .chatId(String.valueOf(chat_id))
                    .build());
            job.setCompanyName(message.getText());
            job.setTechnology(message.getText());
            job.setIsTechnology(1);

        } else if (job.getTerritory().equals("Register") && job.getIsTechnology() == 1) {
            messageSender.sendMessage(SendMessage
                    .builder().text("\uD83C\uDF10 Hudud: \n" +
                            "\n" +
                            "Qaysi hududdansiz?\n" +
                            "Viloyat nomi, Toshkent shahar yoki Respublikani kiriting.")
                    .chatId(String.valueOf(chat_id))
                    .build());
            job.setTechnology(message.getText());
            job.setTerritory(message.getText());

            //generate Job id
            job.setJobId(generateJobId(job.getCompanyName()));
            job.setIsTerritory(1);

        } else if (job.getIsTerritory() == 1 && job.getState().equals(State.NONE.toString())) {
            job.setTerritory(message.getText());
            addRequirement(chat_id);
            job.setIsTerritory(0);

        } else if (job.getIsRequirements() == 1 && job.getState().equals(State.REQUIREMENT.toString())) {

            //add requirement
            String requirement = message.getText();
            Requirement r = new Requirement();
            r.setName(requirement);
            r.setJob(job);
            requirementService.add(r);

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
            job.setState(State.NONE.toString());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Ha") && job.getState().equals(State.NONE.toString())) {
            addRequirement(chat_id);

        } else if (message.getText().equals("Yo'q") && job.getState().equals(State.NONE.toString())) {

            markup = new ReplyKeyboardMarkup();
            keyboardRow = new ArrayList<>();

            List<Requirement> byJob = requirementService.findByJob(job);
            StringBuilder sb = new StringBuilder();
            for (Requirement requirement : byJob) {
                sb.append("\n-").append(requirement.getName()).append(":");
            }

            messageSender.sendMessage(SendMessage.builder().text(
                            "\uD83C\uDF93 Id: " + job.getJobId() + "\n" +
                                    "\uD83C\uDFE2 Idora: " + job.getCompanyName() + "\n" +
                                    "\uD83D\uDCDA Texnologiya: " + job.getTechnology() + "\n" +
                                    "\uD83C\uDDFA\uD83C\uDDFF Telegram: @apply11_bot\n" +
                                    "\uD83C\uDF10 Hudud: " + job.getTerritory() + "\n" +
                                    "?????? Qo`shimcha: " + sb)
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
            job.setState(State.CHECKED.toString());

        } else if (message.getText().equals("Ha") && job.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Arizangiz ko'rib chiqish uchun adminga yuborildi");
            sm.setParseMode("HTML");
            sm.setChatId(String.valueOf(message.getChatId()));
            job.setState(State.COMPLETED.toString());
            keyboardRow.clear();
            job.setIsCompanyName(0);
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);

        } else if (message.getText().equals("Yo'q") && job.getState().equals(State.CHECKED.toString())) {
            SendMessage sm = new SendMessage();
            sm.setText("Qabul qilinmadi " +
                    "\n/start so`zini bosing. E'lon berish qaytadan boshlanadi");
            sm.setChatId(String.valueOf(message.getChatId()));
            job.setState(State.DENIED.toString());
            job.setIsCompanyName(0);
            job.setIsTechnology(0);
            job.setIsTerritory(0);
            job.setIsRequirements(0);
            keyboardRow.clear();
            sm.setReplyMarkup(buttons());
            messageSender.sendMessage(sm);
        }
        jobService.update(job);
        return null;
    }

    @Override
    public SendMessage jobList(Message message, Integer id) {
        List<Jobs> byUser = jobService.findByUserId(message.getFrom().getId());
        job = jobService.findByUserIdAndId(message.getFrom().getId(), id);
        SendMessage sm = new SendMessage();
        if (byUser.size() == 0) {
            sm.setChatId(String.valueOf(message.getChatId()));
            sm.setText("Hozirga sizda ro'yxatdan o'tgan ishlar mavjud emas");
        } else {
            keyboardRow.clear();
            KeyboardRow row1 = new KeyboardRow();
            row1.add("\uD83C\uDFE0??? Bosh sahifa");
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
        if (job.getIsCompanyName() == 1) {
            job.setState(State.DENIED.toString());
            job.setIsCompanyName(0);
            job.setIsTechnology(0);
            job.setIsTerritory(0);
            job.setIsRequirements(0);
            jobService.update(job);
        }
        messageSender.sendMessage(sm);
        return null;
    }

    @Override
    public SendMessage applyList(Message message) {
        String text = message.getText().trim();
        String[] all = text.split(" ");
        String s = all[1];
        List<Candidate> byJobId = candidateService.findByJobId(s);
        List<Candidate> applies = new ArrayList<>();
        for (Candidate candidate : byJobId) {
            if (candidate.getState().equals(State.COMPLETED.toString()))
                applies.add(candidate);
        }
        if (applies.size() == 0)
            messageSender.sendMessage(SendMessage.builder()
                    .text("Bu ish bo'yicha hozirda nomzodlar mavjud emas")
                    .chatId(String.valueOf(message.getChatId()))
                    .build());
        else
            for (Candidate candidate : applies) {
                messageSender.sendMessage(SendMessage.builder().text(
                                "\uD83C\uDF93 Id: " + candidate.getJobId() + "\n" +
                                        "\uD83D\uDC68\u200D\uD83D\uDCBC Xodim: " + candidate.getName() + "\n" +
                                        "\uD83D\uDD51 Yosh: " + candidate.getAge() + "\n" +
                                        "\uD83C\uDDFA\uD83C\uDDFF Telegram: @" + candidate.getUsername() + "\n" +
                                        "\uD83D\uDCDE Aloqa: " + candidate.getPhoneNumber() + "\n" +
                                        "\uD83D\uDD17 CV link: " + "<a href =" + "\"https://api.telegram.org/file/bot" + candidate.getToken() + "/" + candidate.getFilePath() + "\">" + candidate.getJobId() + " " + candidate.getName() + " cv" + "</a>")
                        .parseMode("HTML")
                        .chatId(String.valueOf(message.getChatId()))
                        .build());
            }
        return null;
    }

    @Override
    public SendMessage mainPage(Message message, Integer id) {
        job = jobService.findByUserIdAndId(message.getChatId(), id);
        if (job.getIsCompanyName() == 1) {
            job.setState(State.DENIED.toString());
            job.setIsCompanyName(0);
            job.setIsTechnology(0);
            job.setIsTerritory(0);
            job.setIsRequirements(0);
            jobService.update(job);
        }
        return SendMessage.builder()
                .text("Bosh sahifa")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(buttons())
                .build();
    }

    @Override
    public SendMessage help(Message message) {
        return SendMessage.builder()
                .text("Bu bot bir narsa bir narsa")
                .chatId(String.valueOf(message.getChatId()))
                .replyMarkup(buttons())
                .build();
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

        job.setUserId(user_id);
        job.setUsername(username);
        job.setState(State.NONE.toString());
        job.setRegistrationTime(format);
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
        job.setIsRequirements(1);
        job.setState(State.REQUIREMENT.toString());

    }
}
