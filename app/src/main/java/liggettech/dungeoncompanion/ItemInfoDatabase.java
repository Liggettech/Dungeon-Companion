package liggettech.dungeoncompanion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import static android.content.ContentValues.TAG;

// SQLiteOpenHelper helps you open or create a database

public class ItemInfoDatabase  extends SQLiteOpenHelper {

    // Context : provides access to application-specific resources and classes

    private final Context mHelperContext;

    public ItemInfoDatabase(Context context) {

        // Call use the database or to create it
        super(context, "armor.db", null, 1);
        mHelperContext = context;
    }

    // onCreate is called the first time the database is created

    public void onCreate(SQLiteDatabase database) {

        // How to create a table in SQLite
        // Make sure you don't put a ; at the end of the query

        String query = "CREATE TABLE armor ( uid INTEGER PRIMARY KEY, name TEXT, " +
                "description TEXT)";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(query);
        loadTable();
    }

    // onUpgrade is used to drop tables, add tables, or do anything
    // else it needs to upgrade
    // This is dropping the table to delete the data and then calling
    // onCreate to make an empty table

    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String query = "DROP TABLE IF EXISTS armor";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(query);
        onCreate(database);
    }

    public void insertItem(Armor armor) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        // ContentValues data type is needed because the database
        // requires its data type to be passed

        ContentValues values = new ContentValues();

        //values.put("uid", armor.getUid().substring(1));
        values.put("name", armor.getName());
        values.put("description", armor.getDescription());

        // Inserts the data in the form of ContentValues into the
        // table name provided

        database.insert("armor", null, values);

        Log.e("insertItem", "Successfully inserted: " + armor.getName() + ", " + armor.getDescription());
        // Release the reference to the SQLiteDatabase object

        database.close();
    }

    public int updateItem(HashMap<String, String> queryValues) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data

        ContentValues values = new ContentValues();

        values.put("name", queryValues.get("name"));
        values.put("description", queryValues.get("description"));

        // update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)

        return database.update("armor", values, "uid" + " = ?", new String[] { queryValues.get("uid") });
    }

    // Used to delete a contact with the matching contactId

    public void deleteItem(String uid) {

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM  armor where uid='"+ uid +"'";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data

        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllArmor() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap

        ArrayList<HashMap<String, String>> contactArrayList;

        contactArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM armor";

        // Open a database for reading and writing

        SQLiteDatabase database = this.getWritableDatabase();

        // Cursor provides read and write access for the
        // data returned from a database query

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);

        // Move to the first row

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contactMap = new HashMap<String, String>();

                // Store the key / value pairs in a HashMap
                // Access the Cursor data by index that is in the same order
                // as used when creating the table

                contactMap.put("uid", cursor.getString(0));
                contactMap.put("name", cursor.getString(1));
                contactMap.put("description", cursor.getString(2));

                contactArrayList.add(contactMap);
            } while (cursor.moveToNext()); // Move Cursor to the next row
        }

        // return contact list
        return contactArrayList;
    }

    public Armor getItemInfo(String uid) {
        Armor contactMap = new Armor();

        // Open a database for reading only

        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM armor where uid='" + uid.substring(1) + "'";

        // rawQuery executes the query and returns the result as a Cursor

        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                contactMap.setName(cursor.getString(1));
                contactMap.setDescription(cursor.getString(2));

            } while (cursor.moveToNext());
        }
        return contactMap;
    }

    private void loadTable() {

        Log.e("loadTable", "hello");

        new Thread(new Runnable() {
            public void run() {
                try {
                    loadItems();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void loadItems() throws IOException {
        final Resources resources = mHelperContext.getResources();
        InputStream inputStream = resources.openRawResource(R.raw.armors);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] strings = TextUtils.split(line, ",");
                if (strings.length < 2) continue;

                Armor newArmor = new Armor();
                newArmor.setName(strings[0].trim());
                newArmor.setDescription(strings[1].trim());
                Log.e("LoadItems", "Grabbed: " + strings[0].trim() + " " + strings[1].trim());

                insertItem(newArmor);
            }
        } finally {
            reader.close();
        }
    }
}