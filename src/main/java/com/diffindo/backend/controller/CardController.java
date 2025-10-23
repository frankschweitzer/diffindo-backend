package com.diffindo.backend.controller;

import com.diffindo.backend.dto.*;
import com.diffindo.backend.service.card.CardService;
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

    @PostMapping("/set")
    public ResponseEntity<CardSetResponseDto> approveOrDecline(@RequestBody CardSetRequestDto cardSetRequestDto) {
        logger.info("initiating card set");
        return ResponseEntity.ok(cardService.registerCardOnFile(cardSetRequestDto));
    }

    @GetMapping("/get")
    public ResponseEntity<CardGetResponseDto> fetchAll(@RequestBody CardGetRequestDto cardGetRequestDto) {
        logger.info("initiating fetch of all cards for user");
        return ResponseEntity.ok(cardService.getAllCards(cardGetRequestDto));
    }
}
