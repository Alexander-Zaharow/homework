package jdbc;

public class RetrieveDataPreparedStatement {
    public static final String SELECT_QUERY =
            "SELECT * FROM phones WHERE id > ? AND phone_number LIKE ?;";
}
