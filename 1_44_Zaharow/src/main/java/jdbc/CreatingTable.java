package jdbc;

public class CreatingTable {
    public static final String CREATE_TABLE_QUERY =
            "CREATE TABLE phones "
                    + "(id serial, "
                    + "user_id INT, "
                    + "phone_number VARCHAR(10), "
                    + "PRIMARY KEY(id));";
}