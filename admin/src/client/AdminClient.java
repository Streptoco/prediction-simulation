package client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enginetoui.dto.basic.DesirializeWorldDTO;
import enginetoui.dto.basic.impl.WorldDTO;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

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
        Gson gson = new Gson();
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
}
