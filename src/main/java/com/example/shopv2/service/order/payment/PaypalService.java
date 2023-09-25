package com.example.shopv2.service.order.payment;

import com.example.shopv2.model.Orders;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class PaypalService {

    @Value("${paypal.mode}")
    private String BASE;
    @Value("${paypal.client.id}")
    private String CLIENT_ID;
    @Value("${paypal.client.secret}")
    private String APP_SECRET;
    @Autowired
    private RestTemplate restTemplate;
    private final static Logger LOGGER =  Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    private String getAuth(String client_id, String app_secret) {
        String auth = client_id + ":" + app_secret;
        return Base64.getEncoder().encodeToString(auth.getBytes());
    }

    private HttpHeaders paymentAccessTokenHeader(String auth){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("Authorization", "Basic " + auth);
        return headers;
    }

    public String generateAccessToken() {

        String auth = this.getAuth(CLIENT_ID, APP_SECRET);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        HttpEntity<?> request = new HttpEntity<>(requestBody, paymentAccessTokenHeader(auth));
        requestBody.add("grant_type", "client_credentials");
        ResponseEntity<String> response = restTemplate.postForEntity(
                BASE +"/v1/oauth2/token",
                request,
                String.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            LOGGER.log(Level.INFO, "GET TOKEN: SUCCESSFUL!");
            return new JSONObject(response.getBody()).getString("access_token");
        } else {
            LOGGER.log(Level.SEVERE, "GET TOKEN: FAILED!");
            return "Unavailable to get ACCESS TOKEN, STATUS CODE " + response.getStatusCode();
        }
    }

    public HttpHeaders payPalHeader(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        headers.add("Content-Type", "application/json");
        headers.add("Accept", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    //Zamówienie jest umową pomiędzy kupującym a sprzedającym.
    //Upoważnia sprzedającego do obciążenia konta PayPal kupującego z tytułu zakupu.

    //metoda ustawia tranzakcję kiedy przycisk PayPal zostaje kliknięty
    public Object createOrder() {
        String accessToken = generateAccessToken();
        Double d = 1.0;
        String requestJson = "{\"intent\":\"CAPTURE\",\"purchase_units\":[{\"amount\":{\"currency_code\":\"PLN\",\"value\":\"" + d + "\"}}]}";

        HttpEntity<String> entity = new HttpEntity<String>(requestJson, payPalHeader(accessToken));

        ResponseEntity<Object> response = restTemplate.exchange(
                BASE + "/v2/checkout/orders",
                HttpMethod.POST,
                entity,
                Object.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "ORDER CREATED");
            return response.getBody();
        } else {
            LOGGER.log(Level.INFO, "FAILED CAPTURING ORDER");
            return "Unavailable to CREATE ORDER, STATUS CODE " + response.getStatusCode();
        }
    }

    //finalizacja tranzakcji po potwierdzeniu płatności (wywołania metody createOrder())
    public Object capturePayment(String orderId) {
        String accessToken = generateAccessToken();

        HttpEntity<String> entity = new HttpEntity<String>(null, payPalHeader(accessToken));

        ResponseEntity<Object> response = restTemplate.exchange(
                BASE + "/v2/checkout/orders/" + orderId + "/capture",
                HttpMethod.POST,
                entity,
                Object.class
        );

        if (response.getStatusCode() == HttpStatus.CREATED) {
            LOGGER.log(Level.INFO, "PAYMENT FINALIZE");
            return response.getBody();
        } else {
            LOGGER.log(Level.INFO, "FAILED TO FINALIZE PAYMENT");
            return "Unavailable to FINALIZE PAYMENT, STATUS CODE " + response.getStatusCode();
        }
    }
}
