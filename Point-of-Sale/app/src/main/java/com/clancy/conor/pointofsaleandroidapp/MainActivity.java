package com.clancy.conor.pointofsaleandroidapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.AlertDialog.Builder;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private TextView nNameTextView;
    private TextView mQunatityTextView;
    private TextView mDateTextView;
    private Item mCurrentItem;
    private Item mClearedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Captureviews
        //namesincontent_main
        nNameTextView=findViewById(R.id.name_text);
        mQunatityTextView=findViewById(R.id.quantity_text);
        mDateTextView=findViewById(R.id.date_text);


        // Given boiler plate code, don't mess with it.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Finding the floating action button by its id - id = fab (check xml)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Set current item to the fab when the press the default item
                // The default item is ear plugs
                // TODO: Later make this actually be an add button
                
                //mCurrentItem = Item.getDefaultItem();
                //showCurrentItem();

                addItem();
                
                // for now just practive s
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    private void addItem() {

        // Create dialog and show it
        //AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        //Customize the dialog for needs
        // Simple Dialog
        /*
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Alert message to be shown");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
        */

        //This creates the view

        alertDialog.setTitle(R.string.add_item);
        View view = getLayoutInflater().inflate(R.layout.dialog_add, null, false);
        alertDialog.setView(view);

        // if you want to capture edit text field, capture where they added in the name
        // final because it is used in side this private and points to this object and will never change
        // If you use findViewById(R.id.edit_name); it will only search inside the xml file for aactivity main
        // by using view.findViewById(R.id.edit_name); it searches in the local variable view in dialog

        final EditText nameEditText = view.findViewById(R.id.edit_name);
        final EditText quantityEditText = view.findViewById(R.id.edit_quantity);
        final CalendarView deliveryDateView = view.findViewById(R.id.calendar_view);
        final GregorianCalendar calendar = new GregorianCalendar();

        // when you click buttons on calendar that calls a function and you want to update the calendar
        deliveryDateView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
            // update calendar object as user clicks calendar
                calendar.set(year,month,dayOfMonth);
            }
        });

        alertDialog.setNegativeButton(android.R.string.cancel,null);
        alertDialog.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // This method runs when you click the OK button
                String name = nameEditText.getText().toString();
                // Get quantity which is in hte quantityEditText
                //nt quantity = quantityEditText.getText().toString(); will need to convert string to Integer

                int quantity = Integer.parseInt(quantityEditText.getText().toString());
                mCurrentItem = new Item(name, quantity, calendar);
                showCurrentItem();

            }
        });

        alertDialog.create().show();

    }

    private void showCurrentItem() {
        // Want to set the text to current item name
        nNameTextView.setText(mCurrentItem.getName());

        // mQunatityTextView.setText(mCurrentItem.getQuantity());
        // By passing in an integer it tries to find an ID with that number
        // so need to do the line below
        // NOTE getString will get the String quantity_format, see strings.xml and put in mCurrent
        // item quantity
        mQunatityTextView.setText(getString(R.string.quantity_format,mCurrentItem.getQuantity()));
        mDateTextView.setText(getString(R.string.date_format, mCurrentItem.getDeliveryDateString()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()){
            case R.id.action_reset:
                // will use the default constructor - nothing
                // Make a bugger of current item if we want to retrieve it
                mClearedItem = mCurrentItem;
                // Make a new current item so get rid of it
                mCurrentItem = new Item();
                // show current item, looks like it is gone but it is stored if we need to retrieve it
                showCurrentItem();
                // TODO: Uses a snackbar to allow the user to undo their action
                // Snackbar is the name of the clas
                // .make is a static class method, it has 3 arguments, what view, what you want it to say
                // and how long it must remain up
                // All that returns a Snackbar object and a snackbar object has an instance method called show
                // Snackbar.make(findViewById(R.id.coordinator_layout), "Item Cleared", Snackbar.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout), "Item Cleared", Snackbar.LENGTH_LONG);
                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Someone has just clicked your button
                        // TODO: To do the undo
                        mCurrentItem = mClearedItem;
                        showCurrentItem();
                        Snackbar.make(findViewById(R.id.coordinator_layout), "Item Restored", Snackbar.LENGTH_SHORT).show();
                    }
                });
                snackbar.show();
                break;
            case R.id.action_settings:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
