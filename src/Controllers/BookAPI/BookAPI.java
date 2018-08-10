package Controllers.BookAPI;

import Models.Book;
import Models.frontend.Resource;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;

public class BookAPI {


    public static ArrayList<Book> search(String query) {

        String encodedUrl = null;
        try {
            encodedUrl = URLEncoder.encode(query, "UTF-8");
            System.out.println(encodedUrl);
        } catch (UnsupportedEncodingException ignored) {

        }

        try {
            return Request_Json.call_me(encodedUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Resource getResourceObject(Book book) {
        Resource tempResource = new Resource("Book", book.getId(), book.getTitle(), book.getPublisherInstance(), book.getDescription());
        ArrayList<String> authors = book.getAuthors();
        Map<String, String> isbn = book.getIsbn();

        if (!authors.isEmpty())
            tempResource.setAuthor(authors.get(0));
        //Passing the first author of the book
        if (!isbn.isEmpty()) {
            if (!isbn.get("ISBN_10").isEmpty())
                tempResource.setISBN(isbn.get("ISBN_10"));
            if (!isbn.get("ISBN_13").isEmpty())
                tempResource.setISBN13(isbn.get("ISBN_13"));
        }

        return tempResource;
    }

}
