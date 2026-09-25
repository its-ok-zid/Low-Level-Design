package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SqlQuery {
    private final String table;
    private final List<String> columns;
    private final List<String> joinClauses;
    private final List<String> whereClauses;
    private final List<String> groupByColumns;
    private final String orderBy;
    private final Integer limit;
    private final Integer offset;

    // Package-private constructor accessed strictly by the Builder
    SqlQuery(SqlQueryBuilder builder) {
        this.table = builder.table;
        this.columns = List.copyOf(builder.columns);
        this.joinClauses = List.copyOf(builder.joinClauses);
        this.whereClauses = List.copyOf(builder.whereClauses);
        this.groupByColumns = List.copyOf(builder.groupByColumns);
        this.orderBy = builder.orderBy;
        this.limit = builder.limit;
        this.offset = builder.offset;
    }

    public static SelectStep builder() {
        return new SqlQueryBuilder();
    }

    public String getTable() {
        return table;
    }

    public List<String> getColumns() {
        return columns;
    }

    public List<String> getJoinClauses() {
        return joinClauses;
    }

    public List<String> getWhereClauses() {
        return whereClauses;
    }

    public List<String> getGroupByColumns() {
        return groupByColumns;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public String toSql() {
        StringBuilder sql = new StringBuilder();

        // 1. SELECT clause
        sql.append("SELECT ");
        if (columns.isEmpty()) {
            sql.append("*");
        } else {
            sql.append(String.join(", ", columns));
        }

        // 2. FROM clause
        sql.append(" FROM ").append(table);

        // 3. JOIN clauses
        for (String join : joinClauses) {
            sql.append(" ").append(join);
        }

        // 4. WHERE clauses (chained with AND)
        if (!whereClauses.isEmpty()) {
            sql.append(" WHERE ").append(String.join(" AND ", whereClauses));
        }

        // 5. GROUP BY clause
        if (!groupByColumns.isEmpty()) {
            sql.append(" GROUP BY ").append(String.join(", ", groupByColumns));
        }

        // 6. ORDER BY clause
        if (orderBy != null && !orderBy.trim().isEmpty()) {
            sql.append(" ORDER BY ").append(orderBy);
        }

        // 7. LIMIT clause
        if (limit != null) {
            sql.append(" LIMIT ").append(limit);
        }

        // 8. OFFSET clause
        if (offset != null) {
            sql.append(" OFFSET ").append(offset);
        }

        sql.append(";");
        return sql.toString();
    }

    @Override
    public String toString() {
        return toSql();
    }
}