package com.example.demo.utils;

import com.example.demo.exception.RestException;
import io.vavr.control.Try;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import static java.text.MessageFormat.format;

public interface SqlResource {
    Map<String, String> stringsByPath = new ConcurrentHashMap<>();

    String name();

    default String sql() {

        var pathAnnotation = Optional.of(this.getClass())
                .map(clazz -> clazz.getAnnotation(Path.class))
                .orElseThrow(() -> new RestException("Enclosing Enum should be annotated with the @Path annotation.", HttpStatus.INTERNAL_SERVER_ERROR));

        var path = format(pathAnnotation.pattern(), this.name());

        UnaryOperator<String> mappingFunction = sqlFilePath -> Try.of(() -> getClass().getClassLoader().getResourceAsStream(sqlFilePath))
                .map(inputStream -> new BufferedReader(new InputStreamReader(inputStream))
                        .lines()
                        .collect(Collectors.joining(System.lineSeparator()))
                ).onFailure(throwable -> {
                    throw new RestException("Failed to get SQL file", HttpStatus.INTERNAL_SERVER_ERROR);
                }).get();

        return stringsByPath.computeIfAbsent(path, mappingFunction);
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface Path {

        String pattern() default "{0}.sql";
    }
}
