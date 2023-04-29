package tech.iamalmir.restfulspring;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import tech.iamalmir.restfulspring.controller.TaskController;


@SpringBootApplication
@EnableR2dbcAuditing
public class RestfulSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestfulSpringApplication.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> routes(TaskController taskController) {
        final String API_V1_TASKS = "/api/v1/tasks";
        final String API_V1_TASKS_ID = "/api/v1/tasks/{id}";

        return route(GET(API_V1_TASKS), taskController::all)
                .andRoute(POST(API_V1_TASKS), taskController::create)
                .andRoute(GET(API_V1_TASKS_ID), taskController::getById)
                .andRoute(PATCH(API_V1_TASKS_ID), taskController::update)
                .andRoute(DELETE(API_V1_TASKS_ID), taskController::delete);
    }
}
