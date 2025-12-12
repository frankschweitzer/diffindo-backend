package com.diffindo.backend.service.stripe;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {

    public String setCardOnFile() {
        /**
         * TODO
         *
         * allow users to get a card on file
         */
        return "token";
    }

    public void placeHoldOnCard() {
        /**
         * TODO
         *
         * this function will take in the amount and userId then charge their card on file from the CARDS table
         *
         * in PAYMENTS table consider an intermediary stage between pending + approved like user_approved then it goes to payment_success
         */
    }

    public void initiatePayment() {
        /**
         * TODO
         *
         * similar to above this will take in a GroupId and will iterate through all the users in that group and
         * execute on the hold that was placed on their card
         *
         * again, be careful with the DB status field
         */
    }
}
