package com.futurecollars.accounting.service;

public enum MailMessage {
  ADDED("New", "<b>New Invoice</b> has been added to the system."),
  DELETED("Deleted", "<b>Invoice was deleted</b> from the system."),
  MODIFIED("Modified", "<b>Invoice was modified</b> in the system.");

  private String titlePart;
  private String message;

  MailMessage(String titlePart, String message) {
    this.titlePart = titlePart;
    this.message = message;
  }

  public String getTitlePart() {
    return titlePart;
  }

  public String getMessage() {
    return message;
  }
}
