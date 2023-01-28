import java.sql.*;
import java.util.*;

public class SqlConnect {
    static Scanner reader = new Scanner(System.in);

    /*
        <Execute Sql Command>
        Params: DB url, username, password, query to execute, number of columns to print (+1).
        Return: Output Data as ArrayList.
     */
    public static ArrayList<String> SqlExecute(String url, String name,
                                               String password, String query, int Columns) {
        // Declare Variables.
        ArrayList<String> output = new ArrayList<String>();

        // Locate Driver.
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("class not found");
        }

        try {
            // Connect to MySql.
            Connection con = DriverManager.getConnection(url, name, password);

            // Enter Query, Execute & get Results.
            PreparedStatement statement = con.prepareStatement(query);
            statement.execute();
            ResultSet result = statement.getResultSet();

            // store Output.
            if (result != null) {
                while (result.next()) {
                    String data = "";
                    for (int i = 1; i < Columns; i++) {
                        data += "[" +result.getString(i) + "]";
                    }
                    output.add(data);
                }
            }
            con.close();
            statement.close();
            return output;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
    <Print Output Data>
    Params: Output Data as ArrayList.
    Return: None.
    */
    public static void PrintOutput(ArrayList<String> output){
        if(output == null){
            System.out.println("Error, output is null.");
        }
        else{
            if(!output.isEmpty()){
                for(int i = 0; i < output.size(); i++){
                    System.out.println(output.get(i));
                }
            }
        }
    }

    /*
    <Create Table>
    Params: DB url, username, password, list of the values to be inserted, table name.
    Return: Table Types as ArrayList.
    */
    public static ArrayList<String> CreateTable(String url, String name, String password,
                                                ArrayList<String> list, String tablename) {
        // Declare Variables.
        int CountParam = 0;
        ArrayList<String> types = new ArrayList<String>();
        String query = "create table "+ tablename +" (";
        String type;
        String constraint;
        int primaryKey;
        int foreignKey;

        // Get the number of parameters.
        while (!list.get(CountParam).equals("1")) {
            CountParam++;
        }

        // Get types.
        for (int i = 0; i < CountParam; i++) {
            System.out.println("Whats this? " + list.get(i));
            type = reader.nextLine();
            types.add(type);
            System.out.println("Any Constraints for " + list.get(i) + "?");
            constraint = reader.nextLine();
            query += list.get(i) + " " + type + " " + constraint + ", ";
        }

        // Get primary key.
        System.out.println("Whats the Primary Key's index?");
        primaryKey = reader.nextInt();
        query += " primary key (" + list.get(primaryKey) + ")";

        // Get foreign key.
        System.out.println("Whats the Foreign Key's index? -1 if no foreign key.");
        foreignKey = reader.nextInt();
        while(foreignKey != -1) {
            System.out.println("References?");
            reader.nextLine();
            constraint = reader.nextLine();
            query += ", foreign key (" + list.get(foreignKey) + ") references " + constraint;
            System.out.println("Whats the Foreign Key's index? -1 if no foreign key.");
            foreignKey = reader.nextInt();
        }
        // Create table.
        query += ");";
        System.out.println(query);
        SqlExecute(url, name, password, query, CountParam);

        return types;
    }

    /*
    <Insert Into Table>
    Params: DB url, username, password, list of the values to be inserted, list of table columns types, table name.
    Return: Table Types as ArrayList.
    */
    public static void InsertTable( String url, String name, String password, ArrayList<String> list,
                                    ArrayList<String> types, String tablename) {
        // Get insertion types.
        String insertFormat = "insert into " + tablename + " (";
        String query;
        int CountParam = 0;
        while (!list.get(CountParam).equals("1")) {
            CountParam++;
        }

        for (int i = 0; i < CountParam; i++) {
            insertFormat += list.get(i);
            if (i + 1 != CountParam) {
                insertFormat += ", ";
            }
        }

        insertFormat += ") values (";

        // Start insertions.
        for (int i = 1; i < (list.size() / CountParam); i++) {
            // Init query.
            query = insertFormat;
            // Case : connection but 1 person is missing...
            if(tablename.equals("highschool_friendships") && (list.get(i*CountParam).equals("") ||
                    list.get(i*CountParam + 1).equals(""))){
                continue;
            }
            for (int j = 0; j < CountParam; j++) {
                // Case : no car color...
                if(list.get(i*CountParam + j).equals("") && j == 9 && tablename.equals("highschool")) {
                    if(list.get(i*CountParam + j - 1).equals("true")) {
                        list.set(i * CountParam + j, "Unknown");
                    }
                    else {
                        list.set(i * CountParam + j, "None");
                    }
                }

                // Case : color is present, but there is no car...
                if(list.get(i*CountParam + j).equals("false") && j == 8 && tablename.equals("highschool")){
                    list.set(i * CountParam + j + 1, "None");
                }

                // Case : param is string.
                if (types.get(j).equals("varchar(255)")) {
                    query += "'" + list.get(i * CountParam + j) + "'";
                } else {
                    query += list.get(i * CountParam + j);
                }

                // Add Coma.
                if (j + 1 != CountParam) {
                    query += ", ";
                }

            }
            query += ");";

            // Execute query.
            SqlExecute(url, name, password, query, CountParam);
        }
    }
}