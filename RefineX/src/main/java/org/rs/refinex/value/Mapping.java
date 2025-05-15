package org.rs.refinex.value;

public record Mapping(Class<?> clazz, ObjectMapper<?> mapper) {
    public Mapping(Class<?> clazz, ObjectMapper<?> mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }
}
