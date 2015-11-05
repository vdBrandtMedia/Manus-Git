/**
 * Copyright (C) 2015 Manus Machina
 *
 * This file is part of the Manus SDK.
 *
 * Manus SDK is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Manus SDK is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Manus SDK. If not, see <http://www.gnu.org/licenses/>.
 */

package com.manusmachina.labs.manusmobile;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.manusmachina.labs.manussdk.*;


public class ManusTestActivity extends ActionBarActivity implements ActionBar.OnNavigationListener, Manus.OnGloveChangedListener{
    /**
     * The serialization (saved instance state) Bundle key representing the
     * current dropdown position.
     */
    private static final String STATE_SELECTED_NAVIGATION_ITEM = "selected_navigation_item";
    static String TAG = "MainActivity";

    private long timestamp = System.currentTimeMillis();
    private ManusTestActivity mScope = this;
    private Manus.GloveBinder mBinder = null;
    private ArrayAdapter<String> mArray = null;
    private int mSelectedGlove = 0;
    private Menu mMenu = null;

    //Sound Pools
        SoundPool eSnaar;
        int eSnaarSlag;
        SoundPool aSnaar;
        int aSnaarSlag;
        SoundPool dSnaar;
        int dSnaarSlag;
        SoundPool gSnaar;
        int gSnaarSlag;
        SoundPool bSnaar;
        int bSnaarSlag;
        SoundPool heSnaar;
        int heSnaarSlag;

    //Media Players
        MediaPlayer Guitar1;
        MediaPlayer Guitar2;
        MediaPlayer Guitar3;
        MediaPlayer Guitar4;
        MediaPlayer Guitar5;
        MediaPlayer Guitar6;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            mBinder = (Manus.GloveBinder) service;
            mBinder.addOnGloveChangedListener(mScope);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manus_test);

        // Bind to Manus service
        Intent intent = new Intent(this, Manus.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);

        // Set up the action bar to show a dropdown list.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        // Create an array for navigation,
        mArray = new ArrayAdapter<String>(
                actionBar.getThemedContext(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                new String[] { "Glove 0", "Glove 1" });

        // Set up the dropdown list navigation in the action bar.
        actionBar.setListNavigationCallbacks(
                // Specify a SpinnerAdapter to populate the dropdown list.
                mArray,
                this);

        //Loading basic sounds
        eSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        eSnaarSlag = eSnaar.load(this, R.raw.e0, 1);

        aSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        aSnaarSlag = aSnaar.load(this, R.raw.a0, 1);

        dSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        dSnaarSlag = dSnaar.load(this, R.raw.d0, 1);

        gSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        gSnaarSlag = gSnaar.load(this, R.raw.g0, 1);

        bSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        bSnaarSlag = bSnaar.load(this, R.raw.b0, 1);

        heSnaar = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        heSnaarSlag = heSnaar.load(this, R.raw.he0, 1);

        //creating mediaplayers /*test string*/
        /*Guitar1 = MediaPlayer.create(this, R.raw.e0);
        Guitar2 = MediaPlayer.create(this, R.raw.a0);
        Guitar3 = MediaPlayer.create(this, R.raw.d0);
        Guitar4 = MediaPlayer.create(this, R.raw.g0);
        Guitar5 = MediaPlayer.create(this, R.raw.b0);
        Guitar6 = MediaPlayer.create(this, R.raw.he0);*/

       /* Button btnHold1 = (Button)(findViewById(R.id.btnHold1));
        btnHold1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold1 - Hold is true");
                    ((TextView)findViewById(R.id.holdText1)).setText("Hold is True");

                        Guitar1.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
                            public void onCompletion(MediaPlayer Guitar1){
                                Guitar1.stop();
                                Guitar1.release();
                            }
                        });

                    eSnaar.play(eSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold1 - Hold is false");
                    ((TextView)findViewById(R.id.holdText1)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });

        Button btnHold2 = (Button)(findViewById(R.id.btnHold2));
        btnHold2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold2 - Hold is true");
                    ((TextView)findViewById(R.id.holdText2)).setText("Hold is True");

                    aSnaar.play(aSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold2 - Hold is false");
                    ((TextView)findViewById(R.id.holdText2)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });

        Button btnHold3 = (Button)(findViewById(R.id.btnHold3));
        btnHold3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold3 - Hold is true");
                    ((TextView)findViewById(R.id.holdText3)).setText("Hold is True");

                    dSnaar.play(dSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold3 - Hold is false");
                    ((TextView)findViewById(R.id.holdText3)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });

        Button btnHold4 = (Button)(findViewById(R.id.btnHold4));
        btnHold4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold4 - Hold is true");
                    ((TextView)findViewById(R.id.holdText4)).setText("Hold is True");

                    gSnaar.play(gSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold4 - Hold is false");
                    ((TextView)findViewById(R.id.holdText4)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });

        Button btnHold5 = (Button)(findViewById(R.id.btnHold5));
        btnHold5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold5 - Hold is true");
                    ((TextView)findViewById(R.id.holdText5)).setText("Hold is True");

                    bSnaar.play(bSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold5 - Hold is false");
                    ((TextView)findViewById(R.id.holdText5)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });

        Button btnHold6 = (Button)(findViewById(R.id.btnHold6));
        btnHold6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN) {

                    Log.i(TAG, "btnHold6 - Hold is true");
                    ((TextView)findViewById(R.id.holdText6)).setText("Hold is True");


                    heSnaar.play(heSnaarSlag, 1, 1, 1, 0, 1);

                    return true;
                }

                if(event.getAction()==MotionEvent.ACTION_UP) {

                    Log.i(TAG, "btnHold6 - Hold is false");
                    ((TextView)findViewById(R.id.holdText6)).setText("Hold is False");

                    return false;
                }
                return false;

            }

        });*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBinder = null;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Restore the previously serialized current dropdown position.
        if (savedInstanceState.containsKey(STATE_SELECTED_NAVIGATION_ITEM)) {
            getSupportActionBar().setSelectedNavigationItem(
                    savedInstanceState.getInt(STATE_SELECTED_NAVIGATION_ITEM));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Serialize the current dropdown position.
        outState.putInt(STATE_SELECTED_NAVIGATION_ITEM,
                getSupportActionBar().getSelectedNavigationIndex());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_manus_test, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.calibrate_imu) {
            mBinder.getGlove(mSelectedGlove).calibrate(true, true, false);
            return true;
        } else if (id == R.id.calibrate_fingers) {
            mBinder.getGlove(mSelectedGlove).calibrate(false, false, true);
            ProgressDialog.show(this, "Finger Calibration", "Open and close the hands to calibrate.", true, true, new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mBinder.getGlove(mSelectedGlove).calibrate(false, false, false);
                }
            });
            return true;
        } else if (id == R.id.calibrate_hand) {
            mBinder.getGlove(mSelectedGlove).setHandedness(!item.isChecked());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(int position, long id) {
        // When the given dropdown item is selected, show its contents in the
        // container view.
        mSelectedGlove = position;
        return true;
    }

    @Override
    public void OnGloveChanged(int index, Glove glove) {
        if (index != mSelectedGlove)
            return;

        Glove.Quaternion quat = glove.getQuaternion();
        Glove.Vector euler = glove.getEuler(quat);
        Glove.Vector degrees = euler.ToDegrees();
        float[] fingers = glove.getFingers();

        SeekBar yaw = (SeekBar)findViewById(R.id.yaw);
        SeekBar pitch = (SeekBar)findViewById(R.id.pitch);
        SeekBar roll = (SeekBar)findViewById(R.id.roll);
        SeekBar[] fingerBars = {
                (SeekBar)findViewById(R.id.thumb),
                (SeekBar)findViewById(R.id.index),
                (SeekBar)findViewById(R.id.middle),
                (SeekBar)findViewById(R.id.ring),
                (SeekBar)findViewById(R.id.pinky)
        };
        SeekBar interval = (SeekBar)findViewById(R.id.interval);

        roll.setProgress((int)degrees.x + 180);
        pitch.setProgress((int)degrees.y + 90);
        yaw.setProgress((int) degrees.z + 180);
        for (int i = 0; i < 5; i++)
            fingerBars[i].setProgress((int)(fingers[i] * 255.0f));

        interval.setProgress((int)(System.currentTimeMillis() - timestamp));

        SeekBar fingerCount = (SeekBar)findViewById(R.id.fingerCount);
        fingerCount.setProgress(countBendFingers(fingers));

        /*countBendEachFinger0(fingers);
        countBendEachFinger1(fingers);
        countBendEachFinger2(fingers);
        countBendEachFinger3(fingers);
        countBendEachFinger4(fingers);*/

        mMenu.findItem(R.id.calibrate_hand).setChecked(glove.getHandedness() == Glove.Handedness.RIGHT_HAND);

        timestamp = System.currentTimeMillis();
    }
    private int countBendFingers(float[] fingers){
        int numberOfBendFingers = 0;

        for (int i = 0; i < 5; i++){
            float fingerBend = fingers[i];
            String fingerBendCheck1 = "False";
            String fingerBendCheck2 = "False";
            String fingerBendCheck3 = "False";
            String fingerBendCheck4 = "False";
            String fingerBendCheck5 = "False";

            if (fingerBend > 0.5f){
                numberOfBendFingers++;
            }

            if (numberOfBendFingers == 0){

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e0, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a0, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d0, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g0, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b0, 1);
             heSnaarSlag = heSnaar.load(this, R.raw.he0, 1);
            }
            if (fingerBend > 0.5f && i == 0){
                fingerBendCheck1 = "True";

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e1, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a1, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d1, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g1, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b1, 1);
                heSnaarSlag = heSnaar.load(this, R.raw.he1, 1);
                Log.i(TAG, "Slag variabele verandert naar 1");
            }
            if (fingerBend > 0.5f && i == 1){
                fingerBendCheck2 = "True";

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e2, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a2, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d2, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g2, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b2, 1);
                heSnaarSlag = heSnaar.load(this, R.raw.he2, 1);
                Log.i(TAG, "Slag variabele verandert naar 2");
            }
            if (fingerBend > 0.5f && i == 2){
                fingerBendCheck3 = "True";

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e3, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a3, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d3, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g3, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b3, 1);
                heSnaarSlag = heSnaar.load(this, R.raw.he3, 1);
                Log.i(TAG, "Slag variabele verandert naar 3");
            }
            if (fingerBend > 0.5f && i == 3){
                fingerBendCheck4 = "True";

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e4, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a4, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d4, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g4, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b4, 1);
                heSnaarSlag = heSnaar.load(this, R.raw.he4, 1);
                Log.i(TAG, "Slag variabele verandert naar 4");
            }
            if (fingerBend > 0.5f && i == 4){
                fingerBendCheck5 = "True";

                eSnaar.unload(this.eSnaarSlag);
                aSnaar.unload(this.aSnaarSlag);
                dSnaar.unload(this.dSnaarSlag);
                gSnaar.unload(this.gSnaarSlag);
                bSnaar.unload(this.bSnaarSlag);
                heSnaar.unload(this.heSnaarSlag);
                eSnaarSlag = eSnaar.load(this, R.raw.e5, 1);
                aSnaarSlag = aSnaar.load(this, R.raw.a5, 1);
                dSnaarSlag = dSnaar.load(this, R.raw.d5, 1);
                gSnaarSlag = gSnaar.load(this, R.raw.g5, 1);
                bSnaarSlag = bSnaar.load(this, R.raw.b5, 1);
                heSnaarSlag = heSnaar.load(this, R.raw.he5, 1);
                Log.i(TAG, "Slag variabele verandert naar 5");
            }
        }
        return numberOfBendFingers;
    }

    /*private String countBendEachFinger0(float[] fingers){
            String fingerBendCheck1 = "False";

            for (int i = 0; i < 1; i++){
                float fingerBend = fingers[i];

                if (fingerBend > 0.5f){
                    fingerBendCheck1 = "True";

                    eSnaarSlag = eSnaar.load(this, R.raw.e1, 1);
                    aSnaarSlag = aSnaar.load(this, R.raw.a1, 1);
                    dSnaarSlag = dSnaar.load(this, R.raw.d1, 1);
                    gSnaarSlag = gSnaar.load(this, R.raw.g1, 1);
                    bSnaarSlag = bSnaar.load(this, R.raw.b1, 1);
                 heSnaarSlag = heSnaar.load(this, R.raw.he1, 1);
                    Log.i(TAG, "Slag variabele verandert naar 1");

                }
            }
            return fingerBendCheck1;
        }

        private String countBendEachFinger1(float[] fingers){
            String fingerBendCheck2 = "False";

            for (int i = 1; i < 2; i++){
                float fingerBend = fingers[i];

                if (fingerBend > 0.5f){
                    fingerBendCheck2 = "True";

                    eSnaarSlag = eSnaar.load(this, R.raw.e2, 1);
                    aSnaarSlag = aSnaar.load(this, R.raw.a2, 1);
                    dSnaarSlag = dSnaar.load(this, R.raw.d2, 1);
                    gSnaarSlag = gSnaar.load(this, R.raw.g2, 1);
                    bSnaarSlag = bSnaar.load(this, R.raw.b2, 1);
                 heSnaarSlag = heSnaar.load(this, R.raw.he2, 1);
                    Log.i(TAG, "Slag variabele verandert naar 2");
                }
            }
            return fingerBendCheck2;
        }

        private String countBendEachFinger2(float[] fingers){
            String fingerBendCheck3 = "False";

            for (int i = 2; i < 3; i++){
                float fingerBend = fingers[i];

                if (fingerBend > 0.5f){
                    fingerBendCheck3 = "True";

                    eSnaarSlag = eSnaar.load(this, R.raw.e3, 1);
                    aSnaarSlag = aSnaar.load(this, R.raw.a3, 1);
                    dSnaarSlag = dSnaar.load(this, R.raw.d3, 1);
                    gSnaarSlag = gSnaar.load(this, R.raw.g3, 1);
                    bSnaarSlag = bSnaar.load(this, R.raw.b3, 1);
                 heSnaarSlag = heSnaar.load(this, R.raw.he3, 1);
                    Log.i(TAG, "Slag variabele verandert naar 3");
                }
            }
            return fingerBendCheck3;
        }

        private String countBendEachFinger3(float[] fingers){
            String fingerBendCheck4 = "False";

            for (int i = 3; i < 4; i++){
                float fingerBend = fingers[i];

                if (fingerBend > 0.5f){
                    fingerBendCheck4 = "True";

                    eSnaarSlag = eSnaar.load(this, R.raw.e4, 1);
                    aSnaarSlag = aSnaar.load(this, R.raw.a4, 1);
                    dSnaarSlag = dSnaar.load(this, R.raw.d4, 1);
                    gSnaarSlag = gSnaar.load(this, R.raw.g4, 1);
                    bSnaarSlag = bSnaar.load(this, R.raw.b4, 1);
                 heSnaarSlag = heSnaar.load(this, R.raw.he4, 1);
                    Log.i(TAG, "Slag variabele verandert naar 4");
                }
            }
            return fingerBendCheck4;
        }

        private String countBendEachFinger4(float[] fingers){
            String fingerBendCheck5 = "False";

            for (int i = 4; i < 5; i++){
                float fingerBend = fingers[i];

                if (fingerBend > 0.5f){
                    fingerBendCheck5 = "True";

                    eSnaarSlag = eSnaar.load(this, R.raw.e5, 1);
                    aSnaarSlag = aSnaar.load(this, R.raw.a5, 1);
                    dSnaarSlag = dSnaar.load(this, R.raw.d5, 1);
                    gSnaarSlag = gSnaar.load(this, R.raw.g5, 1);
                    bSnaarSlag = bSnaar.load(this, R.raw.b5, 1);
                 heSnaarSlag = heSnaar.load(this, R.raw.he5, 1);
                    Log.i(TAG, "Slag variabele verandert naar 5");
                }
            }
            return fingerBendCheck5;
    }*/

    //E Button
    public void eButton(View view) {

            Log.i(TAG, "btnHold1 - Hold is true");
            ((TextView) findViewById(R.id.holdText1)).setText("Hold is True");

            /*Guitar1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer Guitar1) {
                    Guitar1.stop();
                    Guitar1.release();
                    eSnaar.release();
                }
            });*/

            eSnaar.play(eSnaarSlag, 1, 1, 1, 0, 1);
    }

    //A Button
    public void aButton(View view) {

        Log.i(TAG, "btnHold2 - Hold is true");
        ((TextView)findViewById(R.id.holdText2)).setText("Hold is True");

        /*Guitar2.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer Guitar2) {
                Guitar2.stop();
                Guitar2.release();
                aSnaar.release();
            }
        });*/

        aSnaar.play(aSnaarSlag, 1, 1, 1, 0, 1);
    }

    //D Button
    public void dButton(View view) {

        Log.i(TAG, "btnHold3 - Hold is true");
        ((TextView)findViewById(R.id.holdText3)).setText("Hold is True");

        /*Guitar3.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer Guitar3) {
                Guitar3.stop();
                Guitar3.release();
                dSnaar.release();
            }
        });*/

        dSnaar.play(dSnaarSlag, 1, 1, 1, 0, 1);
    }

    //G Button
    public void gButton(View view) {

        Log.i(TAG, "btnHold4 - Hold is true");
        ((TextView)findViewById(R.id.holdText4)).setText("Hold is True");

        /*Guitar4.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer Guitar4) {
                Guitar4.stop();
                Guitar4.release();
                gSnaar.release();
            }
        });*/

        gSnaar.play(gSnaarSlag, 1, 1, 1, 0, 1);
    }

    //B Button
    public void bButton(View view) {

        Log.i(TAG, "btnHold5 - Hold is true");
        ((TextView)findViewById(R.id.holdText5)).setText("Hold is True");

        /*Guitar5.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer Guitar5) {
                Guitar5.stop();
                Guitar5.release();
                bSnaar.release();
            }
        });*/

        bSnaar.play(bSnaarSlag, 1, 1, 1, 0, 1);
    }

    //HE Button
    public void heButton(View view) {

        Log.i(TAG, "btnHold6 - Hold is true");
        ((TextView)findViewById(R.id.holdText6)).setText("Hold is True");

        /*Guitar6.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer Guitar6) {
                Guitar6.stop();
                Guitar6.release();
                heSnaar.release();
            }
        });*/

        heSnaar.play(heSnaarSlag, 1, 1, 1, 0, 1);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_manus_test, container, false);

            return rootView;
        }
    }

}
