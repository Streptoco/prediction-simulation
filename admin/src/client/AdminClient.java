package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import enginetoui.dto.basic.DeserializeWorldDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import okhttp3.*;
import request.impl.AllocationRequest;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class AdminClient {
    private final OkHttpClient adminClient;
    private final String BASE_URL = "http://localhost:8080/server";


    public AdminClient() {
        adminClient = new OkHttpClient();
    }

    public void uploadFile(File f) throws IOException {
        String RESOURCE = "/upload-file";
        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                        .build();
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();
        Call call = adminClient.newCall(request);
        Response response = call.execute();
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
        Call call = adminClient.newCall(request);
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
        Call call = adminClient.newCall(request);
        Response response = call.execute();
        String jsonObject = response.body().string();
        Type listDTO = new TypeToken<List<WorldDTO>>() {
        }.getType();
        return gson.fromJson(jsonObject, listDTO);
    }

    public AllocationRequest getLatestRequest() throws IOException {
        String RESOURCE = "/get-last-request";
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = adminClient.newCall(request);
        Response response = call.execute();
        String jsonObject = response.body().string();
        return gson.fromJson(jsonObject, AllocationRequest.class);
    }

    public List<AllocationRequest> getAllRequests() throws IOException {
        String RESOURCE = "/get-all-requests";
        Gson gson = new Gson();
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .get()
                .build();
        Call call = adminClient.newCall(request);
        Response response = call.execute();
        String jsonObject = response.body().string();
        Type requestList = new TypeToken<List<AllocationRequest>>() {
        }.getType();
        return gson.fromJson(jsonObject, requestList);
    }
}
