package com.luu.telemed.exceptions;

import java.util.Date;



/**
 * 
 * @author HungLQ7130
 *
 */
public class ErrorDetails {

  private Date timestamp;
  private String message;
  private String details;

  public ErrorDetails(Date timestamp, String message, String details) {

    super();
    this.timestamp = timestamp;
    this.message = message;
    this.details = details;
  }

  /**
   * @return date
   */
  public Date getTimestamp() {
    return timestamp;
  }

  /**
   * @return message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @return details
   */
  public String getDetails() {
    return details;
  }

}
