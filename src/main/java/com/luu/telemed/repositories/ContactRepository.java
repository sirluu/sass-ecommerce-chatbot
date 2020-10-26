package com.luu.telemed.repositories;



import org.springframework.data.mongodb.repository.MongoRepository;

import com.luu.telemed.models.Contact;


public interface ContactRepository extends MongoRepository<Contact, String> {


}
