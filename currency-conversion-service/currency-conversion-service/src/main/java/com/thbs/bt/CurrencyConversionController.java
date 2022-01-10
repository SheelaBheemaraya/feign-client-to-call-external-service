package com.thbs.bt;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
@RestController
public class CurrencyConversionController 
{
	
	@Autowired
	private CurrencyExchangeServiceProxy proxy;
	@GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}") 
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity)
	{
		
CurrencyConversionBean response=proxy.retrieveExchangeValue(from, to);

	//creating a new response bean and getting the response back and taking it into Bean
	return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	
	
	
}

}