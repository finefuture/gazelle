package org.gra4j.gazelle.structure;

import java.util.Arrays;
import java.util.HashMap;

/**
 * gazelle
 * org.gra4j.gazelle.structure
 *
 * @author tom.long
 */
public class CheckChainMap<K,V> extends HashMap<K,V> {

    Object[] table;

    public Entry<K, V> getForIndex (int i) {
        afterValueSet();
        return (Entry<K, V>) table[i];
    }

    private void afterValueSet () {
        if (null!=table)
            return;
        table = super.entrySet().toArray();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CheckChainMap)) return false;
        if (!super.equals(o)) return false;

        CheckChainMap<?, ?> that = (CheckChainMap<?, ?>) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(table, that.table);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.hashCode(table);
        return result;
    }

    @Override
    public String toString() {
        return "CheckChainMap{" +
                "table=" + Arrays.toString(table) +
                '}';
    }
}
