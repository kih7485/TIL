package tobyspring.hellospring.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecutor implements ApiExecutor{
    @Override
    public String execute(URI uri) throws IOException {
        String response;
        HttpURLConnection urlConnection = (HttpURLConnection) uri.toURL().openConnection();
        try(BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()))){
            response = br.lines().collect(Collectors.joining());
        }
        return response;
    }
}
