package engine.worldbuilder.factory;

import engine.World;
import engine.properties.api.PropertyInterface;
import engine.worldbuilder.prdobjects.PRDWorld;
 public class WorldFactory {
     public WorldFactory() {
     }

     public static World BuildWorld(PRDWorld prdWorld) {
         PropertyInterface property = PropertyFactory.BuildProperty(prdWorld.getPRDEntities().getPRDEntity().get(0).getPRDProperties().getPRDProperty().get(0));
         return null;
     }
}
