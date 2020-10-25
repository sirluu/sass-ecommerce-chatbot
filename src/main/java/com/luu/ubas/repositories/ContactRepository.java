package com.luu.ubas.repositories;



import org.springframework.data.mongodb.repository.MongoRepository;
import com.luu.ubas.models.Contact;


public interface ContactRepository extends MongoRepository<Contact, String> {


}
