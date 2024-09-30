# Spring-Boot3-resilience4j

## Resilience4J features and modules
 - #### Circuit Breaker: fault tolerance
 - #### Rate Limiter: block too frequent request
 - #### Time Limiter: set a time limit when callling remote operation
 - #### Retry Machanism: automatically retry a failed remote operation
 - #### Bukhead: avoid too many concurrent request
 - #### Cache: store of costly remote operation


## Use case
user-service communicate with catalog-service and catalog-service communicate with discount-service.If catalog-service failed, then user-service will get "500 internal server error" and user-service also down.To avoid this failure and user-service make it work rather than any failure in dependent server.To avoid this failure and user-service make it work rather than any failure in dependent server.we can achieve this by implementing the "Resilience4J" with spring.

#### we can achieve this by implementing the "Resilience4J" with spring.
<img width="461" alt="image" src="https://github.com/user-attachments/assets/b5eb4484-0cfe-4c91-9974-42d89696c807">

## Circuit Breaker Pattern
By default CB state is "Close". e.g threshold-rate is 50% and number of call: 5. when user-service call catalog-service continus 5 time and there sucess:2 and failure:3. Means failure rate is more than threshold rate then CB state become "Open". There is reset-timeout is 5sec, means after 5sec againa user-sercive will  do few call  to catalog-service (based on configuration). If again failure rate will be more than thresold-rate the status of CB become "Open" else "Close".
 

<img width="575" alt="image" src="https://github.com/user-attachments/assets/184ecba9-4162-4913-9fe2-4d463c58369a">


## User-Service
### pom.xml
```
        <!-- to health check of service -->
                <dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-aop</artifactId>
		</dependency>
         <!-- spring-boot:3 resiliency4j --> 
               <dependency>
			<groupId>io.github.resilience4j</groupId>
			<artifactId>resilience4j-spring-boot3</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>
```

## Retry Mechanism

When user-service communicate with catalog-service, catalog-service taking longer time to send response to user-service. There could be multiple reason for slowness.

<img width="557" alt="image" src="https://github.com/user-attachments/assets/3c912df7-aa47-4b72-a4fc-9e2a13264584">

user-service get the failure response from catalog-service (due to either service is down or some other reason). user-service call to catalog-service multiple times in some time intervals.

## application.yml
```
resilience4j:
  circuitbreaker:
    instances:
      userService:
        registerHealthIndicator: true
        eventConsumerBufferSize: 10
        failureRateThreshold: 50
        minimumNumberOfCalls: 5
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        permittedNumberOfCallsInHalfOpenState: 3
        slidingWindowSize: 10
        slidingWindowType: COUNT_BASED

  retry:
    instances:
      userService:
        registerHealthIndicator: true
        maxRetryAttempts: 5
        waitDuration: 10s
        retryExceptions: java.io.IOException

  ratelimiter:
    instances:
      userService:
        registerHealthIndicator: false
        limitForPeriod: 10
        limitRefreshPeriod: 10s
        timeoutDuration: 3s

  timeLimiter:
    instances:
      userService:
        timeoutDuration: 2s

  bulkhead:
    instances:
      userService:
        maxConcurrentCalls: 5
        maxWaitDuration: 0
```
 ### Circuit Breaker (circuitbreaker):
----------------------------------
- #### slidingWindowSize:  Number of calls to consider for the failure rate.
- #### minimumNumberOfCalls:  Minimum calls required before calculating failure rate.
- #### failureRateThreshold:  Percentage of failures to open the circuit.
- #### waitDurationInOpenState:  Time to wait before transitioning to half-open.
- #### permittedNumberOfCallsInHalfOpenState:  Number of test calls in half-open state.
- #### automaticTransitionFromOpenToHalfOpenEnabled:  Automatically transition to half-open after wait duration.

 ### Rate Limiter (rateLimiter):
----------------------------
- #### limitForPeriod: Number of calls allowed per period.
- #### limitRefreshPeriod: Time period for refreshing the limit.
- #### timeoutDuration: Time to wait for a permit before failing.

### Retry (retry):
---------------
- #### maxRetryAttempts: Maximum number of retry attempts.
- #### waitDuration: Time to wait between retries.
- #### retryExceptions: Exceptions that trigger a retry.

### Time Limiter (timeLimiter):
----------------------------
- #### timeoutDuration: Maximum time allowed for a call.

### Bulkhead (bulkhead):
----------------------
- #### maxConcurrentCalls: Maximum number of concurrent calls.
maxWaitDuration: Time to wait for a permit before failing (0 means no wait).
   
