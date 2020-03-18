package com.taltech.stockscreenerapplication.controller;


import com.taltech.stockscreenerapplication.model.Ticker;
import com.taltech.stockscreenerapplication.repository.TickerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tickers")
public class TickerController {

    @Autowired
    TickerRepository tickerRepository;

    @GetMapping
    public Iterable<Ticker> getTickers() {

        return tickerRepository.findAll();
    }
}
