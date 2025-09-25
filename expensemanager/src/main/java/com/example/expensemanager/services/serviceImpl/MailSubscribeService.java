package com.example.expensemanager.services.serviceImpl;


import com.example.expensemanager.entity.ExpenseStatistic;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MailSubscribeService {

    @Autowired
    private ExpenseStatsServiceImplementation expenseStatsServiceImplementation;

    @Autowired
    private EmailService emailService;

    @Autowired
    UserServiceImplementation userServiceImplementation;


    @Scheduled(cron = "0 0 10 1 * ?")
    public void sendMailToSubscribeUser(){

        List<User> userList = userServiceImplementation.getAllUser();
        log.info("users fetched for sending mail");
        for (User user : userList){
            if (user.getIsSubscribeToMail().booleanValue()) {
                List<ExpenseStatistic> results = expenseStatsServiceImplementation.getExpenseStatsByUserIdForMonth(user.getUserId());
                String htmlContent = emailService.buildExpenseHtml(results);

                emailService.sendEmail(
                        user.getEmail(),
                        "Your Monthly Expense Report",
                        htmlContent
                );
                log.info("Email send Successfully to {}" ,user.getEmail());
            }
        }




    }
}
