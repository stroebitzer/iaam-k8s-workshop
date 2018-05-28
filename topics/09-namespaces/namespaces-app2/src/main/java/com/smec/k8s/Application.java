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
        String url = "http://namespaces-app1.default:8080/";
        String response = REST_TEMPLATE.getForObject(url, String.class);
        return ResponseEntity.ok("Cerberus got the following from Odysseus: " + response);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> root() {
        return ResponseEntity.ok("Hello I am Cerberus, I live in Hades");
    }

}
