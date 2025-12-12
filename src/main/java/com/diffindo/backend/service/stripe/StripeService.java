package com.diffindo.backend.service.stripe;

import com.diffindo.backend.dto.CardSetResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeService {

    public String setCardOnFile() {
        /**
         * TODO: allow users to get a card on file
         *
         * 1) verify if the user has a stripeCustomerId in the DB
         * 2) if not then create a customer and update the customer in the DB
         * 3) create SetUpIntent via Stripe
         * 4) return the clientSecret & customerId to user
         *         --> they will interface with Stripe directly on the Mobile App to insert card
         *
         */
        return null;
    }

    public void placeHoldOnCard() {
        /**
         * TODO: place a hold on users card when they APPROVE their share of the purchase
         *
         * 1) retrieve stripeCustomerId from USERS table using the userId
         * 2) retrieve paymentMethodId from CARDS table using customerId --> both userId & customerId are unique
         * 3) create PaymentIntentParams using _CaptureMethod.MANUAL_ --> very important as this authorizes and not charges
         * 4) if PaymentIntent creation is successful we get a paymentIntentId to store in PAYMENTS table
         *
         * in PAYMENTS table consider an intermediary stage between pending + approved like user_approved then it goes to payment_success
         */
    }

    public void initiatePayment() {
        /**
         * TODO: capture hold on funds for all members of a group to complete their purchase
         *
         * 1) iterate through all of the payments in PAYMENTS table for given groupId
         * 2) paymentIntentId field should be populated for each record since they approved already
         * 3) capture payment using the paymentIntentId
         * 4) once all payments are captured, shift funds to merchant via their Connected Account
         *
         * again, be careful with the DB status field
         */
    }
}
