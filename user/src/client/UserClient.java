package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enginetoui.dto.basic.DesirializeWorldDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class UserClient {
    private final OkHttpClient userClient;
    private final String BASE_URL = "localhost:8080/server";

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
