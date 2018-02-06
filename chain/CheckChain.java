package org.gra4j.gazelle.chain;

import org.gra4j.gazelle.structure.CheckChainMap;
import org.gra4j.gazelle.structure.CheckerPool;

import java.io.Serializable;
import java.util.Map;

import static org.gra4j.gazelle.util.AsmUtil.fastInvoke;

/**
 * gazelle
 * org.gra4j.gazelle.chain
 *
 * @author tom.long
 */
public class CheckChain implements Check,Serializable {

    private CheckChainMap<String, Object> chain = new CheckChainMap<>();

    private int index = 0;

    public void setCheckNull () {
        addChecker("checkNullValue", this.getClass());
    }

    public void setCheckEmpty () {
        addChecker("checkEmptyValue", this.getClass());
    }

    public void addChecker (String methodName, Class targetClass) {
        if (targetClass==this.getClass()) {
            addChecker(methodName, this);
            return;
        }
        try {
            addChecker(methodName, CheckerPool.getIfExist(targetClass));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public void addChecker (String methodName, Object obj) {
        chain.put(methodName, obj);
    }

    @Override
    public boolean check (Check checkChain, Object value) {
        if(index==chain.size()) {
            index = 0;
            return true;
        }
        Map.Entry<String, Object> check = chain.getForIndex(index++);
        boolean checkResult = (boolean) fastInvoke(check.getValue(), check.getKey(), new Class[]{Object.class}, value);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckChain)) return false;

        CheckChain that = (CheckChain) o;

        if (index != that.index) return false;
        return chain != null ? chain.equals(that.chain) : that.chain == null;
    }

    @Override
    public int hashCode() {
        int result = chain != null ? chain.hashCode() : 0;
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
