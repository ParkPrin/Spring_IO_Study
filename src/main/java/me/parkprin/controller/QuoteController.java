package me.parkprin.controller;

import me.parkprin.domain.Quote;
import me.parkprin.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;

@RestController
public class QuoteController {

    @Autowired
    QuoteService quoteService;

    @GetMapping("/quote")
    public Quote quoteGetRestApiCall(@PathParam(value = "type")String type){
        return quoteService.getQuoteAfterUpdateTask(type);
    }
}
