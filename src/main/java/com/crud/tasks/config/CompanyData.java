package com.crud.tasks.config;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CompanyData {
    @Value("${info.company.name}")
    String companyName;

    @Value("${info.company.email}")
    String companyEmail;

    @Value("${info.company.phone}")
    String companyPhone;
}
