package com.smec.k8s;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

    private static final String FILE_PATH = "/data/file.txt";
    private static final RestTemplate REST_TEMPLATE = new RestTemplate();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void init() throws IOException {
        Paths.get(FILE_PATH).toFile().getParentFile().mkdirs();
        Paths.get(FILE_PATH).toFile().createNewFile();
    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public ResponseEntity<String> write(@RequestParam String content) throws IOException {
        Files.write(Paths.get(FILE_PATH), ("app1: " + content + "\n").getBytes(), StandardOpenOption.APPEND);
        return ResponseEntity.ok("Content got written");
    }

    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public ResponseEntity<List<String>> write() throws IOException {
        List<String> content = Files.readAllLines(Paths.get(FILE_PATH));
        return ResponseEntity.ok(content);
    }

    @RequestMapping(value = "/call", method = RequestMethod.GET)
    public ResponseEntity<String> call() {
        String response = REST_TEMPLATE.getForObject("http://localhost:8081/", String.class);
        return ResponseEntity.ok("app1 got the following from app2: " + response);
    }

}
