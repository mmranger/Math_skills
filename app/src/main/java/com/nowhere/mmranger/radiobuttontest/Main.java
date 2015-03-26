package com.nowhere.mmranger.radiobuttontest;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


public class Main extends ActionBarActivity {

    int firstNumber, secondNumber, attempt;
    int tries = 0;

    String tempTest;
    TextView txtProblem, txtEncourage;
    EditText txtAnswer;
    Button btnAnother, btnCheckIt, btnHelpMe;
    InputMethodManager mgr;
    ImageView img_one, img_two, img_three, img_four, img_five, img_six, img_seven, img_eight, img_nine, img_ten;
    Problem aProblem;
    //GridView grdCountingCubes;
    //ImageView imgCountingArray[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        /*
        if(getResources().getDisplayMetrics().widthPixels>getResources().getDisplayMetrics().
                heightPixels)
        {
            Toast.makeText(this,"Screen switched to Landscape mode",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this,"Screen switched to Portrait mode",Toast.LENGTH_SHORT).show();
        }
        */
        txtProblem = (TextView)findViewById(R.id.problem);
        txtAnswer = (EditText)findViewById(R.id.etxAttempt);
        btnAnother = (Button)findViewById(R.id.btnNew);
        btnCheckIt = (Button)findViewById(R.id.btnCheck);
        txtEncourage = (TextView)findViewById(R.id.txtEncourage);
        btnHelpMe = (Button)findViewById(R.id.btnHelp);
        img_one = (ImageView)findViewById(R.id.firstImageView);
        img_two = (ImageView)findViewById(R.id.secondImageView);
        img_three = (ImageView)findViewById(R.id.thirdImageView);
        img_four = (ImageView)findViewById(R.id.fourthImageView);
        img_five = (ImageView)findViewById(R.id.fifthImageView);
        img_six = (ImageView)findViewById(R.id.sixthImageView);
        img_seven = (ImageView)findViewById(R.id.seventhImageView);
        img_eight = (ImageView)findViewById(R.id.eighthImageView);
        img_nine = (ImageView)findViewById(R.id.ninthImageView);
        img_ten = (ImageView)findViewById(R.id.tenthImageView);
        //mgr created to manipulate the on screen keyboard.
        mgr = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //grdCountingCubes = (GridView) findViewById(R.id.grdCountingImages);
        ClearAll();

        final RadioGroup signs = (RadioGroup)findViewById(R.id.rgpSigns);
        signs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                RadioButton rb = (RadioButton) findViewById(checkedId);
                tempTest = "" + rb.getText();
                tries = 0;

                if (tempTest.equals("Addition")) {
                    aProblem = new Problem('+', " + ");
                    Generator();
                    Toast.makeText(getApplicationContext(), tempTest, Toast.LENGTH_SHORT).show();
                } else if (tempTest.equals("Subtraction")) {
                    aProblem = new Problem('-', " - ");
                    Generator();
                    Toast.makeText(getApplicationContext(), tempTest, Toast.LENGTH_SHORT).show();
                } else if (tempTest.equals("Multiplication")) {
                    aProblem = new Problem('*', " * ");
                    Generator();
                    Toast.makeText(getApplicationContext(), tempTest, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tries = 0;

                if (tempTest.equals("Addition")) {
                    aProblem = new Problem('+', " + ");
                    Generator();
                } else if (tempTest.equals("Subtraction")) {
                    aProblem = new Problem('-', " - ");
                    Generator();
                } else if (tempTest.equals("Multiplication")) {
                    aProblem = new Problem('*', " * ");
                    Generator();
                }
            }
        });
        btnCheckIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean goodAttempt;

                try {
                    aProblem.setUserAttempt(Integer.parseInt(txtAnswer.getText().toString()));
                    goodAttempt = true;
                    mgr.hideSoftInputFromWindow(txtAnswer.getWindowToken(), 0);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Please enter a number.", Toast.LENGTH_SHORT).show();
                    txtAnswer.requestFocus();
                    goodAttempt = false;
                }
                if (tempTest.equals("Addition") && goodAttempt) {
                    checkIt();
                }
                if (tempTest.equals("Subtraction") && goodAttempt) {
                    checkIt();
                }
                if (tempTest.equals("Multiplication") && goodAttempt) {
                    checkIt();
                }
            }
        });

        btnHelpMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tempTest.equals("Addition")) {
                    img_one.setVisibility(View.VISIBLE);
                    img_two.setVisibility(View.VISIBLE);
                    txtEncourage.setText("Count all the stars together to get your answer.");
                    //imgCountingArray[0].setImageResource(aProblem.getFirstNumber());
                    //imgCountingArray[1].setImageResource(aProblem.getSecondNumber());
                    img_one.setImageResource(DetermineImage(aProblem.getFirstNumber()));
                    img_two.setImageResource(DetermineImage(aProblem.getSecondNumber()));
                } else if (tempTest.equals("Subtraction")) {
                    img_one.setVisibility(View.VISIBLE);
                    img_two.setVisibility(View.VISIBLE);
                    txtEncourage.setText("??????");
                    img_one.setImageResource(DetermineImage(aProblem.getFirstNumber()));
                    img_two.setImageResource(DetermineImage(aProblem.getSecondNumber()));
                    /*if (firstNumber > secondNumber) {
                        img_one.setImageResource(DetermineImage(firstNumber));
                        img_two.setImageResource(DetermineImage(secondNumber));
                    } else {
                        img_one.setImageResource(DetermineImage(secondNumber));
                        img_two.setImageResource(DetermineImage(firstNumber));
                    }*/
                } else if (tempTest.equals("Multiplication")) {
                    DetermineMultiplication();
                    txtEncourage.setText("Let's add up the stars.");
                }
            }
        });
    }

    private void DetermineMultiplication() {
        int secondNumber = DetermineImage(aProblem.getSecondNumber());
        int firstNumber = aProblem.getFirstNumber();
        if(firstNumber > 0){
            img_one.setImageResource(secondNumber);
            img_one.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 1){
            img_two.setImageResource(secondNumber);
            img_two.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 2){
            img_three.setImageResource(secondNumber);
            img_three.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 3){
            img_four.setImageResource(secondNumber);
            img_four.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 4){
            img_five.setImageResource(secondNumber);
            img_five.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 5){
            img_six.setImageResource(secondNumber);
            img_six.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 6){
            img_seven.setImageResource(secondNumber);
            img_seven.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 7){
            img_eight.setImageResource(secondNumber);
            img_eight.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 8){
            img_nine.setImageResource(secondNumber);
            img_nine.setVisibility(View.VISIBLE);
        }
        if(firstNumber > 9){
            img_ten.setImageResource(secondNumber);
            img_ten.setVisibility(View.VISIBLE);
        }
    }

    private int DetermineImage(int aNumber) {

        int duh;

        switch (aNumber){
            case 0: duh = R.drawable.img_zero;
                break;
            case 1: duh = R.drawable.img_one;
                break;
            case 2: duh = R.drawable.img_two;
                break;
            case 3: duh = R.drawable.img_three;
                break;
            case 4: duh = R.drawable.img_four;
                break;
            case 5: duh = R.drawable.img_five;
                break;
            case 6: duh = R.drawable.img_six;
                break;
            case 7: duh = R.drawable.img_seven;
                break;
            case 8: duh = R.drawable.img_eight;
                break;
            case 9: duh = R.drawable.img_nine;
                break;
            case 10: duh = R.drawable.img_ten;
                break;
            default:
                duh = R.drawable.img_zero;
                break;
        }
        return duh;
    }

    private void checkIt()
    {   //this method pulls the users attempt and compares it to the correct answer

        if(aProblem.getUserAttempt() == aProblem.getCorrectAnswer())
        {
            txtEncourage.setText("Correct, Way to go!!!");
            txtEncourage.setVisibility(View.VISIBLE);
            btnCheckIt.setVisibility(View.INVISIBLE);
            btnHelpMe.setVisibility(View.INVISIBLE);
            mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            mgr.hideSoftInputFromWindow(txtAnswer.getWindowToken(), 0);

        }else
        {
            encouragement(tries);
            tries += 1;
        }
    }

    private void encouragement(int tries)
    {   //This method gives encouragement and offers the help button after 2 tries
        if(tries == 0)
        {
            txtEncourage.setVisibility(View.VISIBLE);
            txtEncourage.setText("No, You can do it try again.");
            txtAnswer.getText().clear();
            txtAnswer.requestFocus();
        }else if(tries == 1)
        {
            txtEncourage.setText("Not quite, need help?");
            btnHelpMe.setVisibility(View.VISIBLE);
            txtAnswer.getText().clear();
            txtAnswer.requestFocus();
        }else
        {
            txtEncourage.setText("Okay, here is the correct answer");
            btnHelpMe.setVisibility(View.INVISIBLE);
            btnCheckIt.setVisibility(View.INVISIBLE);
            txtAnswer.getText().clear();
            txtAnswer.setText("" + aProblem.getCorrectAnswer());
        }
    }
    //Attempts were made to use the DialogFragment to announce errors with user input
    //May attempt again at some point.
    /*public static class NoAnswerError extends DialogFragment{

        public AlertDialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.err_no_answer);
            builder.setPositiveButton(R.string.err_accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            // Create the AlertDialog object and return it
            return builder.create();
        }

    }*/
    private void ResetVisibility() {
        //method to reset visibility to defaults with a new problem
        txtProblem.setVisibility(View.VISIBLE);
        txtAnswer.setVisibility(View.VISIBLE);
        btnAnother.setVisibility(View.VISIBLE);
        btnCheckIt.setVisibility(View.VISIBLE);
        txtEncourage.setVisibility(View.INVISIBLE);
        txtAnswer.setText("");
        txtAnswer.setHint("?");
        img_one.setVisibility(View.INVISIBLE);
        img_two.setVisibility(View.INVISIBLE);
        img_three.setVisibility(View.INVISIBLE);
        img_four.setVisibility(View.INVISIBLE);
        img_five.setVisibility(View.INVISIBLE);
        img_six.setVisibility(View.INVISIBLE);
        img_seven.setVisibility(View.INVISIBLE);
        img_eight.setVisibility(View.INVISIBLE);
        img_nine.setVisibility(View.INVISIBLE);
        img_ten.setVisibility(View.INVISIBLE);
        btnHelpMe.setVisibility(View.INVISIBLE);
        //grdCountingCubes.setVisibility(View.INVISIBLE);
    }

    private void ClearAll() {
        //method makes this invisible
        txtProblem.setVisibility(View.INVISIBLE);
        txtAnswer.setVisibility(View.INVISIBLE);
        btnCheckIt.setVisibility(View.INVISIBLE);
        btnAnother.setVisibility(View.INVISIBLE);
        btnHelpMe.setVisibility(View.INVISIBLE);
        txtEncourage.setVisibility(View.INVISIBLE);
        txtAnswer.requestFocus();
        txtAnswer.getText().clear();
        txtAnswer.setHint("?");
        //grdCountingCubes.setVisibility(View.INVISIBLE);
    }

    private void Generator() {
        //This method creates the math problem,
        txtProblem.setText("");
        txtProblem.setVisibility(View.VISIBLE);
        txtProblem.setText("" + aProblem.getFirstNumber() + aProblem.getStrSign() + aProblem.getSecondNumber() + " = ");
        ResetVisibility();
        aProblem.setCorrectAnswer();
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
