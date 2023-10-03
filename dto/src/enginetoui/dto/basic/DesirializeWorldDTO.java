package enginetoui.dto.basic;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import enginetoui.dto.basic.impl.*;

import java.lang.reflect.Type;
import java.util.List;

public class DesirializeWorldDTO implements JsonDeserializer<WorldDTO> {
    @Override
    public WorldDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String worldName = json.getAsJsonObject().get("worldName").getAsString();
        int simulationId = json.getAsJsonObject().get("simulationId").getAsInt();
        GridDTO gridDTO = context.deserialize(json.getAsJsonObject().get("gridDTO"), GridDTO.class);
        EnvironmentDTO environmentDTO = context.deserialize(json.getAsJsonObject().get("environment"), EnvironmentDTO.class);
        TerminationDTO terminationDTO = context.deserialize(json.getAsJsonObject().get("termination"), TerminationDTO.class);
        Type entityDTO = new TypeToken<List<EntityDTO>>() {}.getType();
        List<EntityDTO> entityDTOList = context.deserialize(json.getAsJsonObject().get("entityDefinitions"), entityDTO);

        return new WorldDTO(worldName,simulationId,null,gridDTO,environmentDTO,entityDTOList,null,null,null,null,terminationDTO);
    }
}
