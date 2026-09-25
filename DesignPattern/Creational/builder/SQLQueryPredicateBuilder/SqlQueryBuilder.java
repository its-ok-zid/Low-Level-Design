package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlQueryBuilder implements SelectStep, FromStep, OptionalSteps {
    String table;
    final List<String> columns = new ArrayList<>();
    final List<String> joinClauses = new ArrayList<>();
    final List<String> whereClauses = new ArrayList<>();
    final List<String> groupByColumns = new ArrayList<>();
    String orderBy;
    Integer limit;
    Integer offset;

    @Override
    public FromStep select(String... cols) {
        if (cols != null) {
            for (String col : cols) {
                if (col != null && !col.trim().isEmpty()) {
                    this.columns.add(col.trim());
                }
            }
        }
        if (this.columns.isEmpty()) {
            this.columns.add("*");
        }
        return this;
    }

    @Override
    public OptionalSteps fromTable(String table) {
        if (table == null || table.trim().isEmpty()) {
            throw new IllegalArgumentException("Target table cannot be null or empty.");
        }
        this.table = table.trim();
        return this;
    }

    @Override
    public OptionalSteps join(String joinClause) {
        if (joinClause != null && !joinClause.trim().isEmpty()) {
            this.joinClauses.add(joinClause.trim());
        }
        return this;
    }

    @Override
    public OptionalSteps where(String condition) {
        if (condition != null && !condition.trim().isEmpty()) {
            this.whereClauses.add(condition.trim());
        }
        return this;
    }

    @Override
    public OptionalSteps groupBy(String... columns) {
        if (columns != null) {
            for (String col : columns) {
                if (col != null && !col.trim().isEmpty()) {
                    this.groupByColumns.add(col.trim());
                }
            }
        }
        return this;
    }

    @Override
    public OptionalSteps orderBy(String orderByClause) {
        if (orderByClause != null && !orderByClause.trim().isEmpty()) {
            this.orderBy = orderByClause.trim();
        }
        return this;
    }

    @Override
    public OptionalSteps limit(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("LIMIT must be greater than or equal to 0.");
        }
        this.limit = limit;
        return this;
    }

    @Override
    public OptionalSteps offset(int offset) {
        if (offset < 0) {
            throw new IllegalArgumentException("OFFSET must be greater than or equal to 0.");
        }
        this.offset = offset;
        return this;
    }

    @Override
    public SqlQuery build() {
        // Enforce SQL Invariant: OFFSET requires LIMIT
        if (this.offset != null && this.limit == null) {
            throw aerialIllegalStateException("Invalid SQL syntax: OFFSET cannot be specified without a corresponding LIMIT clause.");
        }
        return new SqlQuery(this);
    }

    private IllegalStateException aerialIllegalStateException(String message) {
        return new IllegalStateException(message);
    }
}