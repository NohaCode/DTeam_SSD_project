package command.scenario;

import app.SSD;
import exception.ShellException;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Scenario {
    private static final String filePath = "src/main/java/command/scenario/test/";
    private static ConcurrentHashMap<String, Boolean> scenarioMap;

    public Scenario() {
        scenarioMap = new ConcurrentHashMap<>();
    }

    public static void findScenario()  {
        try {
            scenarioMap.clear();
            Files.walk(Paths.get(filePath)).forEach(path -> {
                if (Files.isRegularFile(path) && path.toString().endsWith(".java")) {
                    String className = new File(path.toString()).getName().replace(".java", "");
                    scenarioMap.put(className, true);
                }
            });
        } catch (Exception e) {
            throw new ShellException();
        }
    }

    public static boolean hasScenario(String name) {
        return scenarioMap.containsKey(name);
    }

    public static void runScenario(String fileName, SSD ssd, ArrayList<String> commandOptionList) throws Exception {

        if (!scenarioMap.containsKey(fileName)) { return;}

        String javaFilePath = filePath + fileName + ".java";

        Files.walk(Paths.get(filePath)).forEach(path -> {
            if (Files.isRegularFile(path) && path.toString().endsWith(".class")) {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Deleted: " + path);
            }
        });

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        compiler.run(null, null, null, javaFilePath);

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{new File(javaFilePath).toURI().toURL()});

        String className = new File(javaFilePath).getName().replace(".java", "");

        Class<?> loadedClass = classLoader.loadClass("command.scenario.test." + className);

        Object instance = loadedClass.getDeclaredConstructor().newInstance();

        Method method = loadedClass.getDeclaredMethod("isValidCommandImpl", ArrayList.class);
        method.invoke(instance, commandOptionList);

        method = loadedClass.getDeclaredMethod("runImpl", SSD.class, ArrayList.class);
        method.invoke(instance, ssd, commandOptionList);

    }
}
