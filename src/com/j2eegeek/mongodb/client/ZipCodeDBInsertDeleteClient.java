package com.j2eegeek.mongodb.client;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * Created by IntelliJ IDEA.
 * User: vcarpenter
 * Date: 11/5/11
 * Time: 9:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class ZipCodeDBInsertDeleteClient {

    public static void main(String[] args) throws UnknownHostException {
        Mongo mongo = new Mongo("localhost", 27017);
        DB zipCodeDB = mongo.getDB("ZipCodes");

        DBCollection dbCollection = zipCodeDB.getCollection("zips");

        BasicDBObject zipCodeEntry = new BasicDBObject();
        zipCodeEntry.put("city", "VINNY");
        zipCodeEntry.put("zip", "99999");
        zipCodeEntry.put("loc", "");
        zipCodeEntry.put("pop", "12345");
        zipCodeEntry.put("state", "WI");
        WriteResult insertResult = dbCollection.insert(zipCodeEntry);

        if (insertResult.getError() != null) {
            System.out.println("insertResult.getError() = " + insertResult.getError());
        } else {
            System.out.println("No errors inserting a new document into the collection");
        }

        //Let's search for the zip code we just entered.
        BasicDBObject zipQuery = new BasicDBObject("zip", "99999");

        DBCursor zipSearchCursor = dbCollection.find(zipQuery);
        while (zipSearchCursor.hasNext()) {
            System.out.println("zipSearchCursor.next() = " + zipSearchCursor.next());
        }

        zipSearchCursor.close();



    }
}
