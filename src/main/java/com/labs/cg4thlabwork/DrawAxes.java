package com.labs.cg4thlabwork;

import com.labs.cg4thlabwork.core.Vector3D;
import com.labs.cg4thlabwork.render.Camera3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawAxes {
    public static void drawAxes(GraphicsContext gc, Camera3D camera, Canvas canvas) {
        // Центр экрана
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        // Оси X, Y и Z (положительные и отрицательные направления)
        Vector3D origin = new Vector3D(0, 0, 0);
        Vector3D xAxisPositive = new Vector3D(600, 0, 0);  // Положительное направление оси X
        Vector3D xAxisNegative = new Vector3D(-600, 0, 0); // Отрицательное направление оси X
        Vector3D yAxisPositive = new Vector3D(0, 375, 0);  // Положительное направление оси Y
        Vector3D yAxisNegative = new Vector3D(0, -375, 0); // Отрицательное направление оси Y
        Vector3D zAxisPositive = new Vector3D(0, 0, 375);  // Положительное направление оси Z
        Vector3D zAxisNegative = new Vector3D(0, 0, -375); // Отрицательное направление оси Z

        // Проецируем точки на экран через камеру
        Vector3D screenOrigin = camera.project(origin);
        Vector3D screenXPos = camera.project(xAxisPositive);
        Vector3D screenXNeg = camera.project(xAxisNegative);
        Vector3D screenYPos = camera.project(yAxisPositive);
        Vector3D screenYNeg = camera.project(yAxisNegative);
        Vector3D screenZPos = camera.project(zAxisPositive);
        Vector3D screenZNeg = camera.project(zAxisNegative);

        // Центр координат в экранных координатах
        double screenX1 = centerX + screenOrigin.x;
        double screenY1 = centerY - screenOrigin.y;

        // Рисуем положительные оси (сплошные линии)
        gc.setLineDashes(); // Убираем пунктир
        gc.setStroke(Color.RED);
        gc.strokeLine(screenX1, screenY1, centerX + screenXPos.x, centerY - screenXPos.y); // Ось X+
        gc.setStroke(Color.GREEN);
        gc.strokeLine(screenX1, screenY1, centerX + screenYPos.x, centerY - screenYPos.y); // Ось Y+
        gc.setStroke(Color.BLUE);
        gc.strokeLine(screenX1, screenY1, centerX + screenZPos.x, centerY - screenZPos.y); // Ось Z+

        // Рисуем отрицательные оси (пунктирные линии)
        gc.setLineDashes(10, 10); // Устанавливаем пунктирный стиль: 10px линия, 10px пробел
        gc.setStroke(Color.RED);
        gc.strokeLine(screenX1, screenY1, centerX + screenXNeg.x, centerY - screenXNeg.y); // Ось X-
        gc.setStroke(Color.GREEN);
        gc.strokeLine(screenX1, screenY1, centerX + screenYNeg.x, centerY - screenYNeg.y); // Ось Y-
        gc.setStroke(Color.BLUE);
        gc.strokeLine(screenX1, screenY1, centerX + screenZNeg.x, centerY - screenZNeg.y); // Ось Z-

        // Убираем пунктирный стиль
        gc.setLineDashes();

        // Добавляем разметку вдоль осей с шагом 50
        drawAxisMarks(gc, camera, centerX, centerY, 600, 100, Color.RED, 'X'); // Для оси X
        drawAxisMarks(gc, camera, centerX, centerY, 375, 100, Color.GREEN, 'Y'); // Для оси Y
        drawAxisMarks(gc, camera, centerX, centerY, 375, 100, Color.BLUE, 'Z'); // Для оси Z
    }

    private static void drawAxisMarks(GraphicsContext gc, Camera3D camera, double centerX, double centerY,
                                      double axisLength, double step, Color color, char axis) {
        // Устанавливаем цвет разметки
        gc.setStroke(color);
        gc.setFill(Color.BLACK); // Цвет текста меток

        // Проходим по положительным и отрицательным значениям оси
        for (double i = step; i <= axisLength; i += step) {
            // Положительное направление
            Vector3D positiveMark = switch (axis) {
                case 'X' -> new Vector3D(i, 0, 0);
                case 'Y' -> new Vector3D(0, i, 0);
                case 'Z' -> new Vector3D(0, 0, i);
                default -> throw new IllegalArgumentException("Unknown axis: " + axis);
            };

            // Отрицательное направление
            Vector3D negativeMark = switch (axis) {
                case 'X' -> new Vector3D(-i, 0, 0);
                case 'Y' -> new Vector3D(0, -i, 0);
                case 'Z' -> new Vector3D(0, 0, -i);
                default -> throw new IllegalArgumentException("Unknown axis: " + axis);
            };

            // Проецируем точки на экран
            Vector3D screenPosMark = camera.project(positiveMark);
            Vector3D screenNegMark = camera.project(negativeMark);

            // Координаты экрана
            double screenPosX = centerX + screenPosMark.x;
            double screenPosY = centerY - screenPosMark.y;
            double screenNegX = centerX + screenNegMark.x;
            double screenNegY = centerY - screenNegMark.y;

            // Рисуем разметку (небольшие линии)
            if (axis == 'X') {
                gc.strokeLine(screenPosX, screenPosY - 5, screenPosX, screenPosY + 5); // Положительное X
                gc.strokeLine(screenNegX, screenNegY - 5, screenNegX, screenNegY + 5); // Отрицательное X
            } else if (axis == 'Y') {
                gc.strokeLine(screenPosX - 5, screenPosY, screenPosX + 5, screenPosY); // Положительное Y
                gc.strokeLine(screenNegX - 5, screenNegY, screenNegX + 5, screenNegY); // Отрицательное Y
            } else if (axis == 'Z') {
                gc.strokeLine(screenPosX - 5, screenPosY, screenPosX + 5, screenPosY); // Положительное Z
                gc.strokeLine(screenNegX - 5, screenNegY, screenNegX + 5, screenNegY); // Отрицательное Z
            }

            // Добавляем числовые метки рядом с линиями
            gc.fillText(String.valueOf((int) i), screenPosX + 10, screenPosY + 10); // Положительное значение
            gc.fillText(String.valueOf((int) -i), screenNegX + 10, screenNegY + 10); // Отрицательное значение
        }
    }
}



/*
package com.labs.cg4thlabwork;

import com.labs.cg4thlabwork.core.Vector3D;
import com.labs.cg4thlabwork.render.Camera3D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawAxes {
    public static void drawAxes(GraphicsContext gc, Camera3D camera, Canvas canvas) {
        // Центр экрана
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        // Оси X, Y и Z (положительные и отрицательные направления)
        Vector3D origin = new Vector3D(0, 0, 0);
        Vector3D xAxisPositive = new Vector3D(600, 0, 0);  // Положительное направление оси X
        Vector3D xAxisNegative = new Vector3D(-600, 0, 0); // Отрицательное направление оси X
        Vector3D yAxisPositive = new Vector3D(0, 375, 0);  // Положительное направление оси Y
        Vector3D yAxisNegative = new Vector3D(0, -375, 0); // Отрицательное направление оси Y
        Vector3D zAxisPositive = new Vector3D(0, 0, 375);  // Положительное направление оси Z
        Vector3D zAxisNegative = new Vector3D(0, 0, -375); // Отрицательное направление оси Z

        // Проецируем точки на экран через камеру
        Vector3D screenOrigin = camera.project(origin);
        Vector3D screenXPos = camera.project(xAxisPositive);
        Vector3D screenXNeg = camera.project(xAxisNegative);
        Vector3D screenYPos = camera.project(yAxisPositive);
        Vector3D screenYNeg = camera.project(yAxisNegative);
        Vector3D screenZPos = camera.project(zAxisPositive);
        Vector3D screenZNeg = camera.project(zAxisNegative);

        // Центр координат в экранных координатах
        double screenX1 = centerX + screenOrigin.x;
        double screenY1 = centerY - screenOrigin.y;

        // Рисуем положительные оси (сплошные линии)
        gc.setLineDashes(); // Убираем пунктир
        gc.setStroke(Color.RED);
        gc.strokeLine(screenX1, screenY1, centerX + screenXPos.x, centerY - screenXPos.y); // Ось X+
        gc.setStroke(Color.GREEN);
        gc.strokeLine(screenX1, screenY1, centerX + screenYPos.x, centerY - screenYPos.y); // Ось Y+
        gc.setStroke(Color.BLUE);
        gc.strokeLine(screenX1, screenY1, centerX + screenZPos.x, centerY - screenZPos.y); // Ось Z+

        // Рисуем отрицательные оси (пунктирные линии)
        gc.setLineDashes(10, 10); // Устанавливаем пунктирный стиль: 10px линия, 10px пробел
        gc.setStroke(Color.RED);
        gc.strokeLine(screenX1, screenY1, centerX + screenXNeg.x, centerY - screenXNeg.y); // Ось X-
        gc.setStroke(Color.GREEN);
        gc.strokeLine(screenX1, screenY1, centerX + screenYNeg.x, centerY - screenYNeg.y); // Ось Y-
        gc.setStroke(Color.BLUE);
        gc.strokeLine(screenX1, screenY1, centerX + screenZNeg.x, centerY - screenZNeg.y); // Ось Z-

        // Убираем пунктирный стиль, чтобы не повлиять на дальнейшую отрисовку
        gc.setLineDashes();
    }
}
*/
