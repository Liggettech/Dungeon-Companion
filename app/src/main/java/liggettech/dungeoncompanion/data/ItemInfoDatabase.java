package liggettech.dungeoncompanion.data;

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

import liggettech.dungeoncompanion.models.Armor;
import liggettech.dungeoncompanion.R;
import liggettech.dungeoncompanion.models.Item;
import liggettech.dungeoncompanion.models.Weapon;

// SQLiteOpenHelper helps you open or create a database
public class ItemInfoDatabase  extends SQLiteOpenHelper {

    //Database Meta
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME="DUNGEON_ITEMS";

    //Table names
    private static final String TABLE_ARMOR = "Armor";
    private static final String TABLE_WEAPONS = "Weapons";

    //Shared variables
    private static final String NAME="Name";
    private static final String COST="Cost";
    private static final String WEIGHT="Weight";
    private static final String DESCRIPTION="Description";

    //Armor variables
    private static final String ARMOR_TYPE="Type";
    private static final String ARMOR_CLASS="AC";
    private static final String STRENGTH_REQUIREMENT="StrengthReq";
    private static final String STEALTH_DISADVANTAGE="StealthDis";

    //Weapons variables
    private static final String PROFICIENCY="Proficiency";
    private static final String DAMAGE="Damage";
    private static final String DAMAGE_TYPE="DamageType";
    private static final String PROPERTIES="Properties";

    //Provides access to application-specific resources and classes
    private final Context mHelperContext;

    public ItemInfoDatabase(Context context) {
        //Call use the database or to create it
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mHelperContext = context;
    }

    // onCreate is called the first time the database is created
    public void onCreate(SQLiteDatabase database) {
        String CREATE_ARMOR_TABLE = "CREATE TABLE " + TABLE_ARMOR + " ( " +
                "UID INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " + COST + " INTEGER, " +
                WEIGHT + " INTEGER, " + DESCRIPTION + " TEXT, " +
                ARMOR_TYPE + " TEXT, " + ARMOR_CLASS + " TEXT, " +
                STRENGTH_REQUIREMENT + " INTEGER, " + STEALTH_DISADVANTAGE + " TEXT)";

        String CREATE_WEAPON_TABLE = "CREATE TABLE " + TABLE_WEAPONS + " ( " +
                "UID INTEGER PRIMARY KEY, " +
                NAME + " TEXT, " + COST + " INTEGER, " +
                WEIGHT + " INTEGER, " + DESCRIPTION + " TEXT, " +
                PROFICIENCY + " TEXT, " + DAMAGE + " TEXT, " +
                DAMAGE_TYPE + " TEXT, " + PROPERTIES + " TEXT)";

        // Executes the create query
        database.execSQL(CREATE_ARMOR_TABLE);
        database.execSQL(CREATE_WEAPON_TABLE);

        loadTables();
    }

    //load stored data values for tables
    private void loadTables() {

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

    //loads each individual item into table
    private void loadItems() throws IOException {
        final Resources resources = mHelperContext.getResources();
        InputStream inputStream;

        //loop through data files
        for (int i = 0; i < 2; i++) {

            switch (i) {
                case 0:
                    inputStream = resources.openRawResource(R.raw.armors);
                    break;

                case 1:
                    inputStream = resources.openRawResource(R.raw.weapons);
                    break;

                default:
                    inputStream = null;
                    break;
            }

            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                try {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] strings = TextUtils.split(line, ">");
                        if (strings.length < 2) continue;

                        Item newItem = new Item();
                        newItem.setName(strings[0].trim());
                        newItem.setCost(strings[1].trim());
                        newItem.setWeight(strings[2].trim());
                        newItem.setDescription(strings[3].trim());

                        switch (i) {
                            case 0:
                                Armor newArmor = new Armor(newItem);
                                newArmor.setType(strings[4].trim());
                                newArmor.setAC(strings[5].trim());
                                newArmor.setStrengthRequirement(Integer.parseInt(strings[6].trim()));
                                newArmor.setStealthDisadvantage(strings[7].trim());
                                insertArmor(newArmor);
                                break;

                            case 1:
                                Weapon newWeapon = new Weapon(newItem);
                                newWeapon.setProficiency(strings[4].trim());
                                newWeapon.setDamage(strings[5].trim());
                                newWeapon.setDamageType(strings[6].trim());
                                newWeapon.setProperties(strings[7].trim());
                                insertWeapon(newWeapon);
                                break;

                            default: break;
                        }
                    }
                } finally {
                    reader.close();
                }
            }

            else {
                Log.e("LoadItems", "Error while looping through data files");
            }
        }
    }

    // onUpgrade is used to drop tables, add tables, or do anything that needs upgrading
    public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
        String queryArmor = "DROP TABLE IF EXISTS " + TABLE_ARMOR;
        String queryWeapon = "DROP TABLE IF EXISTS " + TABLE_WEAPONS;

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data
        database.execSQL(queryArmor);
        database.execSQL(queryWeapon);
        onCreate(database);
    }

    public void insertArmor(Armor armor) {

        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        ContentValues values = new ContentValues();

        //values.put("uid", armor.getUid().substring(1));
        values.put(NAME, armor.getName());
        values.put(COST, armor.getCost());
        values.put(WEIGHT, armor.getWeight());
        values.put(DESCRIPTION, armor.getDescription());
        values.put(ARMOR_TYPE, armor.getType());
        values.put(ARMOR_CLASS, armor.getAC());
        values.put(STRENGTH_REQUIREMENT, armor.getStrengthRequirement());
        values.put(STEALTH_DISADVANTAGE, String.valueOf(armor.getStealthDisadvantage()));

        // Inserts the data in the form of ContentValues into the table name provided
        database.insert(TABLE_ARMOR, null, values);

        // Release the reference to the SQLiteDatabase object
        database.close();
    }

    public void insertWeapon(Weapon weapon) {

        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        ContentValues values = new ContentValues();

        //values.put("uid", armor.getUid().substring(1));
        values.put(NAME, weapon.getName());
        values.put(COST, weapon.getCost());
        values.put(WEIGHT, weapon.getWeight());
        values.put(DESCRIPTION, weapon.getDescription());
        values.put(PROFICIENCY, weapon.getProficiency());
        values.put(DAMAGE, weapon.getDamage());
        values.put(DAMAGE_TYPE, weapon.getDamageType());
        values.put(PROPERTIES, weapon.getProperties());

        // Inserts the data in the form of ContentValues into the table name provided
        database.insert(TABLE_WEAPONS, null, values);

        // Release the reference to the SQLiteDatabase object
        database.close();
    }

    public int updateItem(HashMap<String, String> queryValues) {

        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();

        // Stores key value pairs being the column name and the data
        ContentValues values = new ContentValues();

        values.put(NAME, queryValues.get(NAME));
        values.put(DESCRIPTION, queryValues.get(DESCRIPTION));

        // update(TableName, ContentValueForTable, WhereClause, ArgumentForWhereClause)
        return database.update(TABLE_ARMOR, values, "UID" + " = ?", new String[] { queryValues.get("UID") });
    }

    // Used to delete a contact with the matching contactId

    public void deleteItem(String uid) {

        // Open a database for reading and writing
        SQLiteDatabase database = this.getWritableDatabase();

        String deleteQuery = "DELETE FROM  " + TABLE_ARMOR + " where UID='"+ uid +"'";

        // Executes the query provided as long as the query isn't a select
        // or if the query doesn't return any data
        database.execSQL(deleteQuery);
    }

    public ArrayList<HashMap<String, String>> getAllArmor() {

        // ArrayList that contains every row in the database
        // and each row key / value stored in a HashMap
        ArrayList<HashMap<String, String>> contactArrayList;

        contactArrayList = new ArrayList<HashMap<String, String>>();

        String selectQuery = "SELECT  * FROM " + TABLE_ARMOR;

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

    public Armor getArmorInfo(String uid) {
        Armor foundArmor = new Armor();

        // Open a database for reading only
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_ARMOR + " where UID='" + uid.substring(1) + "'";

        // rawQuery executes the query and returns the result as a Cursor
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                foundArmor.setName(cursor.getString(1));
                foundArmor.setCost(cursor.getInt(2));
                foundArmor.setWeight(cursor.getInt(3));
                foundArmor.setDescription(cursor.getString(4));

                foundArmor.setType(cursor.getString(5));
                foundArmor.setAC(cursor.getString(6));
                foundArmor.setStrengthRequirement(cursor.getInt(7));
                foundArmor.setStealthDisadvantage(cursor.getString(8));
            } while (cursor.moveToNext());
        }

        return foundArmor;
    }

    public Weapon getWeaponInfo(String uid) {
        Weapon foundWeapon = new Weapon();

        // Open a database for reading only
        SQLiteDatabase database = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_WEAPONS + " where UID='" + uid.substring(1) + "'";

        // rawQuery executes the query and returns the result as a Cursor
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                foundWeapon.setName(cursor.getString(1));
                foundWeapon.setCost(cursor.getInt(2));
                foundWeapon.setWeight(cursor.getInt(3));
                foundWeapon.setDescription(cursor.getString(4));

                foundWeapon.setProficiency(cursor.getString(5));
                foundWeapon.setDamage(cursor.getString(6));
                foundWeapon.setDamageType(cursor.getString(7));
                foundWeapon.setProperties(cursor.getString(8));
            } while (cursor.moveToNext());
        }

        return foundWeapon;
    }
}