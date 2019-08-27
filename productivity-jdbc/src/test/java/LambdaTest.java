import org.junit.Test;
import org.springframework.core.ResolvableType;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class LambdaTest {
    @Test
    public void test01() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ColumnFunction<TestModel> lambda = TestModel::getName;
        Class<?> cls = lambda.getClass();
        if (cls.isSynthetic()) {
            Method method = cls.getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda sl = (SerializedLambda) method.invoke(lambda);
            System.out.println(sl.getImplMethodName());
        } else {

        }

    }

    public <T> void getG(T t) {

    }

    @Test
    public void testGetG() throws NoSuchMethodException {
        ResolvableType resolvableType;
        Method method=getClass().getDeclaredMethod("getG",Object.class);
        resolvableType=ResolvableType.forMethodParameter(method,0);
        System.out.println(resolvableType.getGenerics().length);
    }
}
