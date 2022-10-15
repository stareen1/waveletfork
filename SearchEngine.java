import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

class Handler implements URLHandler {
  ArrayList<String> stringList = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            String currentString = "";
            for (int i = 0; i < stringList.size(); i++) {
              currentString = currentString + stringList.get(i);
            }
            return currentString;
        } else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    stringList.add(parameters[1]);
                    return "Added " + parameters[1] + " to string list!";
                }
            }
             if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    String matchedStrings = "";
                    for (int i = 0; i < stringList.size(); i++) {
                      if (stringList.get(i).contains(parameters[1])) {
                        matchedStrings = matchedStrings + " " + stringList.get(i) + " ";
                      }
                    }
                    return "These strings have the substring: " + parameters[1] + ":" + matchedStrings;
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new Handler());
    }
}
