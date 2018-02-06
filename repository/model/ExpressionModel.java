package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.repository.Enum.ExpressionOps;
import org.gra4j.gazelle.repository.annotation.expression.Expression;

import java.io.Serializable;

import static org.gra4j.gazelle.util.Checker.isBlank;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class ExpressionModel implements Serializable {

    private ExpressionOps ops;

    private String key;

    private String value;

    private String alias;

    private Class<?> keyType;

    private Class<?> valueType;

    private Class<?> specType;

    private BlankType blankType;

    private byte keyPosition;

    private byte valuePosition;

    private byte specPosition;

    public ExpressionModel (Expression expression) {
        this.ops = expression.ops();
        this.key = expression.key();
        this.value = expression.value();
        this.alias = expression.alias();
        findMissingParameters();
    }

    private void findMissingParameters () {
        boolean kBlank = isBlank(key);
        boolean vBlank = isBlank(value);
        if (kBlank&&vBlank) {
            blankType = BlankType.kvBlank;
        }else if (!kBlank&&vBlank) {
            blankType = BlankType.valueBlank;
        }else if (!kBlank&&!vBlank) {
            blankType = BlankType.noBlank;
        }else {
            blankType = BlankType.keyBlank;
        }
    }

    public Class[] paramType () {
        switch (blankType) {
            case kvBlank:
                return ops==ExpressionOps.between?new Class[]{keyType, valueType, specType}:new Class[]{keyType, valueType};
            case valueBlank:
                return ops==ExpressionOps.between?new Class[]{String.class, valueType, specType}:new Class[]{String.class, valueType};
            case noBlank:
                return ops==ExpressionOps.between?new Class[]{String.class, String.class, specType}:new Class[]{String.class, String.class};
            case keyBlank:
                return ops==ExpressionOps.between?new Class[]{keyType, String.class, specType}:new Class[]{keyType, String.class};
            default:
                return null;
        }
    }

    public ExpressionOps getOps() {
        return ops;
    }

    public void setOps(ExpressionOps ops) {
        this.ops = ops;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Class<?> getKeyType() {
        return keyType;
    }

    public void setKeyType(Class<?> keyType) {
        this.keyType = keyType;
    }

    public Class<?> getValueType() {
        return valueType;
    }

    public void setValueType(Class<?> valueType) {
        this.valueType = valueType;
    }

    public Class<?> getSpecType() {
        return specType;
    }

    public void setSpecType(Class<?> specType) {
        this.specType = specType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public BlankType getBlankType() {
        return blankType;
    }

    public void setBlankType(BlankType blankType) {
        this.blankType = blankType;
    }

    public byte getKeyPosition() {
        return keyPosition;
    }

    public void setKeyPosition(byte keyPosition) {
        this.keyPosition = keyPosition;
    }

    public byte getValuePosition() {
        return valuePosition;
    }

    public void setValuePosition(byte valuePosition) {
        this.valuePosition = valuePosition;
    }

    public byte getSpecPosition() {
        return specPosition;
    }

    public void setSpecPosition(byte specPosition) {
        this.specPosition = specPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExpressionModel)) return false;

        ExpressionModel that = (ExpressionModel) o;

        if (getKeyPosition() != that.getKeyPosition()) return false;
        if (getValuePosition() != that.getValuePosition()) return false;
        if (getSpecPosition() != that.getSpecPosition()) return false;
        if (getOps() != that.getOps()) return false;
        if (getKey() != null ? !getKey().equals(that.getKey()) : that.getKey() != null) return false;
        if (getValue() != null ? !getValue().equals(that.getValue()) : that.getValue() != null) return false;
        if (getAlias() != null ? !getAlias().equals(that.getAlias()) : that.getAlias() != null) return false;
        if (getKeyType() != null ? !getKeyType().equals(that.getKeyType()) : that.getKeyType() != null) return false;
        if (getValueType() != null ? !getValueType().equals(that.getValueType()) : that.getValueType() != null)
            return false;
        if (getSpecType() != null ? !getSpecType().equals(that.getSpecType()) : that.getSpecType() != null)
            return false;
        return getBlankType() == that.getBlankType();
    }

    @Override
    public int hashCode() {
        int result = getOps() != null ? getOps().hashCode() : 0;
        result = 31 * result + (getKey() != null ? getKey().hashCode() : 0);
        result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
        result = 31 * result + (getAlias() != null ? getAlias().hashCode() : 0);
        result = 31 * result + (getKeyType() != null ? getKeyType().hashCode() : 0);
        result = 31 * result + (getValueType() != null ? getValueType().hashCode() : 0);
        result = 31 * result + (getSpecType() != null ? getSpecType().hashCode() : 0);
        result = 31 * result + (getBlankType() != null ? getBlankType().hashCode() : 0);
        result = 31 * result + (int) getKeyPosition();
        result = 31 * result + (int) getValuePosition();
        result = 31 * result + (int) getSpecPosition();
        return result;
    }

    @Override
    public String toString() {
        return "ExpressionModel{" +
                "ops=" + ops +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", alias='" + alias + '\'' +
                ", keyType=" + keyType +
                ", valueType=" + valueType +
                ", specType=" + specType +
                ", blankType=" + blankType +
                ", keyPosition=" + keyPosition +
                ", valuePosition=" + valuePosition +
                ", specPosition=" + specPosition +
                '}';
    }

    public enum BlankType {
        keyBlank,valueBlank,kvBlank,noBlank
    }
}
