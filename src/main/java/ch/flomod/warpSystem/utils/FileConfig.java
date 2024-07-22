package ch.flomod.warpSystem.utils;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileConfig extends YamlConfiguration {

    private String path;

    public FileConfig(String folder, String filename) {
        this.path = "plugins/" + folder + "/" + filename;

        File file = new File(this.path);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs(); // Erstellt die Verzeichnisse, falls sie nicht existieren
                file.createNewFile(); // Erstellt die Datei, falls sie nicht existiert
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            load(this.path);
        } catch (InvalidConfigurationException | IOException ex) {
            ex.printStackTrace();
        }

    }

    public FileConfig(String filename) {
        this("WarpSystem", filename);
    }

    public void saveConfig() {
        try {
            save(this.path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
