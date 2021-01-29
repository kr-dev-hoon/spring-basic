package dev.hoon.basic.global.model;

import org.springframework.http.ResponseEntity;

import java.util.List;

public class Response {

    public static <T> ResponseEntity<List<T>> of(List<T> result) {

        if (result == null || result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

    public static <T> ResponseEntity<T> of(T result) {

        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

}
