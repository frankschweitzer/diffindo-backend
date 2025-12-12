package com.diffindo.backend.service.card;

import com.diffindo.backend.consants.AppConstants;
import com.diffindo.backend.dto.*;
import com.diffindo.backend.exceptions.UserNotFoundException;
import com.diffindo.backend.model.Card;
import com.diffindo.backend.model.User;
import com.diffindo.backend.repository.CardRepository;
import com.diffindo.backend.repository.UserRepository;
import com.diffindo.backend.service.stripe.StripeService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {

    private static final Logger logger = LoggerFactory.getLogger(CardService.class);

    private final AppConstants APP_CONSTANTS;
    private final CardRepository cardRepository;
    private final StripeService stripeService;
    private final UserRepository userRepository;

    public CardSetResponseDto registerCardOnFile(CardSetRequestDto cardSetRequestDto, String username) {
        // obtain userId from username
        Optional<User> requestingUser = userRepository.findByEmail(username);
        if (requestingUser.isEmpty()) {
            logger.info("userId could not be resolved from token");
            throw new UserNotFoundException("userId could not be resolved from token");
        }
        Long userId = requestingUser.get().getUserId();

        logger.info("initiating card registration");
        String tokenizedCard = stripeService.setCardOnFile();
        logger.info("card registration complete");
        var card = Card.builder()
                .tokenizedCard(tokenizedCard)
                .userId(userId)
                .build();
        cardRepository.save(card);
        logger.info("tokenized card saved to database");
        return CardSetResponseDto.builder()
                .cardId(card.getCardId())
                .build();
    }

    public CardGetResponseDto getAllCards(String username) {
        // obtain userId from username
        Optional<User> requestingUser = userRepository.findByEmail(username);
        if (requestingUser.isEmpty()) {
            logger.info("userId could not be resolved from token");
            throw new UserNotFoundException("userId could not be resolved from token");
        }
        Long userId = requestingUser.get().getUserId();

        logger.info("initiating fetch of all users cards on file");
        Optional<List<Card>> cards = cardRepository.findAllByUserId(userId);
        if (cards.isEmpty()) {
            return CardGetResponseDto.builder()
                    .build();
        }

        logger.info("cards for userId found");
        return CardGetResponseDto.builder()
                .cards(cards.get())
                .build();
    }

}
