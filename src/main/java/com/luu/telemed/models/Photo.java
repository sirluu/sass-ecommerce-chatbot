package com.luu.telemed.models;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * @author HungLQ7130
 *
 */
@Document(collection = "photos")
public class Photo {
  @Id
  private String id;

  private Binary image;

  public Binary getImage() {
    return image;
  }

  public void setImage(Binary image) {
    this.image = image;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

}
