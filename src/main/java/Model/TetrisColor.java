package Model;

import javafx.scene.paint.Color;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public enum TetrisColor {
    Type_O(),
    Type_I(),
    Type_Z(),
    Type_L(),
    Type_J(),
    Type_T();

    public Color color;

    static {
        String s="";
        try {
            s= Files.readString(Path.of(ConstantValues.class.getResource("../ConstantValues.xml").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(s);
        Element valuesElement = document.getElementsByTag("Constant").first()
                .getElementsByTag("colors").first();
        for (TetrisColor color1 : TetrisColor.values()) {
            color1.color = Color.valueOf(valuesElement.getElementsByTag(color1.name()).first().text());
        }
    }
}
