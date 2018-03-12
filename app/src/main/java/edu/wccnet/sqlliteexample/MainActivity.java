package edu.wccnet.sqlliteexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.wccnet.sqlliteexample.business.Order;
import edu.wccnet.sqlliteexample.dao.OrderDAO;

public class MainActivity extends AppCompatActivity {
    public static final String TAG=MainActivity.class.toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // For purposes of this example going to execute the crud statements on the main UI thread -
        // this is not ideal, however right now we are concentrating on database interaction.  We
        // can combine with threading later
        final EditText customerName=findViewById(R.id.editTextCustomerName);
        final TextView numberOrders=findViewById(R.id.textViewNumberOrders);
        final Button createOrderButton=findViewById(R.id.buttonCreateOrder);
        createOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order myOrder = new Order( customerName.getText().toString() );
                OrderDAO myHandler = new OrderDAO(MainActivity.this);
                String result=myHandler.createOrder(myOrder);
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

        final Button getOrderCount=findViewById(R.id.buttonGetOrderCount);
        getOrderCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDAO myHandler = new OrderDAO(MainActivity.this);
                numberOrders.setText(String.valueOf(myHandler.getAllOrders().size()));
            }
        });
    }

}
