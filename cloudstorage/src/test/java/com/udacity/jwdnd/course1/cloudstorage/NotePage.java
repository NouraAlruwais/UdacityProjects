package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NotePage {

    @FindBy(id = "nav-notes-tab")
    private WebElement navNoteTab;
    @FindBy(id = "addNewNote")
    private WebElement addNewNote;
    @FindBy(id = "note-title")
    private WebElement noteTitle;
    @FindBy(id = "note-description")
    private WebElement noteDescription;
    @FindBy(id = "noteSave")
    private WebElement noteSave;
    @FindBy(className = "getNoteDescription")
    private WebElement getNoteDescription;
    @FindBy(className = "getNoteTitle")
    private WebElement getNoteTitle;
    @FindBy(id = "editNote")
    private WebElement editNoteButton;
    @FindBy(id = "deleteNote")
    private WebElement deleteNote;

    private final WebDriverWait wait;
    public NotePage(WebDriver webDriver){
        PageFactory.initElements(webDriver, this);
        this.wait = new WebDriverWait(webDriver,20);
    }
    public void NavNoteTab(){
        this.navNoteTab.click();
    }
    public void addNewNoteButton(){
        this.addNewNote=wait.until(ExpectedConditions.elementToBeClickable(By.id("addNewNote")));
        this.addNewNote.click();
    }
    public void createNote(String noteTitle, String noteDescription){
        this.noteTitle=wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.sendKeys(noteDescription);
        this.noteSave.click();
    }

    public Note getNote(){
        this.getNoteTitle=wait.until(ExpectedConditions.elementToBeClickable(By.className("getNoteTitle")));
        Note note=new Note(this.getNoteTitle.getText(),this.getNoteDescription.getText());
        return note;
    }

    public void editNoteTitle(String noteTitle){
        this.noteTitle=wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
    }
    public void editNoteDescription(String noteDescription){
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
    }
    public void editNoteButton(){
        this.editNoteButton.click();
    }
    public void deleteNote(){
        this.deleteNote.click();
    }
    public void noteSaveChanges(){
        this.noteSave.click();
    }

    public boolean findNote(WebDriver webDriver){
       try{

       webDriver.findElement(By.className("getNoteTitle"));
       webDriver.findElement(By.className("getNoteDescription"));
        return true;
        }
        catch (org.openqa.selenium.NoSuchElementException e){
            return false;
       }
    }
}
