package com.standingtimer.ste;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Main {
    private static String[] extensions = new String[] {".html", ".firebaserc", ".json"};

    public static void main(String[] args) throws IOException {
        Path output = Paths.get("result");
        Files.walk(Paths.get("configs"))
                .filter(file -> file.toFile().isFile())
                .forEach(path -> processConfig(path, output));
    }

    private static void processConfig(Path path, Path output) {
        try {
            final String name = path.getFileName().toString();
            final String nameNoExt = name.split("\\.")[0];
            final Path sub = output.resolve(nameNoExt);
            FileUtils.deleteDirectory(sub.toFile());
            final BufferedReader reader = Files.newBufferedReader(path);
            Map values = new Gson().fromJson(reader, Map.class);
            final Path templates = Paths.get("template");
            Files.walk(templates)
                    .filter(file -> file.toFile().isFile())
                    .forEach(template -> {
                        createFile(templates, template, sub, values);
                    });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createFile(Path templates, Path template, Path root, Map values) {
        try {
            final Path relative = templates.relativize(template);
            final Path file = root.resolve(relative);
            file.toFile().getParentFile().mkdirs();

            if (shouldBeProcessed(template)) {
                System.out.println(String.format("processing - %s", file.toAbsolutePath().toString()));

                file.toFile().createNewFile();
                MustacheFactory mf = new DefaultMustacheFactory();
                Mustache mustache = mf.compile(new FileReader(template.toFile()), template.getFileName().toString());
                final FileWriter writer = new FileWriter(file.toAbsolutePath().toFile());
                mustache.execute(writer, values);
                writer.flush();
                writer.close();
            } else {
                System.out.println(String.format("copying - %s", file.toAbsolutePath().toString()));
                Files.copy(template, file.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean shouldBeProcessed(Path template) {
        for (String extension : extensions) {
            if (template.toAbsolutePath().toString().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }
}
