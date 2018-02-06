package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.repository.annotation.core.Special;

import java.util.ArrayList;
import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class SpecialModel extends JpaModel {

    private List<ExpressionModel> expressionModelList;

    public SpecialModel(Special special, List<ParamInfo> paramInfoList) {
        super(paramInfoList);
        this.expressionModelList = new ArrayList<>();
        paserExpression(special.exp(), expressionModelList);
    }

    public List<ExpressionModel> getExpressionModelList() {
        return expressionModelList;
    }

    public void setExpressionModelList(List<ExpressionModel> expressionModelList) {
        this.expressionModelList = expressionModelList;
    }

    @Override
    public String toString() {
        return "SpecialModel{" +
                "expressionModelList=" + expressionModelList +
                '}';
    }
}
