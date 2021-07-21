package org.acme.db.mgmt.v1;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

@ChangeLog(order = "1")
public class DummyUpgrade {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummyUpgrade.class);

    @ChangeSet(order="1", id="DummyUpgrade", author="Sebastien")
    public void test(MongoDatabase db) {
        LOGGER.info("Started");
        MongoCollection<Document> collection = db.getCollection("dummy");
        collection.insertOne(new Document("dummy", UUID.randomUUID()));
        LOGGER.info("Finished");
    }
}
