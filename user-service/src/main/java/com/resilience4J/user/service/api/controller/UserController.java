package com.resilience4J.user.service.api.controller;

import com.resilience4J.user.service.api.dto.OrderDTO;
import com.resilience4J.user.service.api.service.UserService;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/user-service")
public class UserController {

    public static final String USER_SERVICE = "userService";

    @Autowired
    private UserService userService;

    private int attempt=1;

    @GetMapping("/displayOrders")
    @CircuitBreaker(name = USER_SERVICE, fallbackMethod = "getAllAvailableProducts")
    @Retry(name = USER_SERVICE,fallbackMethod = "getAllAvailableProducts")
    @RateLimiter(name = USER_SERVICE, fallbackMethod = "getAllAvailableProducts")
    @TimeLimiter(name = USER_SERVICE, fallbackMethod = "getAllAvailableProducts")
    @Bulkhead(name = USER_SERVICE, type = Bulkhead.Type.THREADPOOL, fallbackMethod = "getAllAvailableProducts")
    public List<OrderDTO> displayOrders(@RequestParam("category") String category) {
        System.out.println("retry method called "+attempt++ +" times "+" at "+new Date());
        return userService.getOrders(category);
    }

    public List<OrderDTO> getAllAvailableProducts(Exception e) {
        return Stream.of(
                new OrderDTO(119, "LED TV123", "electronics", "white", 45000),
                new OrderDTO(345, "Headset123", "electronics", "black", 7000),
                new OrderDTO(475, "Sound bar123", "electronics", "black", 13000),
                new OrderDTO(574, "Puma Shoes", "foot wear", "black & white", 4600),
                new OrderDTO(678, "Vegetable chopper", "kitchen", "blue", 999),
                new OrderDTO(532, "Oven Gloves", "kitchen", "gray", 745)
        ).collect(Collectors.toList());
    }
}
