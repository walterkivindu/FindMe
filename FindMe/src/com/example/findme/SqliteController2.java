package com.example.findme;


import java.util.ArrayList; 
import java.util.HashMap; 

import android.util.Log; 
import android.content.ContentValues; 
import android.content.Context; 
import android.database.Cursor; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteOpenHelper; 
public class SqliteController2 extends SQLiteOpenHelper { 
	private static final String LOGCAT = null; 
	public SqliteController2(Context androidFragment) {
		super(androidFragment, "findme.db", null, 1);
		Log.d(LOGCAT,"Created");
		}
	@Override public void onCreate(SQLiteDatabase database) { 
		String query; 
		query = "create table IF NOT EXISTS lost " +
			      "(cname text, from text,dob text,place text,district text,psch text,img blob);"; 
		database.execSQL(query); 
		Log.d(LOGCAT,"Lost Created"); 
		} 
	@Override public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query; query = "DROP TABLE IF EXISTS lost"; 
		database.execSQL(query); onCreate(database);
		}
	
	
	public void insertchild(String cname,String from, String dob,String place,String district, String psch,String img ) { 
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("fname", cname);
			values.put("sname", from);
			values.put("dob", dob);
			values.put("place", place);
			values.put("district", district);
			values.put("psch", psch);
			values.put("img", img);
			database.insert("lost", null, values); 
			database.close(); 
			}
		public int updateStudent(HashMap<String, String> queryValues) { 
			SQLiteDatabase database = this.getWritableDatabase(); 
			ContentValues values = new ContentValues(); 
			values.put("StudentName", queryValues.get("StudentName")); 
			return database.update("Students", values, "StudentId" + " = ?", new String[] {
					queryValues.get("StudentId") });
			//String updateQuery = "Update words set txtWord='"+word+"' where txtWord='"+ oldWord +"'"; 
			//Log.d(LOGCAT,updateQuery); 
			//database.rawQuery(updateQuery, null);
			//return database.update("words", values, "txtWord = ?", new String[] { word });
			}
		public void deleteStudent(String id) {
			Log.d(LOGCAT,"delete"); SQLiteDatabase database = this.getWritableDatabase();	
			String deleteQuery = "DELETE FROM lost where cname='"+ id +"'";
			Log.d("query",deleteQuery);
			database.execSQL(deleteQuery); 
			} 
		public ArrayList<HashMap<String, String>> getAllStudents() {
				ArrayList<HashMap<String, String>> wordList; 
				wordList = new ArrayList<HashMap<String, String>>(); 
				String selectQuery = "SELECT * FROM lost"; 
				SQLiteDatabase database = this.getWritableDatabase();
				Cursor cursor = database.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("StudentId", cursor.getString(0)); 
						map.put("StudentName", cursor.getString(1));
						wordList.add(map); } while (cursor.moveToNext());
					} // return contact list 
				return wordList;
				}
			public HashMap<String, String> getStudentInfo(String id) 
			{ 
				HashMap<String, String> wordList = new HashMap<String, String>(); 
				SQLiteDatabase database = this.getReadableDatabase(); 
				String selectQuery = "SELECT * FROM Students where StudentId='"+id+"'"; 
				Cursor cursor = database.rawQuery(selectQuery, null); if (cursor.moveToFirst()) {
					do { 
						//HashMap<String, String> map = new HashMap<String, String>();
						wordList.put("StudentName", cursor.getString(1)); 
						//wordList.add(map);
						} while (cursor.moveToNext());
					}	return wordList; 
					}
			
			public String[] getAllNames() {
				ArrayList<String> usersList = new ArrayList<String>();
				String selectQuery = "SELECT  * FROM  lost";
				try {
					SQLiteDatabase db = this.getReadableDatabase();
					Cursor cursor = db.rawQuery(selectQuery, null);
					// looping through all rows and adding to list
					if (cursor.moveToFirst()) {
						do {
							String user = cursor.getString(0) + "  " + cursor.getString(1);
							usersList.add(user);
						} while (cursor.moveToNext());
					}
		
					String[] users = new String[usersList.size()];
					db.close();
					return (usersList.toArray(users));
				}
				catch(Exception e) {
					Log.d("Error in getting users from DB", e.getMessage());
					return null;
				}
			}
			
			
			//} 
				//}
			//}
	//}
}

