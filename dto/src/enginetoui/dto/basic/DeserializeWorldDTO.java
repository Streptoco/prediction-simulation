package enginetoui.dto.basic;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import engine.entity.impl.EntityInstanceManager;
import enginetoui.dto.basic.impl.*;

import java.lang.reflect.Type;
import java.util.List;

public class DeserializeWorldDTO implements JsonDeserializer<WorldDTO> {
    @Override
    public WorldDTO deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String worldName = json.getAsJsonObject().get("worldName").getAsString();
        int simulationId = json.getAsJsonObject().get("simulationId").getAsInt();
        GridDTO gridDTO = context.deserialize(json.getAsJsonObject().get("gridDTO"), GridDTO.class);
        EnvironmentDTO environmentDTO = context.deserialize(json.getAsJsonObject().get("environment"), EnvironmentDTO.class);
        TerminationDTO terminationDTO = context.deserialize(json.getAsJsonObject().get("termination"), TerminationDTO.class);
        Type entityDTO = new TypeToken<List<EntityDTO>>() {}.getType();
        List<EntityDTO> entityDTOList = context.deserialize(json.getAsJsonObject().get("entityDefinitions"), entityDTO);
        Type ruleDTO = new TypeToken<List<RuleDTO>>() {}.getType();
        List<RuleDTO> ruleDTOList = context.deserialize(json.getAsJsonObject().get("rules"), ruleDTO);
        Type instanceDTO = new TypeToken<List<InstancesDTO>>() {}.getType();
        List<InstancesDTO> instancesDTOList = context.deserialize(json.getAsJsonObject().get("instances"), instanceDTO);
        Type managersDTO = new TypeToken<List<EntityInstanceManager>>() {}.getType();
        List<EntityInstanceManager> managersDTOList = context.deserialize(json.getAsJsonObject().get("managerList"), managersDTO);

        return new WorldDTO(worldName,simulationId,gridDTO,environmentDTO,entityDTOList,ruleDTOList,instancesDTOList,managersDTOList,null,terminationDTO);
    }
}
