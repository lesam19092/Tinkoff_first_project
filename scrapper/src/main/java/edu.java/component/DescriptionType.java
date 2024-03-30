package edu.java.component;

import java.util.List;

public enum DescriptionType {
    UPDATING_DATA,
    NEW_COMMENT,
    NEW_ANSWER;

    public static String getDescription(List<DescriptionType> list) {
        StringBuilder str = new StringBuilder();
        for (DescriptionType type : list) {
            if (type == UPDATING_DATA) {
                str.append("обновление данных : ");
            } else if (type == NEW_COMMENT) {
                str.append("\n" + "появился новый ответ");
            } else if (type == NEW_ANSWER) {
                str.append("\n" + "появился новый комментарий");
            }
        }
        return str.toString();
    }

}
