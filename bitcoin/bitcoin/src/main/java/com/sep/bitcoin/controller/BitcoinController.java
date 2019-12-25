package com.sep.bitcoin.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.sep.bitcoin.model.Bitcoin;
import com.sep.bitcoin.model.Order;
import com.sep.bitcoin.model.OrderBitcoin;
import com.sep.bitcoin.model.OrderBitcoinResponse;
import com.sep.bitcoin.service.OrderService;

@RequestMapping("/")
@RestController
public class BitcoinController {

	@Autowired
	private OrderService orderService;
	
	/*@RequestMapping(value = "/paymentType")
	public String getType(@RequestBody Bitcoin bitcoin) {

		String pgr = createOrderBitcoin(createOrder(bitcoin));
		String split[] = pgr.split(",");
		String status = "\"" + split[0] + "\"";

		return status;

	}*/
	
	
	public Order createOrder() {
		Order order = new Order();
		order.setBuyerEmail("dasdas@gmail.com");
		order.setBuyerFirstName("Petar");
		order.setBuyerLastName("Petrovic");
		order.setCurrency("ddd");
		order.setExecuted(false);
		//order.setMagazine(magazineService.findById(b.getMerchantId()));
		order.setMerchandise("dasdas");
		order.setMerchantId("1");
		//order.setMerchantTimestamp(merchantTimestamp); kreiranje vremena
		order.setProductId((long) 222);
		order.setPayerId("dasdasdasd");
		order.setPrice(200.00);
		order.setQuantity(1);
		order.setStatus("new");
		order.setProductType("dasdasd");
		
		return order;
		
	}
	
	@GetMapping(value = "/paymentType")
	public String createOrderBitcoin() {
		Order o = createOrder();
		
		//Double ex = CurrencyConverter.excangeRate(o.getCurrency(), "EUR");

		//System.out.println("\n ***** " + ex + " CREATE ORDER:  " + "\n");
		OrderBitcoin order = new OrderBitcoin();
		order.setTitle(o.getMerchandise());
		order.setMerchantId(o.getMerchantId());
		//order.setPrice_amount(o.getPrice()*ex); // pazi na cenu
		order.setPrice_amount(o.getPrice());
		order.setPrice_currency("EUR");
		order.setReceive_currency("USD");
		order.setCancel_url("https://localhost:8672/bitcoin/cancel");
		order.setSuccess_url("https://localhost:8672/bitcoin/success");
		//order.setToken(magazineService.findTokenForBitcoin(o.getMagazine()));
		order.setToken("1dNZuJA8pU6TrDKa-zTEsDcyNJtsJGzSbip6UHSA"); // Ovde ide od magazina token
		
		String url = "https://api-sandbox.coingate.com/v2/orders";

		// set up the basic authentication header
		//String authorizationHeader = "Bearer Q-smRAh_a6nF-NVXJarEt48YyHtNag1iX-__bZwx";
		String authorizationHeader = "Bearer "+ order.getToken();
		
		// setting up the request headers
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(MediaType.APPLICATION_JSON);
		requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		requestHeaders.add("Authorization", authorizationHeader);

		// setting up the request body order request entity is created with request body
		// and headers
		HttpEntity<OrderBitcoin> requestEntity = new HttpEntity<OrderBitcoin>(order, requestHeaders);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<OrderBitcoinResponse> responseEntity = rt.exchange(url, HttpMethod.POST, requestEntity,
				OrderBitcoinResponse.class);

		OrderBitcoinResponse responseOrder = null;
		if (responseEntity.getStatusCode() == HttpStatus.OK) {
			responseOrder = responseEntity.getBody();
			System.out.println(Integer.valueOf(responseEntity.getBody().getId()));
			//o.setMerchantTimestamp(Timestamp.parse(responseEntity.getBody().getCreated_at())); // parse timestamp
			o.setMerchantOrderId(1);
			//o.setMerchantPassword(magazineService.findMerchantPassword(o.getMagazine()));
			o.setIdBitcoin(Integer.parseInt(responseEntity.getBody().getId()));
			//orderService.createOrderBitcoin(o);
			return responseOrder.getPayment_url() + "," + Integer.valueOf(responseEntity.getBody().getId());
		}

		return responseEntity.getStatusCode().toString();
	}

	public String cancelOrder(Order o) {
		return null;
	}

	public String executeOrder(Order order) {
		return null;
	}
}
