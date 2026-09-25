package DesignPattern.Creational.builder.SQLQueryPredicateBuilder;

public class SqlQueryApplication {
    public static void main(String[] args) {
        System.out.println("=== 1. Simple Query Test ===");
        SqlQuery simpleQuery = SqlQuery.builder()
                .select("id", "username", "email")
                .fromTable("users")
                .build();
        System.out.println(simpleQuery.toSql());

        System.out.println("\n=== 2. Complex Filtered Query with Joins, GroupBy, Limit ===");
        SqlQuery complexQuery = SqlQuery.builder()
                .select("users.id", "users.name", "COUNT(orders.id) as order_count")
                .fromTable("users")
                .join("LEFT JOIN orders ON users.id = orders.user_id")
                .where("users.status = 'ACTIVE'")
                .where("orders.total_amount > 100")
                .groupBy("users.id", "users.name")
                .orderBy("order_count DESC")
                .limit(20)
                .offset(40)
                .build();
        System.out.println(complexQuery.toSql());

        System.out.println("\n=== 3. Testing Invariant Violation (OFFSET without LIMIT) ===");
        try {
            SqlQuery invalidQuery = SqlQuery.builder()
                    .select("id")
                    .fromTable("audit_logs")
                    .offset(10)
                    .build();
            System.err.println("FAILED: Validation did not catch missing LIMIT! Query: " + invalidQuery.toSql());
        } catch (IllegalStateException e) {
            System.out.println("SUCCESS: Correctly blocked invalid query -> " + e.getMessage());
        }
    }
}