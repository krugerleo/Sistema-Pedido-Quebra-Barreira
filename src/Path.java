public class Path {
    private static Path instance;
    private final String path;

    private Path(String path){
        this.path = path;
    }
    public static Path getInstance(String value){
        if (instance == null)
            instance = new Path(value);
        return instance;
    }

    public static Path getInstance() {
        return instance;
    }

    public String getPath() {
        return path;
    }
}
