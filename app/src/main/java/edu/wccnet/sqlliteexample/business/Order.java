package edu.wccnet.sqlliteexample.business;

import java.util.Date;

/**
 * Created by venus on 3/12/18.
 */

public class Order {
    public int orderID;
    public String customerName;
    private Date orderTime;

    /**
     * For new orders being placed
     * @param customerName
     */
    public Order( String customerName ) {
        this.customerName=customerName;
        orderTime = new Date();
    }

    /**
     * Used when retrieving orders from the database
     *
     * @param orderID
     * @param customerName
     * @param orderTime
     */
    public Order( int orderID, String customerName, Long orderTime ) {
        this.orderID=orderID;
        this.customerName=customerName;
        this.orderTime=new Date(orderTime);
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

}
