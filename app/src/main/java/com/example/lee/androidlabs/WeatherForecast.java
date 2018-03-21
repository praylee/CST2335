package com.example.lee.androidlabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends Activity {

    ProgressBar progressBar;
    TextView currentTemperature;
    TextView maxTemperature;
    TextView minTemperature;
    TextView windSpeed;
    ImageView currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        currentTemperature = (TextView) findViewById(R.id.current_temperature);
        maxTemperature = (TextView) findViewById(R.id.max_temperature);
        minTemperature = (TextView) findViewById(R.id.min_temperature);
        windSpeed = (TextView) findViewById(R.id.wind_speed);
        currentWeather = (ImageView)findViewById(R.id.current_weather);
        new ForecastQuery().execute("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
    }


    class ForecastQuery extends AsyncTask<String, Integer, String>{

        String speed;
        String min;
        String max;
        String currTemperature;
        Bitmap currWeather;


        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            currentTemperature.setText(currTemperature);
            maxTemperature.setText(max);
            minTemperature.setText(min);
            windSpeed.setText(speed);
            currentWeather.setImageBitmap(currWeather);
            progressBar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            InputStream in = null;
            try {
                String urlString = strings[0];
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                in = conn.getInputStream();
                XmlPullParserFactory xppFactory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = xppFactory.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, "UTF-8");
                int eventType = parser.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT){
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            String name = parser.getName();
                            if (("temperature").equals(name)) {
                                currTemperature = parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                Log.i("temperature",currTemperature);
                                max = parser.getAttributeValue(null, "max");
                                publishProgress(50);
                                min = parser.getAttributeValue(null, "min");
                                publishProgress(75);
                            }else if(("speed").equals(name)){
                                speed = parser.getAttributeValue(null, "value");
                            }else if (("weather").equals(name)){
                                String iconName = parser.getAttributeValue(null,"icon");
                                String imagefile = iconName + ".png";
                                Log.i("Looking for image file","File Name:"+imagefile);
                                if(fileExistance(imagefile)){
                                    Log.i("File exsits",imagefile);
                                    FileInputStream fis = null;
                                    try {    fis = openFileInput(imagefile);   }
                                    catch (FileNotFoundException e) {    e.printStackTrace();  }
                                    currWeather = BitmapFactory.decodeStream(fis);
                                }else {
                                    Log.i("File doesn't exist",imagefile);
                                    String imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                                    currWeather = getImage(new URL(imageUrl));
                                    FileOutputStream outputStream = openFileOutput( iconName + ".png", MODE_PRIVATE);
                                    currWeather.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(100);

                            }
                        case XmlPullParser.END_TAG:
                            break;
                        default:
                            break;
                    }
                    eventType = parser.next();
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } finally {
                if(in!=null)
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        public Bitmap getImage(URL url) {
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == 200) {
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } else
                    return null;
            } catch (Exception e) {
                return null;
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        }

        public boolean fileExistance(String fname){
            File file = getBaseContext().getFileStreamPath(fname);
            if(file.exists()){
                Log.i("File Path:",file.getAbsolutePath());
            }
            return file.exists();
        }

    }
}
