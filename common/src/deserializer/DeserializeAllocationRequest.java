package deserializer;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import request.impl.AllocationRequest;

import java.lang.reflect.Type;

public class DeserializeAllocationRequest implements JsonDeserializer<AllocationRequest> {
    @Override
    public AllocationRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String simulationName = json.getAsJsonObject().get("simulationName").getAsString();
        int numOfRuns = json.getAsJsonObject().get("numOfRuns").getAsInt();
        int secondsToRun = json.getAsJsonObject().get("secondsToRun").getAsInt();
        int ticksToRun = json.getAsJsonObject().get("ticksToRun").getAsInt();
        String username = json.getAsJsonObject().get("username").getAsString();
        return new AllocationRequest(simulationName, numOfRuns, ticksToRun, secondsToRun, username);
    }
}
