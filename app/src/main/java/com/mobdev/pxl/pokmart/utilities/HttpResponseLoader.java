package com.mobdev.pxl.pokmart.utilities;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpResponseLoader {
    public static String GetResponse(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(urlConnection.getInputStream());

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");
            return scanner.next();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (in != null) {
                in.close();
            }
        }
    }
}
