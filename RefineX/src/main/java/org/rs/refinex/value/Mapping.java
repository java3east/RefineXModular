package org.rs.refinex.value;

/**
 * Represents a mapping between a class and an object mapper.
 * @param clazz the class to be mapped
 * @param mapper the object mapper to be used for the class
 */
public record Mapping(Class<?> clazz, ObjectMapper<?> mapper) {
    public Mapping(Class<?> clazz, ObjectMapper<?> mapper) {
        this.clazz = clazz;
        this.mapper = mapper;
    }
}
