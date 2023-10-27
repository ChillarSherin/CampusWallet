package com.chillarcards.campuswallet.NotificationCenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chillarcards.campuswallet.application.PrefManager;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.Transaction;
import com.chillarcards.campuswallet.networkmodels.OnlineTransactionHistory.TrasactionCategory;
import com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.TransactionCategory;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
//	private static final int DATABASE_VERSION = 8;
//	private static final int DATABASE_VERSION = 9;
	private static final int DATABASE_VERSION = 15;

	// Database DateTime
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table DateTime
	private static final String TABLE_CONTACTS = "contacts";
	private static final String TABLE_CONTACTS_NEW = "contactsNew";
	private static final String TABLE_TRANSACTIONS = "transactions";
	private static final String TABLE_TRANSACTIONS_CATEGORY = "transactionsCategory";
	private static final String TABLE_WALLET_TRANSACTIONS = "wallettransactions";
	private static final String TABLE_WALLET_TRANSACTIONS_CATEGORY = "wallettransactionsCategory";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_DATETIME = "DateTime";
	private static final String KEY_DATAS = "Datas";
	private static final String KEY_STUD_ID = "StudentID";
	private static final String KEY_STD_NAME = "StandardName";
	private static final String KEY_SCHOOL_ID = "SchoolID";
	private static final String KEY_CLICKED = "Clicked";
	private static final String KEY_MESSAGE_ID = "MessageID";

	// Transaction Table Columns names
	private static final String KEY_TRA_ID = "ID";
	private static final String KEY_TRANSACTION_ID = "transactionID";
	private static final String KEY_TRANS_CREATED_DATE = "onlineTransactionCreatedOn";
	private static final String KEY_TRANS_AMOUNT = "amount";
	private static final String KEY_TRAND_STATUS = "status";
	private static final String KEY_TRAND_ITEM = "item";
	private static final String KEY_TRANS_CATEGORY_ID = "transactionCategoryID";
	private static final String KEY_TRANS_CATEGORY_KEY = "transactionCategoryKey";

	// Transaction Category Table Columns names
	private static final String KEY_TRANSACTION_CATEGORY_ID = "transactionCategoryID";
	private static final String KEY_TRANSACTION_CATEGORY_KEY = "transactionCategoryKey";

	// WALLET_Transaction Table Columns names
	private static final String KEY_WALLET_TRA_ID = "ID";
	private static final String KEY_WALLET_TRANSACTION_ID = "transactionID";
	private static final String KEY_WALLET_TRANS_CREATED_DATE = "transaction_time";
	private static final String KEY_WALLET_TRANS_AMOUNT = "transaction_amount";
	private static final String KEY_WALLET_TRANS_STATUS = "status";
	private static final String KEY_WALLET_TRANS_PRE_BALANCE = "prev_balance";
	private static final String KEY_WALLET_TRANS_CURR_BALANCE = "current_balance";
	private static final String KEY_WALLET_TRAND_EXTRA = "extra_details_flag";
	private static final String KEY_WALLET_TRANS_CATEGORY_TYPE = "transaction_type";
	private static final String KEY_STUDENT_ID = "studentid";

	// WALLET_Transaction Category Table Columns names
	private static final String KEY_WALLET_TRANSACTION_CATEGORY_ID = "id";
	private static final String KEY_WALLET_TRANSACTION_CATEGORY_KEY = "name";

	PrefManager prefManager;
	Context mContext;

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		prefManager=new PrefManager(context);
		mContext=context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS_NEW + "("
				+ KEY_ID + " TEXT," + KEY_DATETIME + " TEXT,"
				+ KEY_DATAS + " VARCHAR," + KEY_CLICKED + " TEXT," + KEY_STUD_ID + " TEXT," + KEY_SCHOOL_ID + " TEXT, "+KEY_STD_NAME+" TEXT, "+KEY_MESSAGE_ID+" INTEGER)";
		db.execSQL(CREATE_CONTACTS_TABLE);
		CreateTransactionCategoryTable(db);
		CreateTransactionsTable(db);
		CreateWalletTransactionCategoryTable(db);
		CreateWalletTransactionsTable(db);

		//System.out.println("SHANIL : "+CREATE_CONTACTS_TABLE.toString());

	}

	public void  CreateTransactionCategoryTable(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS_CATEGORY + "("
				+ KEY_TRANSACTION_CATEGORY_ID + " TEXT,"
				+ KEY_TRANSACTION_CATEGORY_KEY + " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	public void  CreateTransactionsTable(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TRANSACTIONS + "("
				+ KEY_TRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_TRANSACTION_ID + " TEXT,"
				+ KEY_TRANS_CREATED_DATE + " TEXT,"
				+ KEY_TRANS_AMOUNT + " TEXT,"
				+ KEY_TRAND_STATUS + " TEXT,"
				+ KEY_TRANS_CATEGORY_ID + " TEXT,"
				+ KEY_TRANS_CATEGORY_KEY + " TEXT,"
				+ KEY_TRAND_ITEM + " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	public void  CreateWalletTransactionCategoryTable(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WALLET_TRANSACTIONS_CATEGORY + "("
				+ KEY_WALLET_TRANSACTION_CATEGORY_ID + " TEXT,"
				+ KEY_WALLET_TRANSACTION_CATEGORY_KEY+ " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}
	/*public void  CreateWalletTransactionsTable(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WALLET_TRANSACTIONS + "("
				+ KEY_WALLET_TRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_WALLET_TRANSACTION_ID + " TEXT,"
				+ KEY_WALLET_TRANS_CREATED_DATE + " TEXT,"
				+ KEY_WALLET_TRANS_AMOUNT + " TEXT,"
				+ KEY_WALLET_TRAND_EXTRA+ " TEXT,"
				+ KEY_WALLET_TRANS_CATEGORY_TYPE + " TEXT,"
				+ KEY_WALLET_TRANS_PRE_BALANCE + " TEXT,"
				+ KEY_WALLET_TRANS_CURR_BALANCE + " TEXT,"
				+ KEY_WALLET_TRANS_STATUS+ " TEXT,"
				+ KEY_STUDENT_ID+ " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}*/

	public void  CreateWalletTransactionsTable(SQLiteDatabase db)
	{
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WALLET_TRANSACTIONS + "("
				+ KEY_WALLET_TRA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
				+ KEY_WALLET_TRANSACTION_ID + " TEXT,"
				+ KEY_WALLET_TRANS_CREATED_DATE + " TEXT,"
				+ KEY_WALLET_TRANS_AMOUNT + " TEXT,"
				+ KEY_WALLET_TRAND_EXTRA+ " TEXT,"
				+ KEY_WALLET_TRANS_CATEGORY_TYPE + " TEXT,"
				+ KEY_WALLET_TRANS_PRE_BALANCE + " TEXT,"
				+ KEY_WALLET_TRANS_CURR_BALANCE + " TEXT,"
				+ KEY_WALLET_TRANS_STATUS+ " TEXT,"
				+ KEY_STUDENT_ID+ " TEXT)";
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//System.out.println( "Updating table from " + oldVersion + " to " + newVersion);
		// Drop older table if existed
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
//		db.execSQL("DROP TABLE IF EXISTS " + TABLE_WALLET_TRANSACTIONS);
		// Create tables again
//		onCreate(db);
		// CreateWalletTransactionsTable(db);
//		if (newVersion==8)  // for revamp
//		{
//			logoutExixting();
//		}

//		if (newVersion > oldVersion) {
//			deleteAllNotif(db);
//			db.execSQL("ALTER TABLE "+TABLE_CONTACTS+" ADD COLUMN "+KEY_MESSAGE_ID+" INTEGER DEFAULT -1");
//		}
        if(newVersion == 15 ){
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS_NEW + "("
                    + KEY_ID + " TEXT," + KEY_DATETIME + " TEXT,"
                    + KEY_DATAS + " VARCHAR," + KEY_CLICKED + " TEXT," + KEY_STUD_ID + " TEXT," + KEY_SCHOOL_ID + " TEXT, "+KEY_STD_NAME+" TEXT, "+KEY_MESSAGE_ID+" INTEGER)";
            db.execSQL(CREATE_CONTACTS_TABLE);

        }

	}
//	public void logoutExixting()
//	{
//		prefManager.clearAll();
//		// logout from moenguage
////                        MoEHelper.getInstance(mContext.getApplicationContext()).logoutUser();
//		OneSignal.getTags(new OneSignal.GetTagsHandler() {
//			@Override
//			public void tagsAvailable(JSONObject tags) {
//				try {
//					//System.out.println("One Signal Tags : " + tags.toString());
//					Iterator<String> iter = tags.keys();
//					while (iter.hasNext()) {
//						String key = iter.next();
//						//System.out.println("One Signal Tags key : " + key);
//						OneSignal.deleteTag(key);
//					}
//				}
//				catch (Exception e)
//				{
//					//System.out.println("One Signal Tags : ERROR : " + e);
//				}
//			}
//		});
//		Intent i=new Intent(mContext, LoginActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		mContext.startActivity(i);
//		((Activity) mContext).finish();
//	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	//add transactions
	public void addTransactions(Transaction contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		ContentValues values = new ContentValues();
		values.put(KEY_TRANSACTION_ID, contact.getTransactionID()); // Transaction ID
		values.put(KEY_TRANS_CREATED_DATE, contact.getOnlineTransactionCreatedOn()); // Transaction Created DateTime
		values.put(KEY_TRANS_AMOUNT, contact.getAmount()); // Transaction amount
		values.put(KEY_TRAND_STATUS, contact.getStatus()); // Transaction status
		values.put(KEY_TRANS_CATEGORY_ID, contact.getTransactionCategoryID()); // Transaction Category ID
		values.put(KEY_TRANS_CATEGORY_KEY, contact.getTransactionCategoryKey()); // Transaction Category Key
		values.put(KEY_TRAND_ITEM, contact.getItem()); // Transaction Item


		// Inserting Row
		db.insert(TABLE_TRANSACTIONS, null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
//		db.close(); // Closing database connection
	}
	//add Wallet transactions
	public void addWalletTransactions(com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction contact,String studentID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		ContentValues values = new ContentValues();
		values.put(KEY_WALLET_TRANSACTION_ID, contact.getTransactionID()); // Transaction ID
		values.put(KEY_WALLET_TRANS_CREATED_DATE, contact.getTransactionTime()); // Transaction Created DateTime
		values.put(KEY_WALLET_TRANS_AMOUNT, contact.getTransactionAmount()); // Transaction amount
		values.put(KEY_WALLET_TRAND_EXTRA, contact.getExtra_details_flag()); // Transaction status
		values.put(KEY_WALLET_TRANS_CATEGORY_TYPE, contact.getTransactionType()); // Transaction Category ID
		values.put(KEY_WALLET_TRANS_PRE_BALANCE, contact.getPrev_balance()); // Transaction previous balance
		values.put(KEY_WALLET_TRANS_CURR_BALANCE, contact.getCurrent_balance()); // Transaction Current balance
		values.put(KEY_WALLET_TRANS_STATUS, contact.getStatus()); // Transaction Status
		values.put(KEY_STUDENT_ID, studentID); // Transaction Student ID


		// Inserting Row
		db.insert(TABLE_WALLET_TRANSACTIONS, null, values);
//		db.insertWithOnConflict(TABLE_WALLET_TRANSACTIONS, null, values,SQLiteDatabase.CONFLICT_REPLACE);


		db.setTransactionSuccessful();
		db.endTransaction();
//		db.close(); // Closing database connection
	}
	//add transaction Category
	public void addTransactionCategory(TrasactionCategory contact) {
		TrasactionCategory category=getCategoriesCheck(contact.getTransactionCategoryID());
		if (!category.getTransactionCategoryID().equalsIgnoreCase(contact.getTransactionCategoryID())) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(KEY_TRANSACTION_CATEGORY_ID, contact.getTransactionCategoryID()); // Transaction Category ID
			values.put(KEY_TRANSACTION_CATEGORY_KEY, contact.getTransactionCategoryKey()); // Transaction Category Key


			// Inserting Row
			db.insert(TABLE_TRANSACTIONS_CATEGORY, null, values);
			db.setTransactionSuccessful();
			db.endTransaction();
//		db.close(); // Closing database connection
		}
		else
		{
			UpdateTransactionCategories(contact);
		}
	}
	//add wallet transaction Category
	public void addWalletTransactionCategory(TransactionCategory contact) {
		TransactionCategory category=getWalletCategoriesCheck(contact.getId());
		if (!category.getId().equalsIgnoreCase(contact.getId())) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put(KEY_WALLET_TRANSACTION_CATEGORY_ID, contact.getId()); // Transaction Category ID
			values.put(KEY_WALLET_TRANSACTION_CATEGORY_KEY, contact.getName()); // Transaction Category Key


			// Inserting Row
			db.insert(TABLE_WALLET_TRANSACTIONS_CATEGORY, null, values);
			db.setTransactionSuccessful();
			db.endTransaction();
//		db.close(); // Closing database connection
		}
		else
		{
			UpdateWalletTransactionCategories(contact);
		}
	}
	//transaction category check
	public TrasactionCategory getCategoriesCheck(String categoryID)
	{
//		List<TrasactionCategory> contactList = new ArrayList<TrasactionCategory>();
		TrasactionCategory contact = new TrasactionCategory();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS_CATEGORY+" WHERE "+KEY_TRANSACTION_CATEGORY_ID+" = "+"'"+categoryID+"'";
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				contact.setTransactionCategoryID((cursor.getString(0)));
				contact.setTransactionCategoryKey(cursor.getString(1));

				// Adding contact to list
//				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contact;
	}
	//Wallet transaction category check
	public TransactionCategory getWalletCategoriesCheck(String categoryID)
	{
//		List<TrasactionCategory> contactList = new ArrayList<TrasactionCategory>();
		TransactionCategory contact = new TransactionCategory();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WALLET_TRANSACTIONS_CATEGORY+" WHERE "+KEY_WALLET_TRANSACTION_CATEGORY_ID+" = "+"'"+categoryID+"'";
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				contact.setId((cursor.getString(0)));
				contact.setName(cursor.getString(1));

				// Adding contact to list
//				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contact;
	}
	//get all categories
	public List<TrasactionCategory> getAllCategories()
	{
		List<TrasactionCategory> contactList = new ArrayList<TrasactionCategory>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS_CATEGORY;
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TrasactionCategory contact = new TrasactionCategory();
				contact.setTransactionCategoryID((cursor.getString(0)));
				contact.setTransactionCategoryKey(cursor.getString(1));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}
	//get all Wallet categories
	public List<TransactionCategory> getAllWalletCategories()
	{
		List<TransactionCategory> contactList = new ArrayList<TransactionCategory>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WALLET_TRANSACTIONS_CATEGORY;
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				TransactionCategory contact = new TransactionCategory();
				contact.setId((cursor.getString(0)));
				contact.setName(cursor.getString(1));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}
	//get all transaction
	public List<Transaction> getAllTransactions()
	{
		List<Transaction> contactList = new ArrayList<Transaction>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS;
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Transaction contact = new Transaction();
				contact.setTransactionID((cursor.getString(0)));
				contact.setOnlineTransactionCreatedOn(cursor.getString(1));
				contact.setAmount(cursor.getString(2));
				contact.setStatus(cursor.getString(3));
				contact.setTransactionCategoryID(cursor.getString(4));
				contact.setTransactionCategoryKey(cursor.getString(5));
				contact.setItem(cursor.getString(6));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}
	//get all Wallet transaction
	public List<com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction> getAllWalletTransactions(String studentID)
	{
		List<com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction> contactList = new ArrayList<com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction>();

		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WALLET_TRANSACTIONS + " Where "+KEY_STUDENT_ID+"="+"'"+studentID+"'"+" ORDER BY "+ KEY_WALLET_TRA_ID +" DESC";
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction contact = new com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction();
				contact.setTransactionID((cursor.getString(1)));
				contact.setTransactionTime(cursor.getString(2));
				contact.setTransactionAmount(cursor.getString(3));
				contact.setExtra_details_flag(Integer.parseInt(cursor.getString(4)));
				contact.setTransactionType(cursor.getString(5));
				contact.setPrev_balance(cursor.getString(6));
				contact.setCurrent_balance(cursor.getString(7));
				contact.setStatus(Integer.parseInt(cursor.getString(8)));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}
	//get last transaction ID
	public Transaction getLastTransactionsID()
	{
//		List<Transaction> contactList = new ArrayList<Transaction>();
		Transaction contact = new Transaction();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_TRANSACTIONS +" ORDER BY "+ KEY_TRA_ID +" DESC LIMIT 1";;
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				contact.setTransactionID((cursor.getString(0)));
				contact.setOnlineTransactionCreatedOn(cursor.getString(1));
				contact.setAmount(cursor.getString(2));
				contact.setStatus(cursor.getString(3));
				contact.setTransactionCategoryID(cursor.getString(4));
				contact.setTransactionCategoryKey(cursor.getString(5));
				contact.setStatus(cursor.getString(6));

				// Adding contact to list
//				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contact;
	}
	//get last Wallet transaction ID
	public com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction getLastWalletTransactionsID(String studentID)
	{
//		List<Transaction> contactList = new ArrayList<Transaction>();
		com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction contact = new com.chillarcards.campuswallet.networkmodels.cardtransactionhistory.Transaction();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_WALLET_TRANSACTIONS+ " Where "+KEY_STUDENT_ID+ "='"+ studentID+"'" +" ORDER BY "+ KEY_WALLET_TRA_ID +" DESC LIMIT 1";;
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {

				contact.setTransactionID((cursor.getString(1)));
				contact.setTransactionTime(cursor.getString(2));
				contact.setTransactionAmount(cursor.getString(3));
				contact.setExtra_details_flag(Integer.parseInt(cursor.getString(4)));
				contact.setTransactionType(cursor.getString(5));
				contact.setPrev_balance(cursor.getString(6));
				contact.setCurrent_balance(cursor.getString(7));
				contact.setStatus(Integer.parseInt(cursor.getString(8)));

				// Adding contact to list
//				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contact;
	}
	//	update transaction Categories
	public int UpdateTransactionCategories(TrasactionCategory category) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.beginTransaction();
		ContentValues attndnc = new ContentValues();

		attndnc.put(KEY_TRANSACTION_CATEGORY_ID,category.getTransactionCategoryID());
		attndnc.put(KEY_TRANSACTION_CATEGORY_KEY,category.getTransactionCategoryKey());

		int count=db.update(TABLE_TRANSACTIONS_CATEGORY, attndnc, KEY_TRANSACTION_CATEGORY_ID+ " = ?",
				new String[] { String.valueOf(category.getTransactionCategoryID()) });
		//System.out.println("ELDDD: update count  "+count);
		////db.close();
		db.setTransactionSuccessful();
		db.endTransaction();
		// updating row
		return count;
	}
	//	update wallet transaction Categories
	public int UpdateWalletTransactionCategories(TransactionCategory category) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.beginTransaction();
		ContentValues attndnc = new ContentValues();

		attndnc.put(KEY_TRANSACTION_CATEGORY_ID,category.getId());
		attndnc.put(KEY_TRANSACTION_CATEGORY_KEY,category.getName());

		int count=db.update(TABLE_TRANSACTIONS_CATEGORY, attndnc, KEY_TRANSACTION_CATEGORY_ID+ " = ?",
				new String[] { String.valueOf(category.getId()) });
		//System.out.println("ELDDD: update count  "+count);
		////db.close();
		db.setTransactionSuccessful();
		db.endTransaction();
		// updating row
		return count;
	}
	// Adding new contact
	public void addContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.beginTransaction();
		ContentValues values = new ContentValues();
		values.put(KEY_DATETIME, contact.getdatetime()); // Contact DateTime
		values.put(KEY_ID, contact.getID()); // Contact DateTime
		values.put(KEY_DATAS, contact.getDatas()); // Contact Phone
		values.put(KEY_CLICKED, contact.getClicked()); // Contact Phone
		values.put(KEY_STUD_ID, contact.getStudentID()); // Contact Phone
		values.put(KEY_SCHOOL_ID, contact.getSchoolID()); // Contact Phone
		values.put(KEY_STD_NAME, contact.getStdName()); // Contact Phone
		values.put(KEY_MESSAGE_ID, contact.getMessageID()); // Contact Phone

		//System.out.println("DATABSE 1 "+contact.getStudentID());
		//System.out.println("DATABSE 2 "+contact.getSchoolID());

		// Inserting Row
		db.insert(TABLE_CONTACTS_NEW, null, values);
		db.setTransactionSuccessful();
		db.endTransaction();
//		db.close(); // Closing database connection
	}

	// Getting single contact
	Contact getContact(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLE_CONTACTS_NEW, new String[] { KEY_ID,
						KEY_DATETIME, KEY_DATAS,KEY_CLICKED,KEY_STD_NAME }, KEY_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();

		Contact contact = new Contact((cursor.getString(0)),
				cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6),cursor.getInt(7));
		// return contact
		return contact;
	}


	public int UpdateTable(String id) {
		SQLiteDatabase db = this.getWritableDatabase();

		db.beginTransaction();
		ContentValues attndnc = new ContentValues();

		attndnc.put(KEY_CLICKED,"1");

		int count=db.update(TABLE_CONTACTS_NEW, attndnc, KEY_ID+ " = ?",
				new String[] { String.valueOf(id) });
		//System.out.println("ELDDD: update count  "+count);
		////db.close();
		db.setTransactionSuccessful();
		db.endTransaction();
		// updating row
		return count;
	}


	// Getting All Contacts
	public List<Contact> getAllContacts(String studid,String schoolid,String StdName) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS_NEW+" WHERE "+KEY_SCHOOL_ID+" = "+"'"+schoolid+"'"+" AND ("+KEY_STUD_ID+" = "+"'"+studid+"' OR "+KEY_STUD_ID+" = '')"+" AND "+KEY_STD_NAME+" = '"+StdName+"' ORDER BY "+KEY_CLICKED+" ASC";
		//System.out.println("selectQuery : "+selectQuery);

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID((cursor.getString(0)));
				contact.setdatetime(cursor.getString(1));
				contact.setDatas(cursor.getString(2));
				contact.setClicked(cursor.getString(3));
				contact.setStudentID(cursor.getString(4));
				contact.setSchoolID(cursor.getString(5));
				contact.setStdName(cursor.getString(6));
				contact.setMessageID(cursor.getInt(7));

				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}
	public List<Contact> getAllContactsCount(String StudID,String StdName) {
		List<Contact> contactList = new ArrayList<Contact>();
		// Select All Query
		String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS_NEW+" WHERE "+KEY_CLICKED+" = "+"'"+"0"+"'"+" AND ("+KEY_STUD_ID+" = "+"'"+StudID+"' OR "+KEY_STUD_ID+" = '')"+" AND "+KEY_STD_NAME+" = '"+StdName+"'";

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		db.beginTransaction();
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Contact contact = new Contact();
				contact.setID((cursor.getString(0)));
				contact.setdatetime(cursor.getString(1));
				contact.setDatas(cursor.getString(2));
				contact.setClicked(cursor.getString(3));
				contact.setStudentID(cursor.getString(4));
				contact.setSchoolID(cursor.getString(5));
				contact.setStdName(cursor.getString(6));
				contact.setMessageID(cursor.getInt(7));
				// Adding contact to list
				contactList.add(contact);
			} while (cursor.moveToNext());
		}
		db.setTransactionSuccessful();
		db.endTransaction();
		// return contact list
		return contactList;
	}

	// Updating single contact
	public int updateContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_DATETIME, contact.getdatetime());
		values.put(KEY_DATAS, contact.getDatas());
		values.put(KEY_CLICKED, contact.getClicked());

		// updating row
		return db.update(TABLE_CONTACTS_NEW, values, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
	}

	// Deleting single contact
	public void deleteContact(Contact contact) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_CONTACTS_NEW, KEY_ID + " = ?",
				new String[] { String.valueOf(contact.getID()) });
		db.close();
	}

	// Deleting Transactions
	public void deleteTransactions() {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_WALLET_TRANSACTIONS, null,null);
		db.close();
	}

	public void deleteAllNotif(SQLiteDatabase db){
		db.execSQL("delete from "+ TABLE_CONTACTS_NEW);
	}

	// Getting contacts Count
	public int getContactsCount() {
		String countQuery = "SELECT  * FROM " + TABLE_CONTACTS_NEW;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count=cursor.getCount();
		cursor.close();

		return count;
	}

}