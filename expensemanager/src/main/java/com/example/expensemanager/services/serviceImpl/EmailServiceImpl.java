package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.entity.ExpenseStatistic;
import com.example.expensemanager.services.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ExpenseStatsServiceImplementation expenseStatsServiceImplementation;

    @Value("${spring.mail.username}")
    private String fromEmail;


    @Override
    public void sendEmail(String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = enable HTML
            javaMailSender.send(message);
            log.info("Message Sent Successfully to: {}", toEmail);
        }
        catch (Exception e) {
            log.error("sendEmail() | Error : {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }



    public String buildExpenseHtml(List<ExpenseStatistic> results) {
        StringBuilder rows = new StringBuilder();

        for (ExpenseStatistic expense : results) {
            String category = expense.getCategory();
            BigDecimal amount = expense.getAmount();

            rows.append("<tr>")
                    .append("<td>").append(category).append("</td>")
                    .append("<td>").append(amount).append("</td>")
                    .append("</tr>");
        }

        String template = // load from file or inline string (shown above)
                """
                <!DOCTYPE html>
                <html>
                <head>
                <style>      body {
                                           font-family: Arial, sans-serif;
                                           background: #f9f9f9;
                                           padding: 20px;
                                       }
                                       .container {
                                           background: white;
                                           border-radius: 12px;
                                           padding: 20px;
                                           max-width: 600px;
                                           margin: auto;
                                           box-shadow: 0 4px 8px rgba(0,0,0,0.1);
                                       }
                                       h2 {
                                           text-align: center;
                                           color: #4CAF50;
                                       }
                                       table {
                                           width: 100%;
                                           border-collapse: collapse;
                                           margin-top: 15px;
                                       }
                                       th, td {
                                           padding: 12px;
                                           text-align: left;
                                       }
                                       th {
                                           background: #4CAF50;
                                           color: white;
                                       }
                                       tr:nth-child(even) {
                                           background: #f2f2f2;
                                       }
                                       .footer {
                                           margin-top: 20px;
                                           text-align: center;
                                           font-size: 13px;
                                           color: #777;
                                       } </style>
                </head>
                <body>
                <div class="container">
                    <h2>Monthly Expense Report</h2>
                    <table>
                        <tr><th>Category</th><th>Amount</th></tr>
                        {{rows}}
                    </table>
                </div>
                </body>
                </html>
                """;

        return template.replace("{{rows}}", rows.toString());
    }

}
