import javax.print.DocFlavor;
import java.util.Scanner;
public class Menu {
    static Scanner reader = new Scanner(System.in);
    static String URL = "jdbc:mysql://localhost:3306/sima";
    static String NAME = "root";
    static String PASSWORD = "a5368310";
    public static void DisplayMenu(){
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
        System.out.println("=-         ________  __    __        __    _______         -=");
        System.out.println("=-        / ______/ (__)  |  \\      /  |  /   _   \\        -=");
        System.out.println("=-        | |_____  |  |  |   |    |   |  |  |_|  |        -=");
        System.out.println("=-        \\____  \\  |  |  |    \\__/    |  |   _   |        -=");
        System.out.println("=-        _____| |  |  |  |  |\\    /|  |  |  | |  |        -=");
        System.out.println("=-       /_______/  |__|  |__| \\__/ |__|  |__| |__|        -=");
        System.out.println("=-                                                         -=");
        System.out.println("=- &Welcome, Sima!                                         -=");
        System.out.println("=- &For School Avg [1]                                     -=");
        System.out.println("=- &For Female Students Avg [2]                            -=");
        System.out.println("=- &For Male Students Avg [3]                              -=");
        System.out.println("=- &For Avg Height of Every Student Above 2 Meters,        -=");
        System.out.println("=- Whose Car is Colored Purple [4]                         -=");
        System.out.println("=- &For A Student's First And Second Friend Circle [5]     -=");
        System.out.println("=- &For Social Stats [6]                                   -=");
        System.out.println("=- &For A Student's Avg [7]                                -=");
        System.out.println("=- &For Exiting [8]                                        -=");
        System.out.println("=-                                   2023 (c) NahWeek Inc. -=");
        System.out.println("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");

    }

    public static void MenuShell(){
        // Declare Variables.
        char Option;
        boolean switchFlag = true;

        while(switchFlag){
            DisplayMenu();
            Option = reader.next().charAt(0);
            switch (Option) {
                case '1': {
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select avg(grade_avg) from highschool;", 2));
                    break;
                }
                case '2': {
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select avg(grade_avg) from highschool where gender like 'male';", 2));
                    break;
                }
                case '3': {
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select avg(grade_avg) from highschool where gender like 'female';", 2));
                    break;
                }
                case '4': {
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select avg(cm_height) from highschool where cm_height > 200 and car_color like 'purple';", 2));
                    break;
                }
                case '5': {
                    System.out.println("Enter Requested Student's id.");
                    int id = reader.nextInt();
                    String query5_1 = "select other_friend_id from highschool_friendships where friend_id = "+id;
                    String query5_2 = "select friend_id from highschool_friendships where other_friend_id = "+id;
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            query5_1+";", 2));
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            query5_2+";", 2));
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select friend_id from highschool_friendships where"+
                                    " (other_friend_id in ("+query5_2+") or other_friend_id in ("+query5_1+")) and (friend_id not in ("+
                    query5_1+") and friend_id not in ("+query5_1+")) and friend_id != "+id+";", 2));
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select other_friend_id from highschool_friendships where"+
                                    " (friend_id in ("+query5_2+") or friend_id in ("+query5_1+")) and (other_friend_id not in ("+
                            query5_1+") and other_friend_id not in ("+query5_1+")) and other_friend_id != "+id+";", 2));
                    break;
                }
                case '6': {
                    int table_size = SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select id from highschool;", 2).size();
                    int has_friends = SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select id from highschool where id in (select other_friend_id from highschool_friendships) or id in (select friend_id from highschool_friendships);", 2).size();
                    int uni = SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select friend_id from highschool_friendships where friend_id not in (select other_friend_id from highschool_friendships) group by friend_id having count(friend_id) = 1;", 2).size() +
                            SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                                    "select other_friend_id from highschool_friendships where other_friend_id not in (select friend_id from highschool_friendships) group by other_friend_id having count(other_friend_id) = 1;", 2).size();
                    double popular = (double)(has_friends- uni)/table_size * 100;
                    double oneFriend = ((double)uni/table_size) * 100;
                    double lonely = (double)(table_size - has_friends)/table_size * 100;
                    System.out.println("More Than One Friend: " + String.format("%.2f", popular) +"%.");
                    System.out.println("Exactly One Friend: " + String.format("%.2f", oneFriend) +"%.");
                    System.out.println("No Friends: " + String.format("%.2f", lonely) +"%.");
                    break;
                }
                case '7': {
                    System.out.println("Enter Requested Student's id.");
                    int id = reader.nextInt();
                    SqlConnect.PrintOutput(SqlConnect.SqlExecute(URL, NAME, PASSWORD,
                            "select grade_avg from idAndAvg where id = " + id + ";", 2));
                    break;
                }
                case '8': {
                    System.out.println("Goodbye World...");
                    switchFlag = false;
                    break;
                }
                default:{
                    System.out.println("Invalid Input, Press Any Key And Try Again.");
                    reader.next().charAt(0);
                    break;
                }
            }
        }
    }
}

