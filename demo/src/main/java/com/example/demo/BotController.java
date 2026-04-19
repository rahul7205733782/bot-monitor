package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class BotController {

    // 👉 Manual trigger (browser)
    @GetMapping("/check-bots")
    public String checkBots() throws InterruptedException {
        runBotMonitoring();
        return "Check completed";
    }

    // 👉 AUTO RUN every 1 hour
    @Scheduled(fixedRate = 3600000) // 1 hour
    public void runEveryHour() throws InterruptedException {
        System.out.println("⏰ Running scheduled bot check...");
        runBotMonitoring();
    }

    // 👉 Main Monitoring Logic
    public void runBotMonitoring() throws InterruptedException {

        List<Client> clients = Arrays.asList(
                new Client("Max Hospital", "https://api.max.com/health"),
                new Client("KIMS", "https://api.kims.com/health"),
                new Client("Apollo", "https://api.apollo.com/health")
                // 👉 Add all 50 clients API URLs here
        );

        List<String> failedClients = new ArrayList<>();

        for (Client client : clients) {

            System.out.println("Checking " + client.getName());

            boolean apiStatus = checkApiHealth(client);

            if (!apiStatus) {
                System.out.println(client.getName() + " API FAILED ❌");
                failedClients.add(client.getName());
            } else {
                System.out.println(client.getName() + " API SUCCESS ✅");
            }

            Thread.sleep(1000); // small delay
        }

        if (!failedClients.isEmpty()) {
            EmailService.sendEmail(failedClients);
        } else {
            System.out.println("✅ All APIs working fine");
        }
    }

    // 👉 API Health Check Method (FREE)
    private boolean checkApiHealth(Client client) {
        try {
            URL url = new URL(client.getApiUrl());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            int responseCode = con.getResponseCode();

            return responseCode == 200;

        } catch (Exception e) {
            return false;
        }
    }
}