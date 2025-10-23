package com.diffindo.backend.service.card;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.dto.CardGetRequestDto;
import com.diffindo.backend.dto.CardGetResponseDto;
import com.diffindo.backend.dto.CardSetRequestDto;
import com.diffindo.backend.dto.CardSetResponseDto;
import com.diffindo.backend.repository.CardRepository;
import com.diffindo.backend.service.stripe.StripeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    private final AppConstants APP_CONSTANTS;
    private final CardRepository cardRepository;
    private final StripeService stripeService;

    public CardSetResponseDto registerCardOnFile(CardSetRequestDto cardSetRequestDto) {
        stripeService.setCardOnFile();
        return null;
    }

    public CardGetResponseDto getAllCards(CardGetRequestDto cardGetRequestDto) {
        return null;
    }

}
