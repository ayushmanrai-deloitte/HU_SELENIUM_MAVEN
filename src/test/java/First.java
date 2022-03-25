import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class First{
    static WebDriver driver;
    static String username = "", password = "", first_name = "", last_name = "", pin_code = "222001";

    @Test(priority = 1)
    public static void excel_reader() throws IOException {
        String line = "";
        String splitBy = ",";
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\ayurai\\Registration.csv"));
        while ((line = br.readLine()) != null) {
            String[] login = line.split(splitBy);
            username = login[0];
            password = login[1];
            break;
        }
        BufferedReader br2 = new BufferedReader(new FileReader("C:\\Users\\ayurai\\Booking_Details.csv"));
        while ((line = br2.readLine()) != null) {
            String[] cust = line.split(splitBy);
            first_name = cust[0];
            last_name = cust[1];
            pin_code = cust[2];
            break;
        }
    }
}
