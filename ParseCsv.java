import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
public class ParseCsv  {

    /*
    <Convert .csv to ArrayList>
    Params: csv path.
    Return: file content as ArrayList.
    */
    public static ArrayList<String> CsvParse(String path) {
        //parsing a CSV file into Scanner class constructor
        try {
            Scanner csvReader = new Scanner(new File(path));

            //sets the delimiter pattern
            csvReader.useDelimiter(",|\\n");

            ArrayList<String> list = new ArrayList<String>();

            //returns a boolean value
            while (csvReader.hasNext()){
                String value = csvReader.next();
                value = value.replace("\r", "");
                value = value.replace("'", "");
                list.add(value);
            }

            //closes the scanner
            csvReader.close();

            return list;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        // Case failure : null.
        return null;
    }
}