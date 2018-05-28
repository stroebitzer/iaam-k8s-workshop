package com.smec.k8s;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
@Slf4j
public class Application {

    private static final int LAG_SECONDS = 10;
    private boolean live = true;
    private boolean ready = true;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PreDestroy
    private void tearDown() throws InterruptedException {
        log.info("Shutting down");
        for (int i = 0; i < LAG_SECONDS; ++i) {
            Thread.sleep(1000);
            log.info("Shutting down takes {} seconds", i);
        }
        log.info("Shut down");
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<String> root() throws UnknownHostException {
        String hostName = InetAddress.getLocalHost().getHostName();
        String hostIp = InetAddress.getLocalHost().getHostAddress();
        return ResponseEntity.ok("Response from " + hostName + " - " + hostIp + ": live = " + live + ", ready = " + ready);
    }

    @RequestMapping(value = "/live", method = RequestMethod.GET)
    public ResponseEntity<String> live() {
        log.info("live request, is live {}", live);
        if (live) {
            return ResponseEntity.ok("live = " + live);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("live = " + live);
    }

    @RequestMapping(value = "/set_live/{isLive}", method = RequestMethod.GET)
    public ResponseEntity<String> setLive(@PathVariable boolean isLive) {
        log.info("set live request {}", isLive);
        this.live = isLive;
        return ResponseEntity.ok("set live to " + live);
    }

    @RequestMapping(value = "/ready", method = RequestMethod.GET)
    public ResponseEntity<String> ready() {
        log.info("ready request, is ready {}", ready);
        if (live && ready) {
            return ResponseEntity.ok("live = " + live + ", ready = " + ready);
        }
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("live = " + live + ", ready = " + ready);
    }

    @RequestMapping(value = "/set_ready/{isReady}", method = RequestMethod.GET)
    public ResponseEntity<String> setReady(@PathVariable boolean isReady) {
        log.info("set ready request {}", isReady);
        this.ready = isReady;
        return ResponseEntity.ok("set ready to " + ready);
    }

}
