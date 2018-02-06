package org.gra4j.gazelle.repository.model;

import java.io.Serializable;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class ParamInfo implements Serializable {

    private byte position;

    private String name;

    private Class type;

    private Object value;

    public ParamInfo(byte position, Class type, String name) {
        this.position = position;
        this.type = type;
        this.name = name;
    }

    public byte getPosition() {
        return position;
    }

    public void setPosition(byte position) {
        this.position = position;
    }

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParamInfo)) return false;

        ParamInfo paramInfo = (ParamInfo) o;

        if (getPosition() != paramInfo.getPosition()) return false;
        if (getName() != null ? !getName().equals(paramInfo.getName()) : paramInfo.getName() != null) return false;
        if (getType() != null ? !getType().equals(paramInfo.getType()) : paramInfo.getType() != null) return false;
        return getValue() != null ? getValue().equals(paramInfo.getValue()) : paramInfo.getValue() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) getPosition();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getType() != null ? getType().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ParamInfo{" +
                "position=" + position +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                '}';
    }
}
