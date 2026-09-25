package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

public interface OptionalSteps {
    OptionalSteps join(String joinClause);
    OptionalSteps where(String condition);
    OptionalSteps groupBy(String... columns);
    OptionalSteps orderBy(String orderByClause);
    OptionalSteps limit(int limit);
    OptionalSteps offset(int offset);
    SqlQuery build();
}