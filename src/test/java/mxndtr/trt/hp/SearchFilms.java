package mxndtr.trt.hp;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by mxndt on 24.12.2015.
 */
public class SearchFilms extends TestBase {

    @BeforeMethod
    public void openAndLogin(){
        driver.manage().window().maximize();
        driver.get(baseUrl + "/php4dvd/");
        loginUser("admin", "admin");
    }

    @AfterMethod
    public void logOut() throws InterruptedException {
        driver.findElements(By.cssSelector(".center>nav>ul>li>a")).get(3).click();
        Thread.sleep(100);
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    @Test(priority = 0)
    public void FindFilm() throws InterruptedException {
        driver.findElement(By.className("inputDefault")).sendKeys("NewFilm" + Keys.RETURN);
        Thread.sleep(100);
        Assert.assertEquals("NewFilm", driver.findElements(By.className("movie_box")).get(0).findElement(By.className("title")).getText());
    }

    @Test(priority = 1)
    public void FindMissingFilm() throws InterruptedException {
        driver.findElement(By.id("q")).clear();
        driver.findElement(By.id("q")).sendKeys("MissingFilm" + Keys.RETURN);
        Thread.sleep(100);
        Assert.assertEquals("No movies where found.", driver.findElement(By.className("content")).getText());
    }




    public void loginUser(String userName, String userPassword){
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(userPassword);
        driver.findElement(By.name("submit")).click();
    }
}
