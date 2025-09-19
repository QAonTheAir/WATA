package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MailsacPage extends BasePage {
    public MailsacPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "field_a1xtj")
    public WebElement txtEmail;

    /**
     * Get the first message details (Sender, Subject, Time) from the inbox table
     */
    public String[] getFirstMessage(WebDriver driver) {
        // Locate the first row in the table
        WebElement firstRow = driver.findElement(By.cssSelector("tr[ng-repeat='msg in messages']"));

        // Extract details
        String sender = firstRow.findElement(By.cssSelector("td.col-xs-4 strong")).getText();
        String subject = firstRow.findElement(By.cssSelector("td.col-xs-5 .inbox-subject")).getText();
        String time = firstRow.findElement(By.cssSelector("td.col-xs-2 span")).getText();

        return new String[] { sender, subject, time };
    }
    /**
     * Open the first message by clicking it
     */
    public void openFirstMessage(WebDriver driver) {
        WebElement firstRow = driver.findElement(By.cssSelector("tr[ng-repeat='msg in messages']"));
        firstRow.click();
    }
    /**
     * Get the subject of the first message in the inbox
     */
    public String getFirstMessageSubject(WebDriver driver) {
        try {
            WebElement firstRow = driver.findElement(By.cssSelector("tr[ng-repeat='msg in messages']"));
            return firstRow.findElement(By.cssSelector("td.col-xs-5 .inbox-subject")).getText();
        }catch (NoSuchElementException e){
            return "";
        }

    }
}
