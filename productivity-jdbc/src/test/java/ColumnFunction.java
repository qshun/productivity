import java.io.Serializable;
import java.util.function.Consumer;

@FunctionalInterface
public interface ColumnFunction<T> extends Consumer<T>, Serializable {

}
