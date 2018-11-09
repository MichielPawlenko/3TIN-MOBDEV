package com.mobdev.pxl.pokmart;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mobdev.pxl.pokmart.utilities.FetchAddressIntentService;
import com.mobdev.pxl.pokmart.utilities.ShoppingCartHelper;

public class CheckoutActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "Pokemart";
    private EditText mAddressField;
    private Button orderButton;

    private AddressResultReceiver mResultReceiver;
    private LocationManager mLocationManager;
    private Location mLastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        mAddressField = (EditText) findViewById(R.id.addressTextView);
        orderButton = (Button) findViewById(R.id.orderButton);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black);
        actionbar.setTitle("Checkout");

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mResultReceiver = new AddressResultReceiver(new Handler());

        createNotificationChannel();
        new getUserLocation().execute();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    protected void startIntentService() {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(FetchAddressIntentService.Constants.RECEIVER, mResultReceiver);
        intent.putExtra(FetchAddressIntentService.Constants.LOCATION_DATA_EXTRA, mLastLocation);
        startService(intent);
    }

    public void onOrderButton(View view) {
        EditText firstName = (EditText) findViewById(R.id.firstNameField);
        EditText lastName = (EditText) findViewById(R.id.lastNameField);
        EditText address = (EditText) findViewById(R.id.addressTextView);
        EditText cardNumber = (EditText) findViewById(R.id.creditCardNumberEditBox);
        EditText securityNumber = (EditText) findViewById(R.id.securityNumberEditBox);
        EditText expiryDate = (EditText) findViewById(R.id.expiryDateEditBox);
        if (firstName.getText().length() > 0 &&
                lastName.getText().length() > 0 &&
                address.getText().length() > 0 &&
                cardNumber.getText().length() > 0 &&
                securityNumber.getText().length() > 0 &&
                expiryDate.getText().length() > 0) {
            if (cardNumber.getText().toString().matches("^[0-9]{16}$")) {
                if (securityNumber.getText().toString().matches("^[0-9]{3}$")) {
                    if (expiryDate.getText().toString().matches("^(0[1-9]|1[0-2])\\/?([0-9]{4}|[0-9]{2})$")) {

                        final Intent emptyIntent = new Intent(getApplicationContext(), MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0,
                                emptyIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("Order received")
                                .setContentText("We have received your pokemon order and it will be shipped to you soon.")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, mBuilder.build());

                        ShoppingCartHelper.clearCart();

                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("canGoBack", false);
                        startActivity(intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Expiry date is incorrect.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Security Code is invalid.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Card Number is invalid.", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Pokemart";
            String description = "Pokemon shop";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public class getUserLocation extends AsyncTask<Void, Void, Void> {
        private Location getLastBestLocation() {
            Location locationGPS = mLocationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            Location locationNet = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (locationGPS == null) {
                return locationNet;
            }

            if (locationNet == null) {
                return locationGPS;
            }

            long GPSLocationTime = 0;
            GPSLocationTime = locationGPS.getTime();

            long NetLocationTime = 0;
            NetLocationTime = locationNet.getTime();

            if (0 < GPSLocationTime - NetLocationTime) {
                return locationGPS;
            } else {
                return locationNet;
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if (!Geocoder.isPresent()) {
                Log.e("GEO", "Geocoder not present");
                return null;
            }

            mLastLocation = getLastBestLocation();
            if (mLastLocation == null) {
                Log.e("GEO", "Last location is null");
                publishProgress();
                return null;
            }

            // Start service and update UI to reflect new location
            startIntentService();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            Toast.makeText(CheckoutActivity.this, "Unable to get last location, try again later.", Toast.LENGTH_LONG).show();
        }
    }

    class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultData == null) {
                mAddressField.setText("Failed to get location.");
                return;
            }

            // Display the address string
            // or an error message sent from the intent service.
            String mAddressOutput = resultData.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY);
            if (mAddressOutput == null) {
                mAddressOutput = "Failed to get location.";
            }


            mAddressField.setText(mAddressOutput);
        }
    }

}
