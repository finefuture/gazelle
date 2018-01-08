package org.gra4j.gazelle.chain;

import org.gra4j.gazelle.structure.CheckChainMap;
import org.gra4j.gazelle.structure.CheckerPool;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * gazelle
 * org.gra4j.gazelle.chain
 *
 * @author tom.long
 */
public class CheckChain implements Check,Serializable {

    private CheckChainMap<Method, Object> chain = new CheckChainMap<>();

    private int index = 0;

    public void setCheckNull () {
        addChecker("checkNullValue", this.getClass());
    }

    public void setCheckEmpty () {
        addChecker("checkEmptyValue", this.getClass());
    }

    public void addChecker (String methodName, Class targetClass) {
        try {
            addChecker(method(methodName, targetClass, Object.class), CheckerPool.getIfExist(targetClass));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void addChecker (Method method, Object obj) {
        chain.put(method, obj);
    }

    @Override
    public boolean check (Check checkChain, Object value) {
        if(index==chain.size()) {
            index = 0;
            return true;
        }
        Map.Entry<Method, Object> check = chain.getForIndex(index++);
        boolean checkResult = (boolean) invoke(check.getKey(), check.getValue(), value);
        if (checkResult) {
            index = 0;
            return false;
        }
        return checkChain.check(checkChain, value);
    }

    public boolean checkNullValue (Object value) {
        return null==value ? true:false;
    }

    public boolean checkEmptyValue (Object value) {
        return "".equals(value) ? true:false;
    }

    public Method method (String methodName, Class targetClass, Class<?>... parameterTypes) {
        Method method = null;
        try {
            method = targetClass.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return method;
    }

    public Object invoke (Method method, Object obj, Object value) {
        Object invoke = null;
        try {
            invoke = method.invoke(obj, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return invoke;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckChain)) return false;

        CheckChain that = (CheckChain) o;

        if (index != that.index) return false;
        return chain.equals(that.chain);
    }

    @Override
    public int hashCode() {
        int result = chain.hashCode();
        result = 31 * result + index;
        return result;
    }

    @Override
    public String toString() {
        return "CheckChain{" +
                "chain=" + chain +
                ", index=" + index +
                '}';
    }
}
