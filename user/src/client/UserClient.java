package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import enginetoui.dto.basic.DeserializeWorldDTO;
import enginetoui.dto.basic.RequestDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserClient {
    private final OkHttpClient userClient;
    private final String BASE_URL = "http://localhost:8080/server";

    public UserClient() {
        userClient = new OkHttpClient();

        /** This piece of code relevant only for dev phase in order to  detect memory leaks in unclosed connection **/
        Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
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

    public void newRequest(RequestDTO request) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        Gson gson = new Gson();
        String json = gson.toJson(request);
        RequestBody body = RequestBody.create(json, JSON);
        String RESOURCE = "/new-request";
        Request httpRequest = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();
        Call call = userClient.newCall(httpRequest);
        Response response = call.execute();
        String jsonObject = response.body().string();
        System.out.println(jsonObject);
    }

}
