package com.diffindo.backend.controller;

import com.diffindo.backend.dto.*;
import com.diffindo.backend.exceptions.BadTokenException;
import com.diffindo.backend.service.card.CardService;
import com.diffindo.backend.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card")
public class CardController {

    private static final Logger logger = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;
    private final JwtUtil jwtUtil;

    @PostMapping("/set")
    public ResponseEntity<CardSetResponseDto> setCardOnFile(
            @RequestBody CardSetRequestDto cardSetRequestDto,
            @RequestHeader("Authorization") String authToken
    ) {
        logger.info("extracting username from token");
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length != 2) {
            logger.info("invalid token when requesting to fetch all payments");
            throw new BadTokenException("bad authentication token");
        }
        String username = jwtUtil.extractUsername(tokenParts[1]);

        logger.info("initiating card set");
        return ResponseEntity.ok(cardService.registerCardOnFile(cardSetRequestDto, username));
    }

    @GetMapping("/get")
    public ResponseEntity<CardGetResponseDto> fetchAll(
            @RequestHeader("Authorization") String authToken
    ) {
        logger.info("extracting username from token");
        String[] tokenParts = authToken.split(" ");
        if (tokenParts.length != 2) {
            logger.info("invalid token when requesting to fetch all payments");
            throw new BadTokenException("bad authentication token");
        }
        String username = jwtUtil.extractUsername(tokenParts[1]);

        logger.info("initiating fetch of all cards for user");
        return ResponseEntity.ok(cardService.getAllCards(username));
    }
}
