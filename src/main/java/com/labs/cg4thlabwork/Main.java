package com.labs.cg4thlabwork;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    private Model3D cube;
    private Camera3D camera;
    private Render3D renderer;
    private AffineTransform transform;
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        // Инициализация модели, камеры и аффинного преобразования
        cube = ModelLoader.createCube(200); // Куб с размером 200
        camera = new Camera3D(new Vector3D(0, 0, -500), 500); // Камера
        transform = new AffineTransform();

        // Создаем Canvas для рисования
        canvas = new Canvas(800, 600);
        renderer = new Render3D(canvas.getGraphicsContext2D(), camera);

        // Кнопки интерфейса для управления преобразованиями
        HBox controlPanel = createControlPanel();

        // Обновляем сцену
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setBottom(controlPanel);

        // Настройка окна
        primaryStage.setTitle("3D Wireframe Renderer with Mouse Controls");
        primaryStage.setScene(new Scene(root, 800, 650));
        primaryStage.show();

        // Первая отрисовка
        render();
    }

    private HBox createControlPanel() {
        HBox controlPanel = new HBox(13);

        // Кнопки для перемещения
        Button translateX = createButton("Translate X", () -> transform.translate(50, 0, 0), () -> transform.translate(-50, 0, 0));
        Button translateY = createButton("Translate Y", () -> transform.translate(0, 50, 0), () -> transform.translate(0, -50, 0));
        Button translateZ = createButton("Translate Z", () -> transform.translate(0, 0, 50), () -> transform.translate(0, 0, -50));

        // Кнопки для масштабирования
        Button scale = createButton("Scale", () -> transform.scale(1.2, 1.2, 1.2), () -> transform.scale(0.8, 0.8, 0.8));

        // Кнопки для вращения
        Button rotateX = createButton("Rotate X", () -> transform.rotateX(Math.toRadians(15)), () -> transform.rotateX(-Math.toRadians(15)));
        Button rotateY = createButton("Rotate Y", () -> transform.rotateY(Math.toRadians(15)), () -> transform.rotateY(-Math.toRadians(15)));
        Button rotateZ = createButton("Rotate Z", () -> transform.rotateZ(Math.toRadians(15)), () -> transform.rotateZ(-Math.toRadians(15)));

        // Кнопки отражения
        Button mapOxy = createButton("MapOxy", () -> transform.mapOxy(), () -> transform.mapOxy());
        Button mapOyz = createButton("MapOyz", () -> transform.mapOyz(), () -> transform.mapOyz());
        Button mapOxz = createButton("MapOxz", () -> transform.mapOxz(), () -> transform.mapOxz());

        // Добавляем кнопки в панель
        controlPanel.getChildren().addAll(
                translateX, translateY, translateZ,
                scale,
                rotateX, rotateY, rotateZ,
                mapOxy, mapOyz, mapOxz
        );

        return controlPanel;
    }

    private Button createButton(String label, Runnable leftClickAction, Runnable rightClickAction) {
        Button button = new Button(label);

        // Обработчик событий для кнопок мыши
        button.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                // Левая кнопка мыши
                leftClickAction.run();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                // Правая кнопка мыши
                rightClickAction.run();
            }
            render();
        });

        return button;
    }

    private void render() {
        // Применяем преобразование к модели
        Model3D transformedCube = ModelLoader.createCube(200); // Создаем новый куб
        transformedCube.applyTransform(transform);

        // Очищаем экран
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Рисуем координатные оси
        drawAxes(gc);

        // Рендерим преобразованную модель
        renderer.render(transformedCube);
    }

    private void drawAxes(GraphicsContext gc) {
        // Центр экрана
        double centerX = canvas.getWidth() / 2;
        double centerY = canvas.getHeight() / 2;

        // Оси X, Y и Z
        Vector3D origin = new Vector3D(0, 0, 0);
        Vector3D xAxis = new Vector3D(600, 0, 0); // Красная ось X
        Vector3D yAxis = new Vector3D(0, 300, 0); // Зеленая ось Y
        Vector3D zAxis = new Vector3D(0, 0, 300); // Синяя ось Z

        // Проецируем точки на экран через камеру
        Vector3D screenOrigin = camera.project(origin);
        Vector3D screenX = camera.project(xAxis);
        Vector3D screenY = camera.project(yAxis);
        Vector3D screenZ = camera.project(zAxis);

        // Перемещение координат в экранные координаты
        double screenX1 = centerX + screenOrigin.x;
        double screenY1 = centerY - screenOrigin.y;

        // Ось X (красная)
        gc.setStroke(Color.RED);
        gc.strokeLine(screenX1, screenY1, centerX + screenX.x, centerY - screenX.y);

        // Ось Y (зеленая)
        gc.setStroke(Color.GREEN);
        gc.strokeLine(screenX1, screenY1, centerX + screenY.x, centerY - screenY.y);

        // Ось Z (синяя)
        gc.setStroke(Color.BLUE);
        gc.strokeLine(screenX1, screenY1, centerX + screenZ.x, centerY - screenZ.y);
    }


    /*private void render() {
        // Применяем преобразование к модели
        Model3D transformedCube = ModelLoader.createCube(200); // Создаем новый куб
        transformedCube.applyTransform(transform);

        // Очищаем экран
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Рендерим преобразованную модель
        renderer.render(transformedCube);
    }*/

    public static void main(String[] args) {
        launch(args);
    }
}