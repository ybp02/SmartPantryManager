package com.example.smartpantrymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "SmartPantry.db";
    private static final int DATABASE_VERSION = 2; // WE BUMPED THIS TO 2

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Pantry Table
        db.execSQL("CREATE TABLE pantry_items (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity TEXT)");

        // 2. Recipes Table
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, ingredients TEXT, steps TEXT)");

        // 3. Pre-load Recipes (Seed Data)
        seedRecipes(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // If you run the app again on the emulator, this will wipe the old DB and create the new one
        db.execSQL("DROP TABLE IF EXISTS pantry_items");
        db.execSQL("DROP TABLE IF EXISTS recipes");
        onCreate(db);
    }

    // --- PANTRY CRUD ---
    public void addPantryItem(String name, String quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("quantity", quantity);
        db.insert("pantry_items", null, values);
        db.close();
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> itemList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM pantry_items", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String qty = cursor.getString(cursor.getColumnIndexOrThrow("quantity"));
                itemList.add(new PantryItem(name, qty));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return itemList;
    }

    // --- RECIPE LOGIC ---
    private void seedRecipes(SQLiteDatabase db) {
        // We use a special format: "ingredient:quantity" separated by commas
        // Example: "pasta:200,tomatoes:2,garlic:1"

        addRecipe(db, "Tomato Pasta", "pasta:200,tomatoes:2,garlic:1", "Boil pasta. Fry garlic and tomatoes. Mix together.");
        addRecipe(db, "Garlic Bread", "bread:4,butter:50,garlic:2", "Mix butter and garlic. Spread on bread. Bake for 10 mins.");
        addRecipe(db, "Omelette", "eggs:3,milk:50,cheese:30", "Whisk eggs and milk. Cook in pan. Add cheese.");
        addRecipe(db, "Simple Salad", "lettuce:1,tomatoes:2,cucumber:1", "Chop everything and mix.");
        addRecipe(db, "Pancakes", "flour:200,milk:150,eggs:2", "Mix into batter. Fry in a pan.");
        // Add a few more to reach the 15-20 mark for your assignment
        addRecipe(db, "Cheese Sandwich", "bread:2,cheese:2,butter:10", "Butter bread, add cheese, toast.");
        addRecipe(db, "Scrambled Eggs", "eggs:2,butter:10,milk:20", "Whisk and cook in pan with butter.");
        addRecipe(db, "Pasta with Butter", "pasta:200,butter:30,cheese:20", "Boil pasta, mix with butter and cheese.");
        addRecipe(db, "Tomato Soup", "tomatoes:4,onion:1,garlic:1", "Boil and blend all ingredients.");
        addRecipe(db, "French Toast", "bread:2,eggs:2,milk:50", "Dip bread in egg mixture. Fry.");
    }

    private void addRecipe(SQLiteDatabase db, String name, String ingredients, String steps) {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("ingredients", ingredients);
        values.put("steps", steps);
        db.insert("recipes", null, values);
    }

    // GET ALL RECIPES
    public List<String[]> getAllRecipes() {
        List<String[]> recipes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM recipes", null);

        if (cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String ingredients = cursor.getString(cursor.getColumnIndexOrThrow("ingredients"));
                String steps = cursor.getString(cursor.getColumnIndexOrThrow("steps"));
                recipes.add(new String[]{name, ingredients, steps});
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return recipes;
    }
    // DELETE an item
    public void deletePantryItem(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("pantry_items", "name = ?", new String[]{name});
        db.close();
    }
    // UPDATE an item
    public void updatePantryItem(String oldName, String newName, String newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", newName);
        values.put("quantity", newQuantity);

        // Find the item by its OLD name and update it
        db.update("pantry_items", values, "name = ?", new String[]{oldName});
        db.close();
    }
}