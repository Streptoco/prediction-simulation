import okhttp3.*;

import java.io.File;
import java.io.IOException;

public class AdminClient {
    private final OkHttpClient adminClient;
    private final String BASE_URL = "localhost:8080";

    public AdminClient() {
        adminClient = new OkHttpClient();
    }

    public void uploadFile(File f) throws IOException {
        String RESOURCE = "/upload-file";
        RequestBody body =
                new MultipartBody.Builder()
                        .addFormDataPart("file", f.getName(), RequestBody.create(f, MediaType.parse("text/plain")))
                        //.addFormDataPart("key1", "value1") // you can add multiple, different parts as needed
                        .build();
        Request request = new Request.Builder()
                .url(BASE_URL + RESOURCE)
                .post(body)
                .build();
        Call call = adminClient.newCall(request);

        Response response = call.execute();
    }
}
