package me.parkprin.service;

import me.parkprin.domain.Quote;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class QuoteService {
    private static final Logger log = LoggerFactory.getLogger(QuoteService.class);

    private RestTemplate restTemplate;

    @Autowired
    public QuoteService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public Quote getQuoteAfterUpdateTask(String type) {
        Quote quote = restTemplate.getForObject(
                "https://gturnquist-quoters.cfapps.io/api/random",
                Quote.class
                );
        if (StringUtils.isNotEmpty(type)) {
            log.info(quote.toString());
            log.info("type 변경전: " + quote.getType());
            quote.setType(type);
            log.info("type 변경완료: " + quote.getType());
            log.info(quote.toString());
        } else {
            log.info("변경할 type이 지정되지 않았습니다");
            log.info(quote.toString());
        }
        return quote;
    }
}
