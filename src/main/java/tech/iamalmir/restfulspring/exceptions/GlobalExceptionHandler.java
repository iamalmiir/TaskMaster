package tech.iamalmir.restfulspring.exceptions;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;

import reactor.core.publisher.Mono;

@Component
@Order(-2)
public class GlobalExceptionHandler implements WebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof TaskNotFoundException) {
            exchange.getResponse().setStatusCode(HttpStatus.NOT_FOUND);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // Send the error message to the body of the response
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(ex.getMessage().getBytes())));
        } else if (ex instanceof IllegalArgumentException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
            // Send the error message to the body of the response
            return exchange.getResponse().writeWith(Mono.just(exchange.getResponse()
                    .bufferFactory().wrap(ex.getMessage().getBytes())));
        }
        return Mono.error(ex);
    }
}
