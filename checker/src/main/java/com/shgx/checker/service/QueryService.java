package com.shgx.checker.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shgx.common.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

/**
 * @author: guangxush
 * @create: 2019/10/15
 */
@Slf4j
public abstract class QueryService {

    @Autowired
    private RestTemplateBuilder builder;

    private ObjectMapper om = new ObjectMapper();

    /**
     * 外部查询接口调用
     *
     * @param param
     * @return
     */
    public abstract Object doQuery(String param);


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

    /**
     * 带参数的url请求
     *
     * @param url
     * @param args
     * @return
     */
    public Object paramRequest(String url, String... args) {
        Object object = null;
        RestTemplate restTemplate = builder.build();
        if (url == null) {
            log.error("the url is null!");
            return null;
        }
        String request = url;
        StringBuffer sb = new StringBuffer();
        for (String arg : args) {
            sb.append(arg);
        }
        request += sb.toString();
        try {
            ResponseEntity<ApiResponse> entity = restTemplate.getForEntity(request, ApiResponse.class);
            //如果请求数据成功，返回结果
            if (entity.getStatusCode() == HttpStatus.OK) {
                object =Objects.requireNonNull(entity.getBody()).getData();
                log.info("successfully get the info!");
                return object;
            } else {
                log.info("fail get the info!, the return code is {}", entity.getStatusCode());
                return object;
            }
        } catch (Exception e) {
            log.error("send the request {} failed!", request);
        }
        return object;
    }
}
