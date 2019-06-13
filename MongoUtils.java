import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class MongoUtils {

    // ======== Pre-fill in for testing =========
    private static String hostName = "";
    private static int port;
    private static String userName = "";
    private static String source = "";
    private static String password = "";
    private static String dbName = "";
    // ======== Pre-fill in for testing =========

    public static MongoDatabase connect() {
        List<ServerAddress> adds = new ArrayList<>();

        ServerAddress serverAddress = new ServerAddress(hostName,port);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName,source,password.toCharArray());
        credentials.add(mongoCredential);

        MongoClient mongoClient = new MongoClient(adds, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(dbName);
        return mongoDatabase;
    }

    public static MongoDatabase customConnect(String h, int p, String u, String s, String pw, String db) {
        List<ServerAddress> adds = new ArrayList<>();

        ServerAddress serverAddress = new ServerAddress(h,p);
        adds.add(serverAddress);

        List<MongoCredential> credentials = new ArrayList<>();
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(u,s,pw.toCharArray());
        credentials.add(mongoCredential);

        MongoClient mongoClient = new MongoClient(adds, credentials);
        MongoDatabase mongoDatabase = mongoClient.getDatabase(db);
        return mongoDatabase;
    }

    public List<Document> findAll(MongoDatabase mongoDatabase, String collectionName) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        List<Document> list = new ArrayList<>();
        FindIterable findIterable = collection.find();
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            Document x = (Document)cursor.next();
            list.add(x);
        }
        return list;
        //  loop through the list and call .get("memberName") to access member variables
        /*  List<List> y = (List<List>)x.get("data");
            for (int i = 0; i < y.size(); ++i) {
                List<Object> temp = new ArrayList<>();
                Long z1 = (long) y.get(i).get(0);
                String z2 = (String) y.get(i).get(1);
                temp.add(z1);
                temp.add(z2);
                list.add(temp);
            }
        */
    }

    public List<Document> findFilter(MongoDatabase mongoDatabase, String collectionName, String filterName, String filterValue) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        Bson filter = Filters.eq(filterName, filterValue);
        List<Document> list = new ArrayList<>();
        FindIterable findIterable = collection.find(filter);
        MongoCursor cursor = findIterable.iterator();
        while (cursor.hasNext()) {
            Document x = (Document) cursor.next();
            list.add(x);
        }
        return list;
        //  loop through the list and call .get("memberName") to access member variables
        /*  List<List> y = (List<List>)x.get("data");
            for (int i = 0; i < y.size(); ++i) {
                List<Object> temp = new ArrayList<>();
                Long z1 = (long) y.get(i).get(0);
                String z2 = (String) y.get(i).get(1);
                temp.add(z1);
                temp.add(z2);
                list.add(temp);
            }
        */
    }

    public Document findFirst(MongoDatabase mongoDatabase, String collectionName) {
        MongoCollection<Document> collection = mongoDatabase.getCollection(collectionName);
        FindIterable findIterable = collection.find();
        Document document = (Document) findIterable.first();
        return document;
    }
}
