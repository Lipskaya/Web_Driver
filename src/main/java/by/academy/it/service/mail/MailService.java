package by.academy.it.service.mail;

import static by.academy.it.page.Constants.DRAFT_ADDRESS;
import static by.academy.it.page.Constants.EMPTY;
import static by.academy.it.page.Constants.LOGIN;
import static by.academy.it.page.Constants.TEXT;
import static by.academy.it.page.Constants.TOPIC;

import by.academy.it.model.Letter;
import by.academy.it.page.mail.MailPage;

public class MailService {

  private MailPage mailPage;
  private Letter validLetter;
  private Letter noSubjectNoBodyLetter;
  private Letter draftLetter;
  private Letter noAddressLetter;

  public MailService() {
    mailPage = new MailPage();

    validLetter = new Letter();
    validLetter.setAddress(LOGIN);
    validLetter.setTopic(TOPIC);
    validLetter.setMessage(TEXT);

    noSubjectNoBodyLetter = new Letter();
    noSubjectNoBodyLetter.setAddress(LOGIN);
    noSubjectNoBodyLetter.setTopic(EMPTY);
    noSubjectNoBodyLetter.setMessage(EMPTY);

    draftLetter = new Letter();
    draftLetter.setAddress(DRAFT_ADDRESS);
    draftLetter.setTopic(EMPTY);
    draftLetter.setMessage(EMPTY);

    noAddressLetter = new Letter();
    noAddressLetter.setAddress(EMPTY);
    noAddressLetter.setTopic(TOPIC);
    noAddressLetter.setMessage(TEXT);
  }

  public void sendNoAddressNewMail() {
    mailPage.sendNewMail(noAddressLetter);
  }

  public void sendNewNoSubjectNoBodyMail() {
    mailPage.sendNewMail(noSubjectNoBodyLetter);
  }

  public void sendNewValidMail() {
    mailPage.sendNewMail(validLetter);
  }

  public void goToSentEmails() {
    mailPage.goToSentEmails();
  }

  //проверяем есть ли письмо от указанного адреса
  public boolean isMailExist(String addres) {
    return mailPage.isMailExist(addres);
  }

  public void deleteAllLetters() {
    mailPage.deleteAllLetters();
  }

  public void goToInboxEmails() {
    mailPage.goToInboxEmails();
  }

  public void closeNewEmailDialog() {
    mailPage.closeNewEmailDialog();
  }

  //заходим папку Черновики
  public void goToDraftEmails() {
    mailPage.goToDraftEmails();
  }

  //заходим папку Удаленные
  public void goToTrashEmails() {
    mailPage.goToTrashEmails();
  }

  // создает и сохраняет черновик письма
  public void createDraftMail() {
    mailPage.createDraftMail(draftLetter.getAddress(), draftLetter.getTopic(),
        draftLetter.getMessage());
    closeNewEmailDialog();
  }

  public String getEmtyFolderMessage() {
    return mailPage.getEmtyFolderMessage();
  }

  public String getExpectedEmtyFolderMessage() {
    return "В корзине пусто";
  }

  public String getNoAddressErrorMessage() {
    return mailPage.getNoAddressErrorMessage();
  }

  public String getExpectedNoAddressErrorMessage() {
    return "Не указан адрес получателя";
  }
}
