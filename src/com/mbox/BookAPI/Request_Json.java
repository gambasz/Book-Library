package com.mbox.BookAPI;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mbox.Publisher;
import javafx.scene.image.Image;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;


public class Request_Json {
    static int count2 = 0;


    public static void main(String[] args) {
        try {

            Request_Json.call_me("C++");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static JSONObject requestQuery(String query) throws Exception {
        Gson gson = new Gson();
//convert space to url format

        String url = String.format("https://www.googleapis.com/books/v1/volumes?q=%s", query);
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();

        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        JSONObject myResponse = new JSONObject(response.toString());

        return myResponse;


    }


    public static ArrayList<Book> call_me(String query) throws Exception {
        Gson gson = new Gson();

        File index = new File("output");

        if (!index.exists()) {
            index.mkdir();
        } else {
            String[] entries = index.list();
            for (String s : entries) {
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
        }

        ArrayList<Book> returnedList = new ArrayList<Book>();

        JSONObject myResponse = new JSONObject(requestQuery(query).toString());
        JSONArray allItems = myResponse.getJSONArray("items");

        for (int i = 0; i < allItems.length(); i++) {

            returnedList.add(jsonToBook(allItems.getJSONObject(i)));
            Image tempImage = getSmallImage(allItems.getJSONObject(i));

            returnedList.get(i).setIcon(tempImage);
            returnedList.get(i).getPublisherInstance().setID(i);
            returnedList.get(i).setId(i + 1);


        }
        return returnedList;
    }


    public static Book jsonToBook(JSONObject jsonObject) throws JSONException {
        Gson gson = new Gson();
        String tempPubTitle = "", publishedDate = "", tempDesc = "", tempTitle = "";
        Map<String, String> tempISBN = new HashMap<String, String>();
        List<String> tempAuthors = new ArrayList<String>();


        tempTitle = jsonObject.getJSONObject("volumeInfo").get("title").toString();
        try {
            if(jsonObject.getJSONObject("volumeInfo").has("authors"))
            tempAuthors = jsonArrayToStringArray(jsonObject.getJSONObject("volumeInfo").
                    getJSONArray("authors"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tempISBN = getIsbn(jsonObject);

//            Publisher
        try {
            if (jsonObject.has("publisher"))
                tempPubTitle = jsonObject.getJSONObject("publisher").toString();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonObject.has("publisher"))
            publishedDate = jsonObject.getJSONObject("publishedDate").toString();
        if (jsonObject.has("description"))
            tempDesc = jsonObject.getJSONObject("description").toString();

        Book returnedBook = new Book(tempTitle, (ArrayList<String>) tempAuthors, tempISBN, tempDesc, 0);
        Publisher tempPublisher = new Publisher();
        tempPublisher.setTitle(tempPubTitle);
            returnedBook.setPublisherInstance(tempPublisher);
        return returnedBook;

    }

    public static Image getSmallImage(JSONObject jsonObject) {
        Image tempImage = null;
        count2++;

        try {

            if (jsonObject.getJSONObject("volumeInfo").has("imageLinks"))
                if (jsonObject.getJSONObject("volumeInfo").getJSONObject("imageLinks").has("smallThumbnail")) {

                    try {
                        URL url = new URL(jsonObject.getJSONObject("volumeInfo").getJSONObject("imageLinks").get("smallThumbnail").toString());

                        InputStream in = new BufferedInputStream(url.openStream());
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buf = new byte[1024];
                        int n = 0;
                        while (-1 != (n = in.read(buf))) {
                            out.write(buf, 0, n);
                        }
                        out.close();
                        in.close();
                        byte[] response = out.toByteArray();
                        FileOutputStream fos = new FileOutputStream(String.format("output/Book %s %d.jpg",
                                jsonObject.getJSONObject("volumeInfo").get("title").toString(), count2));
                        fos.write(response);
                        fos.close();

                        tempImage = new Image(new FileInputStream(String.format("output/Book %s %d.jpg",
                                jsonObject.getJSONObject("volumeInfo").get("title").toString(), count2)));

                    } catch (IOException e) {
                    }
                    return tempImage;
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String, String> getIsbn(JSONObject item) throws JSONException {
        Gson gson = new Gson();

        String tempType = "", tempISBN = "";
        Map<String, String> isbnMap = new HashMap<String, String>();
        int counter = 0;
        if(item.getJSONObject("volumeInfo").has("industryIdentifiers")){
            JSONArray tempArr = item.getJSONObject("volumeInfo").getJSONArray("industryIdentifiers");
            Type listType = new TypeToken<List<HashMap<String, String>>>() {
            }.getType();
            List<HashMap<String, String>> isbns =
                    gson.fromJson(tempArr.toString(), listType);

            for (HashMap<String, String> map : isbns) {
                counter = 0;
                for (HashMap.Entry<String, String> entry : map.entrySet()) {
                    if (counter == 0) {
                        tempISBN = entry.getValue(); //Key is identifier
                    }
                    if (counter == 1) {
                        tempType = entry.getValue();
                        //Key is type
                    }
                    counter++;
                }
                if (tempType != null || tempISBN != null)
                    isbnMap.put(tempType, tempISBN);

            }
            return isbnMap;
        }
        return null;

    }

    public static void printMap(Map<String, String> map) {

        for (HashMap.Entry<String, String> entry : map.entrySet()) {
            System.out.println("Key: " + entry.getKey() + " Value: " + entry.getValue());
        }
    }

    public static ArrayList<String> jsonArrayToStringArray(JSONArray jsArray) {
        ArrayList<String> strArray = new ArrayList<String>();

        for (int j = 0; j < jsArray.length(); j++) {
            try {
                strArray.add(jsArray.get(j).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return strArray;
    }


    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

}
