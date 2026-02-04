package com.example.demo.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
@Slf4j
public class EndpointConfig {

    @Bean
    public CommandLineRunner printEndpoints(RequestMappingHandlerMapping mapping) {
        return args -> {
            log.info("==========================================================================================");
            log.info("         API ENDPUNKT ÜBERSICHT                                                           ");
            log.info("==========================================================================================");

            mapping.getHandlerMethods().entrySet().stream()
                    .flatMap(entry -> {
                        var methods = entry.getKey().getMethodsCondition().getMethods();
                        var methodSet = methods.isEmpty() ? Set.of("ANY") :
                                methods.stream().map(Enum::name).collect(Collectors.toSet());

                        String path = extractPath(entry);

                        String className = entry.getValue().getBeanType().getSimpleName();
                        String methodName = entry.getValue().getMethod().getName();
                        String handlerInfo = className + "." + methodName + "()";

                        return methodSet.stream().map(m -> new EndpointInfo(m, path, handlerInfo));
                    })
                    .sorted(Comparator.comparing(EndpointInfo::getMethod).thenComparing(EndpointInfo::getPath))
                    .collect(Collectors.groupingBy(EndpointInfo::getMethod, LinkedHashMap::new, Collectors.toList()))
                    .forEach((method, endpoints) -> {
                        log.info("");
                        log.info("--- [ {} ] -----------------------------------------------------------------------", method);
                        for (EndpointInfo e : endpoints) {
                            String formattedLine = String.format("  %-45s | Method: %s", e.getPath(), e.getHandler());
                            log.info(formattedLine);
                        }
                    });

            log.info("");
            log.info("==========================================================================================");
        };
    }

    private static String extractPath(Map.Entry<RequestMappingInfo, HandlerMethod> entry) {
        return entry.getKey().getDirectPaths().isEmpty()
                ? entry.getKey().getPatternValues().toString()
                : entry.getKey().getDirectPaths().toString();
    }

    @Getter
    private static class EndpointInfo {
        private final String method;
        private final String path;
        private final String handler;

        public EndpointInfo(String method, String path, String handler) {
            this.method = method;
            this.path = path;
            this.handler = handler;
        }

    }
}