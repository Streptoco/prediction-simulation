package engine.worldbuilder.factory;

import engine.general.object.Environment;
import engine.general.object.Rule;
import engine.general.object.Termination;
import engine.general.object.World;
import engine.entity.impl.EntityDefinition;
import engine.grid.impl.Grid;
import engine.property.api.PropertyInterface;
import engine.worldbuilder.prdobjects.*;
import engine.xml.NewXMLReader;

import java.util.ArrayList;
import java.util.List;

public class WorldFactory {
     public static World BuildWorld(PRDWorld prdWorld) {
         int numOfThreads = prdWorld.getPRDThreadCount();
         Environment environment = EnvironmentFactory.BuildEnvironment(prdWorld.getPRDEnvironment());
         NewXMLReader.envVariables = environment.GetAllEnvVariables();

         List<EntityDefinition> entities = new ArrayList<>();
         for(PRDEntity entity : prdWorld.getPRDEntities().getPRDEntity()) {
             entities.add(EntityFactory.BuildEntity(entity));
         }
         // entities
         
         List<Rule> rules = new ArrayList<>();
         for(PRDRule rule : prdWorld.getPRDRules().getPRDRule()) {
             Rule currentRule = RuleFactory.BuildRule(rule);
             rules.add(currentRule);
         }
         // rules

         Termination termination = TerminationFactory.BuildTermination(prdWorld.getPRDTermination());
         // termination
         // TODO: termination has to be bound dynamically upon the creation of the world

         // TODO: add all the new shit that popped up.

         Grid grid = GridFactory.BuildGrid(prdWorld.getPRDGrid());

         return new World(termination,entities,environment,rules, numOfThreads, grid);
     }
}
