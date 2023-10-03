package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CredentialPage {

    @FindBy(id = "nav-credentials-tab")
    private WebElement navCredentialsTab;
    @FindBy(id = "addNewCredential")
    private WebElement addNewCredentialButton;
    @FindBy(id = "editCredential")
    private WebElement editCredentialButton;
    @FindBy(id = "deleteCredential")
    private WebElement deleteCredentialButton;
    @FindBy(className = "#getCredentialUrl")
    private WebElement getCredentialUrl;
    @FindBy(id = "getCredentialUsername")
    private WebElement getCredentialUsername;
    @FindBy(id = "getCredentialPassword")
    private WebElement getCredentialPassword;
    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;
    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;
    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;
    @FindBy(id = "credentialSave")
    private WebElement credentialSaveButton;
    private final WebDriverWait wait;
    public CredentialPage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.wait=new WebDriverWait(webDriver,20);
    }

    public void navCredentialsTab(){
        this.navCredentialsTab.click();
    }
    public void addNewCredentialButton(){
        this.addNewCredentialButton=wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewCredential")));
        this.addNewCredentialButton.click();
    }
    public void createCredential(String url, String username, String password){
       this.credentialUrlField= wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        this.credentialUrlField.sendKeys(url);
        this.credentialUsernameField.sendKeys(username);
        this.credentialPasswordField.sendKeys(password);
        this.credentialSaveButton.click();
    }
    public Credential getCredential(){
        this.getCredentialUrl=wait.until(ExpectedConditions.elementToBeClickable(By.className("getCredentialUrl")));
        Credential credential=new Credential(this.getCredentialUrl.getText(),this.getCredentialUsername.getText(),this.getCredentialPassword.getText());
        return credential;
    }
    public String verifyPassword(){
        this.credentialPasswordField=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
        return this.credentialPasswordField.getAttribute("value");
    }
    public void editCredentialButton(){
        this.editCredentialButton=wait.until(ExpectedConditions.elementToBeClickable(By.id("editCredential")));
        this.editCredentialButton.click();
    }
    public void editCredentialUrl(String url){
        this.credentialUrlField=wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        this.credentialUrlField.clear();
        this.credentialUrlField.sendKeys(url);
    }

    public void editCredentialUsername(String username){
        this.credentialUsernameField=wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-username")));
        this.credentialUsernameField.clear();
        this.credentialUsernameField.sendKeys(username);
    }
    public void editCredentialPassword(String password){
        this.credentialPasswordField=wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-password")));
        this.credentialPasswordField.clear();
        this.credentialPasswordField.sendKeys(password);
    }
    public void credentialSaveButton(){
        this.credentialSaveButton.click();
    }
    public void deleteCredentialButton(){
        this.deleteCredentialButton.click();
    }
    public boolean findCredential(WebDriver webDriver){
        try {
            webDriver.findElement(By.className("getCredentialUrl"));
            webDriver.findElement(By.id("getCredentialUsername"));
            webDriver.findElement(By.id("getCredentialPassword"));
            return true;
        }

        catch (org.openqa.selenium.NoSuchElementException e){
            return false;
    }
    }
}
