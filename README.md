# feign-client-to-call-external-service

Feign
The Feign is a declarative web service (HTTP client) developed by Netflix.
The developers can use declarative annotations to call the REST services instead of writing representative boilerplate code.
Feign is a component that solves this problem. Feign makes it easy to invoke other microservices.
The other additional thing that Feign provides is:  it integrates with the Ribbon

Step 1: Select currency-conversion-service project.

Step 2: Open the pom.xml and add the Feign dependency. Feign inherits from the Netflix.
<dependency>  
<groupId>org.springframework.cloud</groupId>    
<artifactId>spring-cloud-starter-feign</artifactId>  
<version>1.4.4.RELEASE</version>  
</dependency> 

Step 3:@EnableFeignClients in the CurrencyConversionServiceApplication.java file.
Step 4: Define an attribute in the @EnableFeignClients annotation. The attribute is the name of the package that we want to scan.

package com.thbs.bt;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
@EnableFeignClients("com.thbs.bt") 
public class CurrencyConversionServiceApplication 
{
public static void main(String[] args) 
{
SpringApplication.run(CurrencyConversionServiceApplication.class, args);
}
}


Step 5: Create a Feign proxy that enables us to talk to external microservices. Let’s create an interface with the name CurrencyExchangeServiceProxy.
Step 6: Add an annotation @FeignClient. Pass the attributes name and URL.

package com.thbs.bt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="currency-exchange-service",url="localhost:8000")
public interface CurrencyExchangeServiceProxy {
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")		//where {from} and {to} are path variable
	public CurrencyConversionBean retrieveExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);

}

Step 7:define a method that talks to the currency-exchange-controller. Open the Currency-ConverterController.java file.

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

Step 8: First, run the currency-exchange-service by invoking the URL http://localhost:8000/currency-exchange/from/USD/to/INR
Response:{"id":10001,"from":"USD","to":"INR","conversionMultiple":65.00,"port":8000}

Step 9: Execute the feign service by using the URL http://localhost:8100/currency-converter-feign/from/USD/to/INR/quantity/1000.
Response:
{"id":10001,"from":"USD","to":"INR","quantity":1000,"totalCalculatedAmount":65000.00,"port":8000,"conversionMultiple":65.00}




