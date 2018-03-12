package edu.wccnet.sqlliteexample.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import edu.wccnet.sqlliteexample.business.Order;

/**
 * Created by venus on 3/12/18.
 */

public class OrderDAO extends SQLiteOpenHelper {
    public static final String TAG=OrderDAO.class.toString();

    // Databases can be versioned... Note what we are doing in here is really typical only of
    // a sqlite implementation on Android
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "WaitstaffHelperDB";

    // We are going to set the following so we don't accidentally make mistakes in the naming
    // of our columns or table later
    private static final String TABLE_ORDERS = "orders";
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_CUSTOMER_NAME = "order_customer_name";
    private static final String ORDER_TIME = "order_time";

    public OrderDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS +
                "(" + ORDER_ID + " INTEGER PRIMARY KEY,"
                + ORDER_CUSTOMER_NAME + " TEXT,"
                + ORDER_TIME + " INTEGER" +
                ")";

        Log.d( TAG, CREATE_ORDERS_TABLE );

        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public String createOrder(Order myOrder) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ORDER_CUSTOMER_NAME, myOrder.getCustomerName());
            values.put(ORDER_TIME, myOrder.getOrderTime().getTime());
            long orderID=db.insert(TABLE_ORDERS, null, values);
            return "Created order #" + orderID;
            // Do not forgot to close your database connections!
        } catch( Exception e0 ) {
            return e0.getMessage();
        } finally {
            db.close();
        }
    }

    /**
     * We can certainly improve the handling in here!
     * @param orderID
     * @return
     */
    public Order getOrder(int orderID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_ORDERS, new String[] { ORDER_ID,
                        ORDER_CUSTOMER_NAME, ORDER_TIME }, ORDER_ID + "=?",
                new String[] { String.valueOf(orderID) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
            Order myOrder = new Order(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getLong(2));

        return myOrder;
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> allOrders = new ArrayList<Order>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Order myOrder = new Order(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getLong(2));

                allOrders.add(myOrder);
            } while (cursor.moveToNext());
        }

        return allOrders;
    }

    // Based on getAllOrders -- can you guess how to count the orders?  hint --> the query
    // is select count(*) from tablename
    // look at getAllOrders for a reference


    // What about an update and a delete? Use the addOrder as a reference
    // What methods does the SQLiteDatbase object have?



}
