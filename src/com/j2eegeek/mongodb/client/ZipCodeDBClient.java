package com.j2eegeek.mongodb.client;

import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * The <code>zipCodeDBClient</code> is a simple Java class to demonstrate connectivity to a MongoDB database
 * with some simple search operations.
 *
 * @author Vinny Carpenter (vscarpenter@nospam.gmail.com)
 * @version 1.0
 */
public class ZipCodeDBClient {

    public static void main(String[] args) throws UnknownHostException {

        Mongo mongo = new Mongo("localhost", 27017);
        DB zipCodeDB = mongo.getDB("ZipCodes");

        //Let's get all of the collections in the db
        Set<String> dbCollections = zipCodeDB.getCollectionNames();

        for (String s : dbCollections) {
            System.out.println(s);
        }

        DBCollection dbCollection = zipCodeDB.getCollection("zips");
        System.out.println("dbCollection.count() = " + dbCollection.count());
        System.out.println("dbCollection.getFullName() = " + dbCollection.getFullName());

        //Let's use the find() method to return a cursor and iterate over all the documents in this collection.
        DBCursor dbCursor = dbCollection.find();
        System.out.println("dbCursor.size() = " + dbCursor.size());

        while (dbCursor.hasNext()) {
            System.out.println("dbCursor.next() = " + dbCursor.next());
        }

        dbCursor.close();

        //Let's search by specific zip code
        BasicDBObject zipQuery = new BasicDBObject("zip", "53045");

        DBCursor zipSearchCursor = dbCollection.find(zipQuery);
        while (zipSearchCursor.hasNext()) {
            System.out.println("zipSearchCursor.next() = " + zipSearchCursor.next());
        }

        zipSearchCursor.close();

        //Let's search using the name of a city.
        BasicDBObject cityQuery = new BasicDBObject("city", "NEW YORK");
        DBCursor citySearchCursor = dbCollection.find(cityQuery);

        System.out.println("citySearchCursor.size() = " + citySearchCursor.size());
        while (citySearchCursor.hasNext()) {
            System.out.println("citySearchCursor.next() = " + citySearchCursor.next());
        }

        citySearchCursor.close();

        //Close the underlying connection.
        mongo.close();
    }
}
