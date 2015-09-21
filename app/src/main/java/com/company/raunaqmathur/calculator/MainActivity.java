package com.company.raunaqmathur.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;
import java.util.Calendar;
import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
            EditText textHomeValue = (EditText)findViewById(R.id.textHomeValue);
            EditText textDownPayment = (EditText)findViewById(R.id.textDownPayment);
            EditText textInterestRate = (EditText)findViewById(R.id.textInterestRate);
            Spinner spinnerTerm = (Spinner)findViewById(R.id.spinnerTerm);
            EditText textPropertyTaxRate = (EditText)findViewById(R.id.textPropertyTaxRate);
            textHomeValue.setText("");
            textDownPayment.setText("");
            textInterestRate.setText("");
            spinnerTerm.setSelection(0);
            textPropertyTaxRate.setText("");

            ((EditText)findViewById(R.id.textTotalTaxPaid)).setText("");
            ((EditText)findViewById(R.id.textTotalInterestPaid)).setText( "");
            ((EditText)findViewById(R.id.textTotalMonthlyPayment)).setText("");
            ((EditText) findViewById(R.id.textPayOffDate)).setText("");


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onButtonClicked(View view)
    {


        int term = 0;
        EditText textHomeValue = (EditText)findViewById(R.id.textHomeValue);
        EditText textDownPayment = (EditText)findViewById(R.id.textDownPayment);
        EditText textInterestRate = (EditText)findViewById(R.id.textInterestRate);
        Spinner spinnerTerm = (Spinner) findViewById(R.id.spinnerTerm);
        EditText textPropertyTaxRate = (EditText)findViewById(R.id.textPropertyTaxRate);
        double homeValue = 0;
        double downPayment  =0 ;
        double interestRate=0;
        double propertyTaxRate =0;
        int monthlyTerm = 0;

        if( textHomeValue.getText().toString().length() == 0 )
            textHomeValue.setError( "Home Value is mandatory for calculation." );
        else
        {
            try{
                 homeValue = Double.parseDouble(textHomeValue.getText().toString());
            }catch (NumberFormatException ne)
            {
                textHomeValue.setError( "Please enter valid Home Value!" );
            }
        }

        if( textDownPayment.getText().toString().length() == 0 )
            textDownPayment.setError( "Down Payment is mandatory for calculation." );
        else
        {
            try{
                downPayment = Double.parseDouble(textDownPayment.getText().toString());
            }catch (NumberFormatException ne)
            {
                textDownPayment.setError( "Please enter valid Down Payment!" );
            }
        }

        if( textInterestRate.getText().toString().length() == 0 )
            textInterestRate.setError( "Interest Rate is mandatory for calculation." );
        else
        {
            try{
                interestRate = Double.parseDouble(textInterestRate.getText().toString());
            }catch (NumberFormatException ne)
            {
                textInterestRate.setError( "Please enter valid Interest Rate!" );
            }
        }

        if( textPropertyTaxRate.getText().toString().length() == 0 )
            textPropertyTaxRate.setError( "Property Tax Rate is mandatory for calculation." );
        else
        {
            try{
                propertyTaxRate = Double.parseDouble(textPropertyTaxRate.getText().toString());
            }catch (NumberFormatException ne)
            {
                textPropertyTaxRate.setError( "Please enter valid Property Tax Rate!" );
            }
        }

        term = Integer.parseInt(String.valueOf(spinnerTerm.getSelectedItem()));

        System.out.println("got all value " + homeValue + " - " + downPayment + " - " + interestRate + " - " + term + " - " + propertyTaxRate);

        monthlyTerm = term * 12;
        double principle = homeValue - downPayment;
        double totalPropertyTax =  homeValue * propertyTaxRate * term/100;

        double monthlyPropertyTax = totalPropertyTax / monthlyTerm;

        double monthlyInterestRate = interestRate/1200;

        double numerator = principle * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, monthlyTerm);
        double denominator = Math.pow(1 + monthlyInterestRate, monthlyTerm) - 1;
        double monthlyMortgagePayment = numerator/denominator;

        double totalMonthlyPayment = monthlyMortgagePayment + monthlyPropertyTax;

        double totalInterestPaid = (totalMonthlyPayment *  monthlyTerm) - principle - totalPropertyTax;

        ((EditText)findViewById(R.id.textTotalTaxPaid)).setText(totalPropertyTax + "");
        ((EditText)findViewById(R.id.textTotalInterestPaid)).setText(totalInterestPaid + "");
        ((EditText)findViewById(R.id.textTotalMonthlyPayment)).setText(totalMonthlyPayment + "");

        Calendar cal = Calendar.getInstance();
        //cal.setTime();
        cal.add(Calendar.MONTH, monthlyTerm); //minus number would decrement the days
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");

        ((EditText) findViewById(R.id.textPayOffDate)).setText(new SimpleDateFormat("MMM").format(cal.getTime()) + ", " + new SimpleDateFormat("yyyy").format(cal.getTime()));
        System.out.println("Property:" + totalPropertyTax);

    }

}
