package com.techelevator.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Component
public class TaxRateClient {
    RestTemplate restTemplate;

    public BigDecimal getTaxRate(String stateCode){
        //try
        ResponseEntity<String> response = restTemplate.getForEntity("https://teapi.netlify.app/api/statetax?state=" + stateCode, String.class);
        return new BigDecimal(response.getBody()).divide(new BigDecimal("100"));
    }

}
