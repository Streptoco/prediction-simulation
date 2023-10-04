package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import enginetoui.dto.basic.DeserializeWorldDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class UserClient {
    private final OkHttpClient userClient;
    private final String BASE_URL = "http://localhost:8080/server";

    public UserClient() {
        userClient = new OkHttpClient();
    }

    public WorldDTO getWorld() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WorldDTO.class, new DeserializeWorldDTO())
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
        return gson.fromJson(jsonObject, WorldDTO.class);
    }

    public List<WorldDTO> getAllWorlds() throws IOException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(WorldDTO.class, new DeserializeWorldDTO())
                .setPrettyPrinting()
                .create();
        String RESOURCE = "/get-worlds-list";
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = userClient.newCall(request);
        Response response = call.execute();
        String jsonObject = response.body().string();
        Type listDTO = new TypeToken<List<WorldDTO>>() {
        }.getType();
        return gson.fromJson(jsonObject, listDTO);
    }

}
