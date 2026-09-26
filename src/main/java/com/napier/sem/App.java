package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class App
{

    public static void main(String[] args)
    {

        //connect to MongoDB on local system
        MongoClient mongoClient = new MongoClient("mongo-dbserver");

        //"get" a database
        MongoDatabase database = mongoClient.getDatabase("mydb");

        //"get a collection from said database
        MongoCollection<Document> collection = database.getCollection("test");

        //create a document
        Document doc = new Document("name", "Kevin Sim")
                .append("class", "DevOps")
                .append("year", "2024")
                .append("result", new Document("CW,", "95").append("EX", 85));

        //add document to collection
        collection.insertOne(doc);

        //check document is in collection
        Document myDoc = collection.find().first();
        System.out.println(myDoc.toJson());



        /*
        // old hello world code
        System.out.printf("Hello and welcome!");
        for (int i = 1; i <= 5; i++)
        {
            System.out.println("i = " + i);
        }
        */

    }

}