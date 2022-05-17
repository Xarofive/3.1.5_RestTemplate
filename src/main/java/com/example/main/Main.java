package com.example.main;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Main {
    public static final String URL = "http://94.198.50.185:7081/api/users";

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        // шаг 1
        ResponseEntity<String> response
                = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
        // шаг 2
        headers.set(HttpHeaders.COOKIE, response.getHeaders().getFirst(HttpHeaders.SET_COOKIE));
        // шаг 3
        User user = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);
        ResponseEntity<String> resultSave
                = restTemplate.exchange(URL, HttpMethod.POST, requestBody, String.class);
        // шаг 4
        user.setName("Thomas");
        user.setLastName("Shelby");
        requestBody = new HttpEntity<>(user, headers);
        ResponseEntity<String> resultUpdate
                = restTemplate.exchange(URL, HttpMethod.PUT, requestBody, String.class);
        // шаг 5
        requestBody = new HttpEntity<>(headers);
        ResponseEntity<String> resultDelete
                = restTemplate.exchange(URL + "/" + user.getId(), HttpMethod.DELETE, requestBody, String.class);
        // результат
        System.out.print(resultSave.getBody());
        System.out.print(resultUpdate.getBody());
        System.out.print(resultDelete.getBody());
    }
}