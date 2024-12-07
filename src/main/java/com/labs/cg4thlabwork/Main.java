package com.labs.cg4thlabwork;

import com.labs.cg4thlabwork.core.*;
import com.labs.cg4thlabwork.render.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private Model3D currentModel;
    private Camera3D camera;
    private Render3D renderer;
    private AffineTransform transform;
    private Canvas canvas;
    private int modelType;
    private int prevModelType;

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Инициализация камеры и преобразования
        camera = new Camera3D(new Vector3D(0, 0, -500), 500); // Камера
        transform = new AffineTransform();

        // Создаем Canvas для рисования
        canvas = new Canvas(1200, 750);
        renderer = new Render3D(canvas.getGraphicsContext2D(), camera);

        // Выбор модели
        ComboBox<String> modelSelector = new ComboBox<>();

        // Задаём количество загружаемых моделей
        int amountOfModels = 6;
        String[] allModels = new String[amountOfModels];
        for (int i = 0; i < amountOfModels; i++) {
            allModels[i] = "Model " + (i+1);
        }
        modelSelector.getItems().addAll(allModels);
        modelSelector.setValue(allModels[0]); // По умолчанию куб


        modelSelector.setOnAction(event -> {
            modelType = Integer.parseInt(String.valueOf((modelSelector.getValue().charAt(modelSelector.getValue().length() - 1))));
            try {
                currentModel = ModelLoader.loadCubeFromFile("./models/model" + modelType + ".txt");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


            try {
                render();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Панель управления
        HBox controlPanel = createControlPanel();

        // Добавляем элементы в интерфейс
        BorderPane root = new BorderPane();
        root.setTop(modelSelector);
        root.setCenter(canvas);
        root.setBottom(controlPanel);

        // Настройка окна
        primaryStage.setTitle("3D Wireframe Renderer");
        primaryStage.setScene(new Scene(root, 1250, 900));
        primaryStage.show();

        // Устанавливаем начальную модель (куб)
        modelType = 1;
        prevModelType = 1;
        currentModel = ModelLoader.loadCubeFromFile("./models/model1.txt");
        render();
    }

    private HBox createControlPanel() {
        HBox controlPanel = new HBox(20); // Основная панель управления

        // Секция Translate
        VBox translateSection = new VBox(10);
        translateSection.getChildren().addAll(
                new Label("  Translating"),
                createButton("Translate X", () -> transform.translate(50, 0, 0), () -> transform.translate(-50, 0, 0)),
                createButton("Translate Y", () -> transform.translate(0, 50, 0), () -> transform.translate(0, -50, 0)),
                createButton("Translate Z", () -> transform.translate(0, 0, 50), () -> transform.translate(0, 0, -50))
        );

        // Секция Scaling
        VBox scalingSection = new VBox(10);
        scalingSection.getChildren().addAll(
                new Label("Scaling"),
                createButton("Scale", () -> transform.scale(1.2, 1.2, 1.2), () -> transform.scale(0.8, 0.8, 0.8))
        );

        // Секция Rotating
        VBox rotatingSection = new VBox(10);
        rotatingSection.getChildren().addAll(
                new Label("Rotating"),
                createButton("Rotate X", () -> transform.rotateX(Math.toRadians(15)), () -> transform.rotateX(-Math.toRadians(15))),
                createButton("Rotate Y", () -> transform.rotateY(Math.toRadians(15)), () -> transform.rotateY(-Math.toRadians(15))),
                createButton("Rotate Z", () -> transform.rotateZ(Math.toRadians(15)), () -> transform.rotateZ(-Math.toRadians(15)))
        );

        // Секция Mapping
        VBox mappingSection = new VBox(10);
        mappingSection.getChildren().addAll(
                new Label("Mapping"),
                createButton("Map Oxy", () -> transform.mapOxy(), () -> transform.mapOxy()),
                createButton("Map Oyz", () -> transform.mapOyz(), () -> transform.mapOyz()),
                createButton("Map Oxz", () -> transform.mapOxz(), () -> transform.mapOxz())
        );

        // Добавляем секции в основную панель
        controlPanel.getChildren().addAll(translateSection, scalingSection, rotatingSection, mappingSection);

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
            try {
                render();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        return button;
    }

    private void render() throws IOException {
        // Применяем преобразование к модели


        currentModel = ModelLoader.loadCubeFromFile("./models/model" + modelType + ".txt");

        currentModel.applyTransform(transform);

        if (modelType != prevModelType) {
            transform = new AffineTransform();

            prevModelType = modelType;
            currentModel = ModelLoader.loadCubeFromFile("./models/model" + modelType + ".txt");

        }

        // Очищаем экран
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Рисуем координатные оси
        DrawAxes.drawAxes(gc, camera, canvas);

        // Рендерим преобразованную модель
        renderer.render(currentModel);
    }


    public static void main(String[] args) {
        launch(args);
    }
}