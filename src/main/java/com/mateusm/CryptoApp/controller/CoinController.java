package com.mateusm.CryptoApp.controller;

import com.mateusm.CryptoApp.entity.CoinEntity;
import com.mateusm.CryptoApp.repository.CoinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@RestController
@RequestMapping("/v1/crypto/coin")
public class CoinController {

    @Autowired
    private CoinRepository coinRepository;

    @Bean
    public void init(){
        CoinEntity bitCoin = new CoinEntity();
        bitCoin.setName("bitcoin");;
        bitCoin.setPrice(new BigDecimal(12456.487));
        bitCoin.setQuantity(new BigDecimal(0.000015));
        bitCoin.setDatetime(new Timestamp(System.currentTimeMillis()));
        coinRepository.insert(bitCoin);

        CoinEntity mamanoelCoin = new CoinEntity();
        mamanoelCoin.setName("mamanoelCoin");;
        mamanoelCoin.setPrice(new BigDecimal(1.4547));
        mamanoelCoin.setQuantity(new BigDecimal(134));
        mamanoelCoin.setDatetime(new Timestamp(System.currentTimeMillis()));
        coinRepository.insert(mamanoelCoin);

        CoinEntity pedrinCoin = new CoinEntity();
        pedrinCoin.setName("pedrinCoin");;
        pedrinCoin.setPrice(new BigDecimal(15.87));
        pedrinCoin.setQuantity(new BigDecimal(14));
        pedrinCoin.setDatetime(new Timestamp(System.currentTimeMillis()));
        coinRepository.insert(pedrinCoin);
    }

    @PostMapping()
    public ResponseEntity create(@RequestBody CoinEntity coin) {
        try {
            coin.setDatetime(new Timestamp(System.currentTimeMillis()));
            return new ResponseEntity<>(coinRepository.insert(coin), HttpStatus.CREATED);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return new ResponseEntity<>(coinRepository.getAll(), HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity getByName(@PathVariable String name) {
        try {
            return new ResponseEntity<>(coinRepository.getByName(name), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable int id) {
        try {
            return new ResponseEntity<>(coinRepository.getById(id), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        try {
            return new ResponseEntity<>(coinRepository.delete(id), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity update(@RequestBody CoinEntity coin) {
        try {
            return new ResponseEntity<>(coinRepository.update(coin), HttpStatus.OK);
        } catch (Exception error) {
            return new ResponseEntity<>(error.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
