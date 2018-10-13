package com.clancy.conor.pointofsaleandroidapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView nNameTextView;
    private TextView mQunatityTextView;
    private TextView mDateTextView;
    private Item mCurrentItem;

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
                
                mCurrentItem = Item.getDefaultItem();
                showCurrentItem();
                
                // for now just practive s
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
