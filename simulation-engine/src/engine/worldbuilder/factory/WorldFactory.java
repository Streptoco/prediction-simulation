package engine.worldbuilder.factory;

import engine.Environment;
import engine.Rule;
import engine.Termination;
import engine.World;
import engine.entity.impl.EntityDefinition;
import engine.properties.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDAction;
import engine.worldbuilder.prdobjects.PRDEntity;
import engine.worldbuilder.prdobjects.PRDRule;
import engine.worldbuilder.prdobjects.PRDWorld;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class WorldFactory {
     public static World BuildWorld(PRDWorld prdWorld) {
         List<EntityDefinition> entities = new ArrayList<>();
         for(PRDEntity entity : prdWorld.getPRDEntities().getPRDEntity()) {
             entities.add(EntityFactory.BuildEntity(entity));
         }
         // entities
         
         List<Rule> rules = new ArrayList<>();
         for(PRDRule rule : prdWorld.getPRDRules().getPRDRule()) {
             Rule currentRule = RuleFactory.BuildRule(rule);
             rules.add(currentRule);
             for (PRDAction action : rule.getPRDActions().getPRDAction()) {
                 currentRule.addAction(ActionFactory.BuildAction(action));
             }
         }
         // rules

         Termination termination = TerminationFactory.BuildTermination(prdWorld.getPRDTermination());
         // termination

         List<PropertyInterface> envProperties = new ArrayList<>();
         Environment environment = EnvironmentFactory.BuildEnvironment(prdWorld.getPRDEvironment());
         return new World(termination,entities,environment,rules);
     }
}
