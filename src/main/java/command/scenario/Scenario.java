package command.scenario;

import app.SSD;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class Scenario {
    private static final String filePath = "src/main/java/command/scenario/test/";

    private static ConcurrentHashMap<String, Boolean> scenarioMap;

    public Scenario() {
        scenarioMap = new ConcurrentHashMap<>();
    }

    public static void findScenario() {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".java")) {
                    String className = file.getName().replace(".java", "");
                    scenarioMap.put(className, true);
                }
            }
        }
    }

    public static boolean hasScenario(String name) {
        return scenarioMap.containsKey(name);
    }

    public static void deleteAllClassFile() {
        File dir = new File(filePath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    file.delete();
                }
            }
        }
    }

    public static void runScenario(String fileName, SSD ssd, ArrayList<String> commandOptionList) throws Exception {
        if (!scenarioMap.containsKey(fileName)) {
            return;
        }

        String javaFilePath = filePath + fileName + ".java";

        deleteAllClassFile();

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, javaFilePath);

        URL[] url = new URL[]{new File(javaFilePath).toURI().toURL()};
        URLClassLoader classLoader = URLClassLoader.newInstance(url);
        String className = new File(javaFilePath).getName().replace(".java", "");
        Class<?> loadedClass = classLoader.loadClass("command.scenario.test." + className);

        Object instance = loadedClass.getDeclaredConstructor().newInstance();

        Method method = loadedClass.getMethod("process", SSD.class, ArrayList.class);
        method.invoke(instance, ssd, commandOptionList);

        deleteAllClassFile();
    }
}
