package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.chain.CheckChain;
import org.gra4j.gazelle.repository.Enum.CheckOps;
import org.gra4j.gazelle.repository.Enum.KeyType;
import org.gra4j.gazelle.repository.annotation.comprise.And;
import org.gra4j.gazelle.repository.annotation.comprise.Having;
import org.gra4j.gazelle.repository.annotation.comprise.Or;
import org.gra4j.gazelle.repository.annotation.comprise.Order;
import org.gra4j.gazelle.repository.annotation.core.Where;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.gra4j.gazelle.structure.EntityPool.merge;
/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class WhereModel extends JpaModel {

    private Map<KeyType,List<ExpressionModel>> emodelMap;

    private Map<KeyType,List<List<ExpressionModel>>> emodelArrayMap;

    private Map<KeyType,String[]> stringMap;

    private CheckChain check;

    {
        emodelMap = new HashMap<>(4,1);
        emodelArrayMap = new HashMap<>(2,1);
        stringMap = new HashMap<>(3,1);

        emodelMap.put(KeyType.and, new ArrayList<>());
        emodelMap.put(KeyType.ha, new ArrayList<>());
        emodelMap.put(KeyType.easc, new ArrayList<>());
        emodelMap.put(KeyType.edesc, new ArrayList<>());
        emodelArrayMap.put(KeyType.or, new ArrayList<>());
        emodelArrayMap.put(KeyType.ho, new ArrayList<>());
        stringMap.put(KeyType.asc, new String[]{});
        stringMap.put(KeyType.desc, new String[]{});
    }

    public WhereModel (Where where, List<ParamInfo> paramInfoList) {
        super(paramInfoList);
        paserAnd(where.and(), emodelMap.get(KeyType.and));
        paserOr(where.or(), emodelArrayMap.get(KeyType.or));
        paserHaving(where.having(), emodelMap.get(KeyType.ha), emodelArrayMap.get(KeyType.ho));
        paserOrder(where.order(), emodelMap.get(KeyType.easc), emodelMap.get(KeyType.edesc));
        stringMap.put(KeyType.groupBy, where.groupBy());
        check = new CheckChain();
        paserCheck(where.check());
    }

    private void paserCheck (CheckOps[] checkOps) {
        for (CheckOps ops:checkOps) {
            if (ops==CheckOps.none)
                return;
            check.addChecker(ops.name(), CheckChain.class);
        }
    }

    private void paserAnd (And[] ands, List<ExpressionModel> andExpressionModel) {
        for (And and:ands)
            paserExpression(and.value(), andExpressionModel);
    }

    private void paserOr (Or[] ors, List<List<ExpressionModel>> orExpressionModel) {
        int length = ors.length;
        for (int i=0;i<length;i++) {
            List<ExpressionModel> expressionModels = new ArrayList<>();
            paserExpression(ors[i].value(), expressionModels);
            orExpressionModel.add(expressionModels);
        }
    }

    private void paserHaving (Having[] havings, List<ExpressionModel> haExpressionModel, List<List<ExpressionModel>> hoExpressionModel) {
        for (Having having:havings) {
            paserAnd(having.and(), haExpressionModel);
            paserOr(having.or(), hoExpressionModel);
        }
    }

    private void paserOrder (Order[] orders, List<ExpressionModel> eascExpressionModel, List<ExpressionModel> edescExpressionModel) {
        for (Order order:orders) {
            stringMap.put(KeyType.asc, merge(order.asc(), stringMap.get(KeyType.asc)));
            stringMap.put(KeyType.desc, merge(order.desc(), stringMap.get(KeyType.desc)));
            paserExpression(order.easc(), eascExpressionModel);
            paserExpression(order.edesc(), edescExpressionModel);
        }
    }

    public Map<KeyType, List<ExpressionModel>> getEmodelMap() {
        return emodelMap;
    }

    public void setEmodelMap(Map<KeyType, List<ExpressionModel>> emodelMap) {
        this.emodelMap = emodelMap;
    }

    public Map<KeyType, List<List<ExpressionModel>>> getEmodelArrayMap() {
        return emodelArrayMap;
    }

    public void setEmodelArrayMap(Map<KeyType, List<List<ExpressionModel>>> emodelArrayMap) {
        this.emodelArrayMap = emodelArrayMap;
    }

    public Map<KeyType, String[]> getStringMap() {
        return stringMap;
    }

    public void setStringMap(Map<KeyType, String[]> stringMap) {
        this.stringMap = stringMap;
    }

    public CheckChain getCheck() {
        return check;
    }

    public void setCheck(CheckChain check) {
        this.check = check;
    }

    @Override
    public String toString() {
        return "WhereModel{" +
                "emodelMap=" + emodelMap +
                ", emodelArrayMap=" + emodelArrayMap +
                ", stringMap=" + stringMap +
                ", check=" + check +
                '}';
    }
}
