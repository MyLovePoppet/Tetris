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
    Type_O(Color.RED),
    Type_I(Color.GREEN),
    Type_Z(Color.YELLOW),
    Type_L(Color.BLUE),
    Type_J(Color.ORANGE),
    Type_T(Color.PURPLE);

    final public Color color;

    TetrisColor(Color color) {
        this.color = color;
    }
}
