package com.smec.k8s;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Vector;

@SpringBootApplication
@RestController
@Slf4j
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void init() {
        log.info("Total memory in MB: {}", Runtime.getRuntime().totalMemory() / 1024 / 1024);
        log.info("Max memory in MB: {}", Runtime.getRuntime().maxMemory() / 1024 / 1024);
        long allocatedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
        long freeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
        log.info("Allocated init memory in MB: {}", allocatedMemory / 1024 / 1024);
        log.info("Free init memory in MB: {}", freeMemory / 1024 / 1024);
        log.info("Available init processors: {}", Runtime.getRuntime().availableProcessors());
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> root() {
        Vector vector = new Vector();
        while (true) {
            byte bytes[] = new byte[1048576];
            vector.add(bytes);
            long allocatedMemory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
            long freeMemory = Runtime.getRuntime().maxMemory() - allocatedMemory;
            log.info("Allocated memory in MB: {}", allocatedMemory / 1024 / 1024);
            log.info("Free memory in MB: {}", freeMemory / 1024 / 1024);
        }
    }

}
