package client;

import okhttp3.OkHttpClient;

public class UserClient {
    private final OkHttpClient userClient;
    private final String BASE_URL = "localhost:8080";

    public UserClient() {
        userClient = new OkHttpClient();
    }

}
