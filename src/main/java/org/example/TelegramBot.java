package org.example;

import org.json.JSONObject;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TelegramBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "weather_77bot";
    }

    @Override
    public String getBotToken() {
        return "5701616394:AAH1O77gAy3n-QBzwAHi9KCb9G0xR4T04IA";
    }

    @Override
    public void onUpdateReceived(Update update) {
//        SendMessage sendMessage = new SendMessage();
//        sendMessage.setChatId(update.getMessage().getChatId());
//        sendMessage.setText("Hello");
//        try {
//            execute(sendMessage);
//        }catch (Exception e){
//            e.getMessage();

        try {
            List<City> list = new ArrayList<>();
            list.add(new City("Минск", "53.893009", "27.567444"));
            list.add(new City("Витебск", "55.187222", "30.205116"));
            list.add(new City("Могилев", "53.910300", "30.350100"));
            list.add(new City("Гродно", "53.669353", "23.813131"));
            list.add(new City("Брест", "52.097622", "23.734051"));
            String lat = "", lon = "";
            for (City elem : list) {
                if (elem.getName().equalsIgnoreCase(update.getMessage().getText())) {
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat="+elem.getLat()+"&lon="+elem.getLon()+"&appid=3b2497ac0ba531282a80e0f0859e105c");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
                    String date = bufferedReader.readLine();
                    JSONObject jsonObject = new JSONObject(date);
                    String main = jsonObject.get("main").toString();
                    jsonObject = new JSONObject(main);
                    String temp = jsonObject.get("temp").toString();

                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setText(temp +"°K");

                    execute(sendMessage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
