package com.shgx.router.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author: guangxush
 * @create: 2020/02/20
 */
@Service
@Slf4j
public class RequestService {
    @Autowired
    private RestTemplateBuilder builder;

    private ObjectMapper om = new ObjectMapper();

    public Boolean jsonRequest(String url, Object object) {
        Boolean sendResult = false;
        RestTemplate restTemplate = builder.build();
        String objectJson = "";
        try {
            objectJson = new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            log.error("can't trans the {} object to json string!", object);
        }
        String request = url;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectJson, headers);
        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(request, requestEntity, String.class);
            //如果请求数据成功，获取请求之后的值
            if (entity.getStatusCode() == HttpStatus.OK) {
                JsonNode jsonNode = new ObjectMapper().readTree(entity.getBody());
                sendResult = Boolean.valueOf(jsonNode.get("data").asText());
                if (!sendResult) {
                    log.info("send {} failed!, the return result is {}.", object, sendResult);
                }
                log.info("send {} succeed!", object);
            } else {
                log.error("send {}  failed, the status is {}!, the entity is {}, the url is {}!", object,
                        entity.getStatusCode(), requestEntity.toString(), url);
            }
        } catch (Exception e) {
            log.error("send {}  failed!", object);
            throw new InternalError(String.format("send %s  failed!, the error is %s", object, e));
        }
        return sendResult;
    }
}
