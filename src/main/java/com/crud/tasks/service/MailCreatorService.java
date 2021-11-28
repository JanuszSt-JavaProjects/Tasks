package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.config.CompanyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;
    @Autowired
    private CompanyData companyData;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://januszst-javaprojects.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("GOODBYE_MESSAGE", "Have a nice day!");
        context.setVariable("COMPANY_NAME", companyData.getCompanyName());
        context.setVariable("COMPANY_EMAIL", companyData.getCompanyEmail());
        context.setVariable("COMPANY_PHONE", companyData.getCompanyPhone());
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
