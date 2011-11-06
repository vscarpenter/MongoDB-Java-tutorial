package com.j2eegeek.mongodb.client;

import com.mongodb.*;

import java.net.UnknownHostException;

/**
 * The <code>ZipCodeDBInsertDeleteClient</code> class demonstrates some simple insert, update and delete
 * functionality to modify documents in a collection.
 *
 * @author Vinny Carpenter (vscarpenter@nospam.gmail.com)
 * @version 1.0
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
            DBObject dbObject = zipSearchCursor.next();
            System.out.println("dbObject.toString() = " + dbObject.toString());
        }

        zipSearchCursor.close();

        //Let's update the record we just added
        DBObject updatedObject = new BasicDBObject();
        updatedObject.put("city", "VINNY-UPDATED-CITY");
        updatedObject.put("zip", "99999");
        updatedObject.put("loc", "");
        updatedObject.put("pop", "123456");
        updatedObject.put("state", "WI");

        dbCollection.findAndModify(zipQuery, updatedObject);

        //Let's search again for see if the update took.
        DBCursor updatedResults = dbCollection.find(zipQuery);
        while (updatedResults.hasNext()) {
            System.out.println("Updated Results = " + updatedResults.next());
        }

        //now let's delete that document/record.
        BasicDBObject itemsToDeleteQuery = new BasicDBObject("zip", "99999");
        DBObject itemsBeingRemoved = dbCollection.findAndRemove(itemsToDeleteQuery);
        System.out.println("Item being deleted = " + itemsBeingRemoved.toString());

        //Close the underlying connection.
        mongo.close();

    }
}
