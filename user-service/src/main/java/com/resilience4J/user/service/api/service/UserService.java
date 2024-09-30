package com.resilience4J.user.service.api.service;

import com.resilience4J.user.service.api.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    @Autowired
    @Lazy
    private RestTemplate restTemplate;

    private static final String BASEURL = "http://localhost:9191/orders";

    public List<OrderDTO> getOrders(String category) {
        String url = ((category == null) || (category.length()==0)) ? BASEURL : BASEURL + "/" + category;

        OrderDTO[] forObject = restTemplate.getForObject(url, OrderDTO[].class);
        assert forObject != null;
        return Arrays.asList(forObject);

//        ResponseEntity<List<OrderDTO>> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<OrderDTO>>() {
//                }
//        );
//
//        return responseEntity.getBody();
    }
}
