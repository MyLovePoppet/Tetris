package Model;

import javafx.scene.paint.Color;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;


public enum ConstantValues {
    main_square_vertical_num(),
    main_square_horizon_num(),
    next_square_vertical_num(),
    next_square_horizon_num(),
    square_length();
    public int value;

    static {
        String s="";
        try {
            s=Files.readString(Path.of(ConstantValues.class.getResource("../ConstantValues.xml").toURI()));
            } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        Document document = Jsoup.parse(s);
        Element valuesElement = document.getElementsByTag("Constant").first()
                .getElementsByTag("values").first();
        for (ConstantValues values : ConstantValues.values()) {
            values.value = Integer.parseInt(valuesElement.getElementsByTag(values.name()).first().text());
        }
    }

}

