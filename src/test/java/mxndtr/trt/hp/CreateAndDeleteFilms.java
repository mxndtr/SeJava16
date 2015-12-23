    package mxndtr.trt.hp;

        import org.openqa.selenium.support.ui.ExpectedConditions;
        import org.openqa.selenium.support.ui.WebDriverWait;
        import org.testng.Assert;
        import org.testng.annotations.*;
        import org.openqa.selenium.*;

    public class CreateAndDeleteFilms extends TestBase {
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();
    private String newFilmIMDb = "1234";
    private String newFilmTitle = "NewFilm";
    private String newFilmYear = "1991";
    private int filmsCount;

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

    //Попытка добавления некорректного фильма
    @Test(priority = 0)
    public void addNoValidFilm() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.cssSelector(".button>div>a>img")).get(0)));
        WebElement addMovieButton = driver.findElements(By.cssSelector(".button>div>a>img")).get(0);
        addMovieButton.click();
        WebElement fieldIMDbNumber =  driver.findElement(By.cssSelector("#updateform")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1).findElement(By.className("digits"));
        WebElement fieldTitle =  driver.findElement(By.name("name"));
        WebElement addFilmButton = driver.findElement(By.id("submit"));
        WebElement errorLabelYear = driver.findElement(By.cssSelector("#updateform")).findElements(By.tagName("tr")).get(3).findElements(By.tagName("td")).get(1).findElements(By.className("error")).get(1);
        fieldIMDbNumber.sendKeys(newFilmIMDb);
        fieldTitle.sendKeys(newFilmTitle);
        addFilmButton.click();
        Assert.assertEquals("", errorLabelYear.getAttribute("style"));
    }

    //Корректное добавление фильма
    @Test(priority = 1)
    public void addFilm() throws Exception {
        driver.findElements(By.cssSelector(".button>div>a>img")).get(0).click();
        WebElement fieldIMDbNumber =  driver.findElement(By.cssSelector("#updateform")).findElements(By.tagName("tr")).get(0).findElements(By.tagName("td")).get(1).findElement(By.className("digits"));
        WebElement fieldTitle =  driver.findElement(By.name("name"));
        WebElement fieldYear =  driver.findElement(By.name("year"));
        WebElement addFilmButton = driver.findElement(By.id("submit"));
        WebElement errorLabelTitle = driver.findElement(By.cssSelector("#updateform")).findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).get(1).findElements(By.className("error")).get(1);
        WebElement errorLabelYear = driver.findElement(By.cssSelector("#updateform")).findElements(By.tagName("tr")).get(3).findElements(By.tagName("td")).get(1).findElements(By.className("error")).get(1);
        fieldIMDbNumber.sendKeys(newFilmIMDb);
        fieldTitle.sendKeys(newFilmTitle);
        fieldYear.sendKeys(newFilmYear);
        Assert.assertEquals("display: none;", errorLabelYear.getAttribute("style"));
        Assert.assertEquals("display: none;", errorLabelTitle.getAttribute("style"));
        addFilmButton.click();
        Assert.assertEquals(newFilmTitle+" ("+newFilmYear+")", driver.findElement(By.cssSelector(".maininfo_full>h2")).getText());
    }
    //Удаление фильма
    @Test(priority = 2)
    public void delFilm() throws Exception {
        filmsCount = driver.findElements(By.className("movie_box")).size();
        driver.findElements(By.className("movie_box")).get(0).click();
        driver.findElements(By.cssSelector(".button>div>a>img")).get(4).click();
        Thread.sleep(100);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        Assert.assertEquals(filmsCount - 1, driver.findElements(By.className("movie_box")).size());
    }

    public void loginUser(String userName, String userPassword){
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("username")).sendKeys(userName);
        driver.findElement(By.name("password")).clear();
        driver.findElement(By.name("password")).sendKeys(userPassword);
        driver.findElement(By.name("submit")).click();
    }

}