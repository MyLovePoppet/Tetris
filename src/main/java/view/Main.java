package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();    // 创建对象

        loader.setBuilderFactory(new JavaFXBuilderFactory());    // 设置BuilderFactory
        loader.setLocation(Main.class.getResource("Tetris.fxml"));    // 设置路径基准
        InputStream in = Main.class.getResourceAsStream("Tetris.fxml");
        Scene scene=loader.load(in); // 对象方法的参数是InputStream，返回值是Object
        primaryStage.setScene(scene);
        primaryStage.setTitle("舒钦瑜_2017152044");
        Image icon=new Image(this.getClass().getResource("lol.png").toString());
        primaryStage.getIcons().add(icon);
        primaryStage.show();
        in.close();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
