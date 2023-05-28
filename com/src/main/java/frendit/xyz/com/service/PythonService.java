package frendit.xyz.com.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class PythonService {

    public String analyzePost(String post) throws IOException, InterruptedException {
        String fetching = "python " + "scripts\\run.py \"";
        String[] commandToExecute = new String[]{"cmd.exe", "/c", fetching};
        ProcessBuilder pb = new ProcessBuilder(commandToExecute);
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            return output.toString();
        } else {
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorMessage = new StringBuilder();
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorMessage.append(errorLine).append("\n");
            }
            throw new RuntimeException("Python script failed with exit code " + exitCode + ":\n" + errorMessage.toString());
        }
    }
}

