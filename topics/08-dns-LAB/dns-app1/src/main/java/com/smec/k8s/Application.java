package com.smec.k8s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class Application {

    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public ResponseEntity<String> call() {
        // TODO 08-01 Define the url of the endpoint "/" running on port 8080 running on the Service "dns-app2"
        String url = "";
        String response = REST_TEMPLATE.getForObject(url, String.class);
        return ResponseEntity.ok("app1 got the following from app2: " + response);
    }

}
