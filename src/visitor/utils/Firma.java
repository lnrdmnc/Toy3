package visitor.utils;
import node.Type;
import java.util.ArrayList;

public interface Firma {

    Firma clone() ;
    Type getType();
    ArrayList<Type> getMultipleTypes();

}
