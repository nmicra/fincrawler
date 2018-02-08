package il.fincrawl.fincrawler.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FinCrawlerResource {


    private final Logger log = LoggerFactory.getLogger("analytics");

    @GetMapping("/insertEntry/{key}/{val:.+}") // latitude:.+ --> solves spring's bug to truncating last number after dot (.)
    public String getNearParkingSpots(@PathVariable String key, @PathVariable String val) {
        log.info(String.format("%s=%s", key, val));
        return key + "=" + val;
    }

    @GetMapping("/writeStr/{str:.+}")
    public String writeStr(@PathVariable String str) {
        log.info(str);
        return str;
    }
}
