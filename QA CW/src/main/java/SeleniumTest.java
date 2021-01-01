import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class SeleniumTest {

    WebDriver driver = new ChromeDriver();

    @Test
    public void  automationTask(){

        System.out.println("Test Started");

        System.setProperty("webdriver.chrome.driver","ChromeDriver/chromedriver.exe");

        // Going to the given website
        driver.get("http://www.saucedemo.com/");
        // Filling username and password
        txtboxfill("user-name","standard_user");
        txtboxfill("password","secret_sauce");
        // Clicking the login button
        btnClickId("login-button");

        // Verifying the url
        checkUrl("https://www.saucedemo.com/inventory.html");

        // Clicking on a product image (Sauce Labs Bolt T-Shirt  $15.99)
        btnClickXpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[3]/div[1]/a/img");


        // Verifying the product name and price
        productNameCss("div > div.inventory_details_name");
        String productnametxt = driver.findElement(By.cssSelector("div > div.inventory_details_name")).getText();
        productPriceCss("div > div.inventory_details_price");
        String productpricetxt = driver.findElement(By.cssSelector("div > div.inventory_details_price")).getText();

        // Clicking on add to cart
        btnClickXpath("//div/div[2]/div[2]/div/div/div/button");

        // Clicking on shopping cart icon
        btnClickCss("svg > path");

        // Verifying the url
        checkUrl("https://www.saucedemo.com/cart.html");

        // Verifying the product name and price
        verifyProductNameXpath(productnametxt,"/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/a/div");
        verifyProductPriceCss(productpricetxt,"div.item_pricebar > div");

        // Clicking on remove button
        btnClickXpath("/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/div[2]/button");

        // Verifying the product is no longer in the cart
        try{
            productNameXpath("/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/a/div");
            System.out.println("Product is not removed");
        } catch (Exception exception){
            System.out.println("Product is removed");
        }

        // Clicking on continue button
        btnClickXpath("/html/body/div/div[2]/div[3]/div/div[2]/a[1]");

        // Adding two products to the cart
        btnClickXpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/button");
        btnClickXpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[4]/div[3]/button");

        // getting element names to verify
        String productname1 = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[2]/a/div")).getText();
        String productprice1 = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[1]/div[3]/div")).getText();
        String productname2 = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[4]/div[2]/a/div")).getText();
        String productprice2 = driver.findElement(By.xpath("/html/body/div/div[2]/div[2]/div/div[2]/div/div[4]/div[3]/div")).getText();

        // Verifying added products in cart
        btnClickCss("svg > path");
        verifyProductNameXpath(productname1, "/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/a/div");
        verifyProductPriceXpath(productprice1, "/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/div[2]/div");
        verifyProductNameXpath(productname2, "/html/body/div/div[2]/div[3]/div/div[1]/div[4]/div[2]/a/div");
        verifyProductPriceXpath(productprice2, "/html/body/div/div[2]/div[3]/div/div[1]/div[4]/div[2]/div[2]/div");


        // Clicking on checkout button
        btnClickCss("div.cart_footer > a.btn_action.checkout_button");

        // Filling text fields using appropriate data
        txtboxfill("first-name","Johnny");
        txtboxfill("last-name","Depp");
        txtboxfill("postal-code", "20508");

        // Clicking on continue button
        btnClickCss("div.checkout_buttons > input");

        // Verifying the total is correct or not
        total();


        // Clicking on Finish
        btnClickXpath("/html/body/div/div[2]/div[3]/div/div[2]/div[8]/a[2]");

        // Verifying the thank you text
        verifyText("/html/body/div/div[2]/div[3]/h2");

        // Verifying the url
        checkUrl("https://www.saucedemo.com/checkout-complete.html");

        System.out.println("Test Finished");

        driver.close();

    }

    // total function is to testing whether this total is correct or not
    private void total(){
        String Text1 = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[1]/div[3]/div[2]/div[2]")).getText();
        String Text2 = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[1]/div[4]/div[2]/div[2]")).getText();
        String Text3 = driver.findElement(By.xpath("/html/body/div/div[2]/div[3]/div/div[2]/div[5]")).getText();

        // replacing the '$' in the text and replacing it with ' ' and finally removing the whitespaces using trim function
        double num1 = Double.parseDouble((Text1.replace('$',' ')).trim());
    //String[] n=Text1.split("$");


        double num2 = Double.parseDouble((Text2.replace('$',' ')).trim());
        double num3 = Double.parseDouble((Text3.replaceAll("Item total: "," ").replace('$',' ')).trim());

        double total = num1 + num2;

        if (total == num3){
            System.out.println("Total is Correct");
        } else{
            System.out.println("Total is InCorrect");
        }
    }

    // Button click function by xpath
    private void btnClickXpath(String xpath){
        WebElement Button = driver.findElement(By.xpath(xpath));
        Button.click();
    }

    // Button click function by css selector
    private void btnClickCss(String cssSelector){
        WebElement Button = driver.findElement(By.cssSelector(cssSelector));
        Button.click();
    }

    // Button click function by id
    private void btnClickId(String id){
        WebElement Button = driver.findElement(By.id(id));
        Button.click();
    }

    // Check URL function
    private void checkUrl(String URL){
        String url = driver.getCurrentUrl();
        String givenURL = URL;
        System.out.println("Verifying the url...");
        Assert.assertEquals(url,givenURL);
        System.out.println("URL verified");
    }

    // Verifying Product name function using xpath
    private  void productNameXpath(String xpath){
        WebElement Productname = driver.findElement(By.xpath(xpath));
        System.out.println("Verifying product price...");
        Assert.assertTrue(Productname.isDisplayed());
        System.out.println("Product price verified");
    }

    // Verifying Product name function using css
    private  void productNameCss(String cssSelector){
        WebElement Productname = driver.findElement(By.cssSelector(cssSelector));
        System.out.println("Verifying product name...");
        Assert.assertTrue(Productname.isDisplayed());
        System.out.println("Product name verified");
    }

    // Verifying Text function using xpath
    private  void verifyText(String xpath){
        WebElement Text = driver.findElement(By.xpath(xpath));
        System.out.println("Verifying text...");
        Assert.assertTrue(Text.isDisplayed());
        System.out.println("Text verified");
    }

    // Verifying Product Price function using Css
    private  void productPriceCss(String cssSelector){
        WebElement Productprice = driver.findElement(By.cssSelector(cssSelector));
        System.out.println("Verifying product price...");
        Assert.assertTrue(Productprice.isDisplayed());
        System.out.println("Product price verified");
    }

    // Filling Text boxes function using id
    private void txtboxfill(String id, String value){
        WebElement textElement = driver.findElement(By.id(id));
        textElement.sendKeys(value);
    }

    // Verifying Product Name function using Xpath (this function is to compare elements from previous page)
    private  void verifyProductNameXpath(String productnametxt, String xpath){
        String newproductname = driver.findElement(By.xpath(xpath)).getText();
        if (newproductname.equals(productnametxt)){
            System.out.println("Product name is correct");
        } else{
            System.out.println("Product name is incorrect");
        }
    }

    // Verifying Product Price function using CSS (this function is to compare elements from previous page)
    private  void verifyProductPriceCss(String productpricetxt, String cssSelector){
        String newproductprice = driver.findElement(By.cssSelector(cssSelector)).getText();
        if (newproductprice.equals((productpricetxt.replace('$',' ')).trim())){
            System.out.println("Product price is correct");
        } else{
            System.out.println("Product price is incorrect");
        }
    }

    // Verifying Product Price function using Xpath (this function is to compare elements from previous page)
    private  void verifyProductPriceXpath(String productpricetxt, String xpath){
        String newproductprice = driver.findElement(By.xpath(xpath)).getText();
        // replacing the '$' in the text and replacing it with ' ' and finally removing the whitespaces using trim function
        if (newproductprice.equals((productpricetxt.replace('$',' ')).trim())){
            System.out.println("Product price is correct");
        } else{
            System.out.println("Product price is incorrect");
        }
    }

}