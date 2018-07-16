package com.mbox.BookAPI;
import org.json.*;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.ArrayList;
import java.util.Map;

public class BookAPI {


    public static ArrayList<Book> search(String query) throws JSONException, MalformedURLException {

            String encodedUrl = null;
            try {
                encodedUrl = URLEncoder.encode(query, "UTF-8");
                System.out.println(encodedUrl.toString());
            } catch (UnsupportedEncodingException ignored) {

            }

        try {
            return Request_Json.call_me(encodedUrl.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
