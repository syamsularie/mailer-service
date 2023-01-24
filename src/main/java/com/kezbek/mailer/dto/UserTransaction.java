package com.kezbek.mailer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTransaction {
    private String email;
    private String phone;
    private BigDecimal cashbackTransaction;
    private BigDecimal cashbackLoyalty;
    private BigDecimal totalCashback;
}
