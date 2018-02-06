package org.gra4j.gazelle.repository.model;

import org.gra4j.gazelle.repository.Enum.ExpressionOps;
import org.gra4j.gazelle.repository.annotation.expression.Expression;

import java.util.List;

/**
 * gazelle
 * org.gra4j.gazelle.repository.model
 *
 * @author tom.long
 */
public class JpaModel {

    private List<ParamInfo> paramInfoList;

    private int offset = 0;

    public JpaModel (List<ParamInfo> paramInfoList) {
        this.paramInfoList = paramInfoList;
    }

    protected void paserExpression (Expression[] expressionList, List<ExpressionModel> expressionModelList) {
        int size = expressionList.length;
        try {
            for (int i = 0; i < size; i++) {
                ExpressionModel model = new ExpressionModel(expressionList[i]);
                ExpressionModel.BlankType blankType = model.getBlankType();
                ExpressionOps ops = model.getOps();
                ParamInfo paramInfo;
                switch (blankType) {
                    case kvBlank:
                        paramInfo = paramInfoList.get(offset++);
                        model.setKeyType(paramInfo.getType());
                        model.setKeyPosition(paramInfo.getPosition());
                        paramInfo = paramInfoList.get(offset++);
                        model.setValueType(paramInfo.getType());
                        model.setValuePosition(paramInfo.getPosition());
                        break;
                    case valueBlank:
                        paramInfo = paramInfoList.get(offset++);
                        model.setValueType(paramInfo.getType());
                        model.setValuePosition(paramInfo.getPosition());
                        break;
                    case keyBlank:
                        paramInfo = paramInfoList.get(offset++);
                        model.setKeyType(paramInfo.getType());
                        model.setKeyPosition(paramInfo.getPosition());
                        break;
                }
                if (ops == ExpressionOps.between) {
                    paramInfo = paramInfoList.get(offset++);
                    model.setSpecType(paramInfo.getType());
                    model.setSpecPosition(paramInfo.getPosition());
                }
                expressionModelList.add(model);
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            throw new ArrayIndexOutOfBoundsException("parameters :"+paramInfoList+"  is error");
        }
    }

}
