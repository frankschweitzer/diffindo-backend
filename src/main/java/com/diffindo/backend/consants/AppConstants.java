package com.diffindo.backend.consants;

import org.springframework.stereotype.Component;

@Component
public class AppConstants {
    public final String GROUP_PAYMENT_STATUS_PENDING = "PENDING";
    public final String GROUP_PAYMENT_STATUS_APPROVED = "APPROVED";
    public final String GROUP_PAYMENT_STATUS_DECLINED = "DECLINED";

    public final String INDIVIDUAL_PAYMENT_STATUS_PENDING = "PENDING";
    public final String INDIVIDUAL_PAYMENT_STATUS_APPROVED = "APPROVED";
    public final String INDIVIDUAL_PAYMENT_STATUS_DECLINED = "DECLINED";
}
