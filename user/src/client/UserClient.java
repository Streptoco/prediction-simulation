package client;

import okhttp3.OkHttpClient;

public class UserClient {
    private final OkHttpClient userClient;
    private final String BASE_URL = "localhost:8080";

    public UserClient() {
        userClient = new OkHttpClient();
    }

    public WorldDTO getWorld() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WorldDTO.class, new DesirializeWorldDTO())
                .setPrettyPrinting()
                .create();
        String RESOURCE = "/get-world";
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = userClient.newCall(request);
        Response response = call.execute();
        String jsonObject = response.body().string();
        System.out.println(jsonObject);
        return gson.fromJson(jsonObject, WorldDTO.class);
    }

}
