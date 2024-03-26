package edu.java.component;

import java.util.List;

public enum DescriptionType {
    UPDATING_DATA,
    NEW_COMMENT,
    NEW_ANSWER;

    public static String getDescription(List<DescriptionType> list) {
        StringBuilder str = new StringBuilder();
        for (DescriptionType type : list) {
            switch (type) {
                case UPDATING_DATA -> str.append("обновление данных : ");
                case NEW_COMMENT -> str.append("\n" + "появился новый ответ");
                case NEW_ANSWER -> str.append("\n" + "появился новый комментарий");
            }
        }
        return str.toString();
    }

}
