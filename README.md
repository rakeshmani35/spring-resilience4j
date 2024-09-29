# spring-resilience4j

## Resilience4J features and modules
 - ### Circuit Breaker: fault tolerance
 - ### Rate Limiter: block too frequent request
 - ### Time Limiter: set a time limit when callling remote operation
 - ### Retry Machanism: automatically retry a failed remote operation
 - ### Bukhead: avoid too many concurrent request
 - ### Cache: store of costly remote operation
##

## Use case
### user-service communicate with catalog-service and catalog-service communicate with discount-service.If catalog-service failed, then user-service will get "500 internal server error" and user-service also down.To avoid this failure and user-service make it work rather than any failure in dependent server.To avoid this failure and user-service make it work rather than any failure in dependent server.we can achieve this by implementing the "Resilience4J" with spring.

### we can achieve this by implementing the "Resilience4J" with spring.
<img width="461" alt="image" src="https://github.com/user-attachments/assets/b5eb4484-0cfe-4c91-9974-42d89696c807">

## Circuit Breaker Pattern
## By default CB state is "Close". e.g threshold-rate is 50% and number of call: 5. when user-service call catalog-service continus 5 time and there sucess:2 and failure:3. Means failure rate is more than threshold rate then CB state become "Open". There is reset-timeout is 5sec, means after 5sec againa user-sercive will  do few call  to catalog-service (based on configuration). If again failure rate will be more than thresold-rate the status of CB become "Open" else "Close".
 

<img width="575" alt="image" src="https://github.com/user-attachments/assets/184ecba9-4162-4913-9fe2-4d463c58369a">



