package Client.ClientProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main3 {
    public static void main(String[] args) throws IOException {
        //Client client = new Client();
        Path dir = Paths.get("");
        try(Stream<Path> stream = Files.list(dir)) {
            stream.filter(Files::isDirectory).forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }

        File dir2 = new File(".");
        if(dir2.isDirectory()){
            File[] files = dir2.listFiles();
            if(files != null){
                for (File file :
                        files) {
                    if (file.isDirectory()) {
                        System.out.println(file.getAbsolutePath());
                    }
                    }
            }
        }
    }
}
