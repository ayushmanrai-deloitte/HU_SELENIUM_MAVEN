import org.openqa.selenium.By;
import java.lang.Double;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class second extends First {
    static int item_count=0;
    static String username="standard_user",password="secret_sauce",first_name="Ayush",last_name="man",pin_code="222001";
    static double item_1_price,item_2_price;
    @Test(priority = 2)
    public static void base() throws Exception
    {
        System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
        dri=new ChromeDriver();
        dri.get("https://www.saucedemo.com/");
        dri.manage().window().maximize();

    }
    @Test(priority = 3)
    public static void login() throws Exception
    {
        send_keys("(//input[@class='input_error form_input'])[1]",username);
        send_keys("(//input[@class='input_error form_input'])[2]",password);
        click("//input[@type='submit']");
    }
    @Test(priority = 4)
    public static void selection_and_conformation() throws Exception{
        container("//select[@class='product_sort_container']", "Price (high to low)");
        item_1_price = item_price("//div[@class='inventory_item_price']");
        if (item_1_price >= 100)
            throw new Exception("price is too high");
        click("(//div[@class='inventory_item'])[1]//button");
        item_count += 1;
        String remove = dri.findElement(By.xpath("(//div[@class='inventory_item'])[1]//button")).getText();
        if (!remove.equals("REMOVE"))
            throw new Exception("remove option not available");
        item_count -= 1;
        click("(//div[@class='inventory_item'])[1]//button");
        click("(//div[@class='inventory_item'])[1]//button");
        item_count += 1;
        click("//a[@class='shopping_cart_link']");
        click("//button[@id='continue-shopping']");
        container("//select[@class='product_sort_container']", "Price (low to high)");
        item_2_price = item_price("//div[@class='inventory_item_price']");
        click("(//div[@class='inventory_item'])[1]//button");
        item_count += 1;
        String item_count_value = dri.findElement(By.xpath("//span[@class='shopping_cart_badge']")).getText();
        if (!(item_count_value.toString()).equals("2")) {
            dri.navigate().refresh();
            Thread.sleep(1000);
        }
        click("//a[@class='shopping_cart_link']");
        if(!(item_count==Integer.parseInt(item_count_value)))
            throw new Exception("item not matches");
        click("//button[@id='checkout']");
    }
    @Test(priority = 5)
    public static void price_conformation() throws Exception
    {customer_details();
        String s3=dri.findElement(By.xpath("//div[@class='summary_subtotal_label']")).getText();
        String  s4=s3.replace("$","");
        String total_price=s4.replace("Item total: ","");
        double total_cost_price=Double.parseDouble(total_price);
        if(!(Double.compare(total_cost_price,item_1_price+item_2_price)==0))
            throw new Exception("amount is different in total cost");
    }
    @Test(priority = 6)
    public static void finish_checking() throws Exception
    {
        click("//button[@id='finish']");
        String final_message=dri.findElement(By.xpath("//h2[text()='THANK YOU FOR YOUR ORDER']")).getText();
        if(final_message.equals("THANK YOU FOR YOUR ORDER"))
            System.out.println("Order Placed");
    }
    @Test(priority = 7)
    public static void closing() throws Exception
    {
        dri.close();
    }
    public static void click(String x_path) throws Exception{
        dri.findElement(By.xpath(x_path)).click();
        Thread.sleep(1000);
    }
    public static double item_price(String x_path)
    {
        String s1=dri.findElement(By.xpath(x_path)).getText();
        String  price=s1.replace("$","");
        double item_price= Double.parseDouble(price);
        return item_price;
    }
    public static void container(String x_path,String option) throws Exception
    {
        Select list2=new Select(dri.findElement(By.xpath(x_path)));
        list2.selectByVisibleText(option);
        Thread.sleep(1000);
    }
    public static void send_keys(String x_path,String value) throws Exception
    {
        dri.findElement(By.xpath(x_path)).sendKeys(value);
        Thread.sleep(1000);
    }
    public static void customer_details() throws Exception{

        send_keys("//input[@id='first-name']",first_name);
        send_keys("//input[@id='last-name']",last_name);
        send_keys("//input[@id='postal-code']",pin_code);
        click("//input[@id='continue']");
    }
}