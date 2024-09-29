# spring-resilience4j

## Resilience4J features and modules
 - ### Circuit Breaker: fault tolerance
 - ### Rate Limiter: block too frequent request
 - ### Time Limiter: set a time limit when callling remote operation
 - ### Retry Machanism: automatically retry a failed remote operation
 - ### Bukhead: avoid too many concurrent request
 - ### Cache: store of costly remote operation



## user-service communicate with catalog-service and catalog-service communicate with discount-service
## If catalog-service failed, then user-service will get "500 internal server error" and user-service also down.
## To avoid this failure and user-service make it work rather than any failure in dependent server.
## we can achieve this by implementing the "Resilience4J" with spring.
<img width="461" alt="image" src="https://github.com/user-attachments/assets/b5eb4484-0cfe-4c91-9974-42d89696c807">


