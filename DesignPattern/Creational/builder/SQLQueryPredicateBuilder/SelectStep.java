package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

public interface SelectStep {
    FromStep select(String... columns);
}