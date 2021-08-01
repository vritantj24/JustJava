package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;

    public void increment(View view) {
        quantity++;
        if(quantity>100)
        {
            Context context = getApplicationContext();
            CharSequence text = "You cannot add too many coffees!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        display(quantity);
    }

    public void decrement(View view) {
        quantity--;
        if (quantity<1)
        {
            Context context = getApplicationContext();
            CharSequence text = "Coffees cannot be less than 1 !";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            return;
        }
        display(quantity);
    }

    private int calculateprice(boolean hasWhippedCream, boolean haschocolate) {
        int priceof1coffee = 5;
        if (hasWhippedCream) {
            priceof1coffee += 1;
        }
        if (haschocolate) {
            priceof1coffee += 2;
        }
        return quantity * priceof1coffee;
    }

    private String createordersummary(int price, Boolean hasWhippedCream, Boolean haschocolate, String name) {
        return "Name : " + name + "\nAdd Whipped Cream? " + hasWhippedCream + "\nAdd Chocolate? " + haschocolate + "\nQuantity : " + quantity + "\nTotal : $" + price + "\nThank You!";
    }

    public void submitOrder(View view) {
        CheckBox checkBox = findViewById(R.id.whipped_cream_check);
        CheckBox checkBox1 = findViewById(R.id.chocolate_check);
        EditText editText = findViewById(R.id.name);
        String name = editText.getText().toString();
        boolean haschocolate = checkBox1.isChecked();
        boolean hasWhippedCream = checkBox.isChecked();
        int price = calculateprice(hasWhippedCream, haschocolate);
        String pricemessage = createordersummary(price, hasWhippedCream, haschocolate, name);
        String subject = "Just Java Order for "+name;
        composeEmail(subject,pricemessage);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    public void composeEmail( String subject,String pricemessage) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT,pricemessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


}