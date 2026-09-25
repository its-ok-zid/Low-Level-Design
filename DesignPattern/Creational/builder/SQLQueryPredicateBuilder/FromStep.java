package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

public interface FromStep {
    OptionalSteps fromTable(String table);
}