package com.example.findme;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class SqliteController extends SQLiteOpenHelper { 
	private static final String LOGCAT = null; 
	public SqliteController(Context applicationcontext) {
		super(applicationcontext, "find.db", null, 1);
		Log.d(LOGCAT,"Created");
		}
	@Override public void onCreate(SQLiteDatabase database) { 
		String query,query1,sql,sql1,sql2,sql3,sql4,sql5,d,del; 
		query = "create table IF NOT EXISTS lost " +
				      "( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,fname text,"
				      + " sname text,dob text,place text,district text,psch text,img blob);";
		query1= "create table IF NOT EXISTS found" +
			      "( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,ffrom text,"
			      + "cname text,dob text,place text,district text,psch text,img blob);"; 
		sql="create table IF NOT EXISTS msg( lto text,ffrom text, msg text, date date)";
		sql1="create table IF NOT EXISTS claim( uby text, over text)";
		sql2="create table IF NOT EXISTS users( _id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, deviceid text,serial text, phone text, uName text,status text)";
		sql3="create table IF NOT EXISTS match( fromFN text, fromSN text,foundDevId text,toFN text,toSN text,lostDevId text, date date)";
		sql4="create table IF NOT EXISTS admin( admin text, pass text)";
		sql5="insert into admin values( 'walter', 'walter')";
		d = "create table IF NOT EXISTS codes " +
			      "( phone text,code text);";
		del="delete from codes";
		database.execSQL(d);
		database.execSQL(del); 
		database.execSQL(query); 
		database.execSQL(query1);
		database.execSQL(sql);
		database.execSQL(sql1);
		database.execSQL(sql2);
		database.execSQL(sql3);
		database.execSQL(sql4);
		database.execSQL(sql5);
		Log.d(LOGCAT,"Tables Created"); 
		} 
	@Override public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query; query = "DROP TABLE IF EXISTS lost"; 
		database.execSQL(query); onCreate(database);
		}
	public String[] getmsgUsers() {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM  msg where lto='0716573387'";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String user = cursor.getString(1);
					usersList.add(user);
				} while (cursor.moveToNext());
			}

			String[] users = new String[usersList.size()];
			//cursor.close();
			db.close();
			return (usersList.toArray(users));
		}
		catch(Exception e) {
			Log.d("Error in getting users from DB", e.getMessage());
			return null;
		}
	}
	public String[] getAllUsers() {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM  users";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String user = cursor.getString(0) + "  " + cursor.getString(4);
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
	public void submitCode(String phone,String code) { 
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("phone", phone);
		values.put("code", code);
		database.insert("codes", null, values); 
		database.close(); 
		}
	public void deletecode(String phone) {
		Log.d(LOGCAT,"delete"); SQLiteDatabase database = this.getWritableDatabase();	
		String deleteQuery = "DELETE FROM codes where phone='"+ phone +"'";
		Log.d("query",deleteQuery);
		database.execSQL(deleteQuery); 
		} 
	public void AddUser(String phone,String name,String serial, String imei,String status) { 
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("deviceid", imei);
		values.put("serial", serial);
		values.put("phone", phone);
		values.put("uName", name);
		values.put("status", status);
		database.insert("users", null, values); 
		database.close(); 
		}
	public String[] getImei(String serial) {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  imei FROM  users where serial='"+serial+"';";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String imei = cursor.getString(2);
					usersList.add(imei);
				} while (cursor.moveToNext());
			}

			String[] imeis = new String[usersList.size()];
			db.close();
			return (usersList.toArray(imeis));
		}
		catch(Exception e) {
			Log.d("Error in getting users from DB", e.getMessage());
			return null;
		}
	}

	
	public String[] getSerial(String phone) {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  serial FROM  users where phone='"+phone+"';";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String serial = cursor.getString(1) ;
					usersList.add(serial);
				} while (cursor.moveToNext());
			}

			String[] serials = new String[usersList.size()];
			db.close();
			return (usersList.toArray(serials));
		}
		catch(Exception e) {
			Log.d("Error in getting users from DB", e.getMessage());
			return null;
		}
	}

	public String[] getPhone(String serial) {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  phone FROM  users where serial='"+serial+"';";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String phone = cursor.getString(0);
					usersList.add(phone);
				} while (cursor.moveToNext());
			}

			String[] phones = new String[usersList.size()];
			db.close();
			return (usersList.toArray(phones));
		}
		catch(Exception e) {
			Log.d("Error in getting users from DB", e.getMessage());
			return null;
		}
	}
	public String[] getcode() {
		ArrayList<String> usersList = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM  codes ;";
		try {
			SQLiteDatabase db = this.getReadableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			// looping through all rows and adding to list
			if (cursor.moveToFirst()) {
				do {
					String imei = cursor.getString(1);
					usersList.add(imei);
				} while (cursor.moveToNext());
			}

			String[] imeis = new String[usersList.size()];
			db.close();
			return (usersList.toArray(imeis));
		}
		catch(Exception e) {
			Log.d("Error in getting users from DB", e.getMessage());
			return null;
		}
	}
	public void insertchild(String name1,String name2, String dob,String place,String district, String psch,byte[] img ) { 
			SQLiteDatabase database = this.getWritableDatabase();
			ContentValues values = new ContentValues();
			values.put("fname", name1);
			values.put("sname", name2);
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
				String selectQuery = "SELECT * FROM lost;"; 
				SQLiteDatabase database = this.getWritableDatabase();
				Cursor cursor = database.rawQuery(selectQuery, null);
				if (cursor.moveToFirst()) {
					do {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Name:", cursor.getString(0)+"  "+cursor.getString(1));
						//map.put("sname", cursor.getString(1));
						//map.put("dob", cursor.getString(2));
						//map.put("From", cursor.getString(3)+","+cursor.getString(4)+","+cursor.getString(5));
						//map.put("district", cursor.getString(4));
						//map.put("psch", cursor.getString(5));
						//map.put("img", cursor.getString(6));
						//map.put("StudentName", cursor.getString(1));
						wordList.add(map);
						}
					while (cursor.moveToNext());
					} // return contact list 
				return wordList;
				}
		
		
		// LIst all users
				public String[] getAllPlaces() {
					ArrayList<String> usersList = new ArrayList<String>();
					String selectQuery = "SELECT  * FROM  lost";
					try {
						SQLiteDatabase db = this.getReadableDatabase();
						Cursor cursor = db.rawQuery(selectQuery, null);
						// looping through all rows and adding to list
						if (cursor.moveToFirst()) {
							do {
								String user = cursor.getString(3) + ", " + cursor.getString(4) +  ", " + cursor.getString(5);
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
		
				
				public String[] getAllimages() {
					ArrayList<String> usersList = new ArrayList<String>();
					String selectQuery = "SELECT  * FROM  lost";
					try {
						SQLiteDatabase db = this.getReadableDatabase();
						Cursor cursor = db.rawQuery(selectQuery, null);
						// looping through all rows and adding to list
						if (cursor.moveToFirst()) {
							do {
								String user = cursor.getString(6) ;
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
		
				public String[] getAllNames() {
					ArrayList<String> usersList = new ArrayList<String>();
					String selectQuery = "SELECT  * FROM  found";
					try {
						SQLiteDatabase db = this.getReadableDatabase();
						Cursor cursor = db.rawQuery(selectQuery, null);
						// looping through all rows and adding to list
						if (cursor.moveToFirst()) {
							do {
								String user = cursor.getString(0) + "  " + cursor.getString(2);
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
			//} 
				//}
			//}
	//}
			
			public String[] getNames() {
				
				String selectQuery = "SELECT * FROM lost";
				
				SQLiteDatabase db = this.getReadableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				String[] names=new String[cursor.getCount()] ;
				cursor.moveToFirst();
				for(int i=0;i<names.length;i++){
				
				
				names[i] = cursor.getString(0);
				cursor.moveToNext();
				}
					
				//cursor.close();
				db.close();
				return names;
			}
      public String[] getPlaces() {
				
				String selectQuery = "SELECT * FROM lost";
				
				SQLiteDatabase db = this.getReadableDatabase();
				Cursor cursor = db.rawQuery(selectQuery, null);
				String[] names=new String[cursor.getCount()] ;
				cursor.moveToFirst();
				for(int i=0;i<names.length;i++){
				
				
				names[i] = cursor.getString(1);
				cursor.moveToNext();
				}
					
				cursor.close();
				db.close();
				return names;
			}
      
      
}

