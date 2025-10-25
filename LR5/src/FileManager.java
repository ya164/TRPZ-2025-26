import java.util.HashMap;
import java.util.Map;

public class FileManager {

    private Map<String, FileSystemObject> templates = new HashMap<>();

    public void registerTemplate(String key, FileSystemObject prototype) {
        templates.put(key, prototype);
    }

    public FileSystemObject createFromTemplate(String key, String newPath, String newName)
            throws CloneNotSupportedException {

        FileSystemObject prototype = templates.get(key);
        if (prototype == null) {
            throw new IllegalArgumentException("Template not found: " + key);
        }

        FileSystemObject clone = prototype.clone();
        clone.setName(newName);
        clone.setPath(newPath);
        return clone;
    }
}