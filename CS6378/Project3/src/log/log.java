package log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class log implements Runnable {

    private char label;
    private List<String> lines;

    public log(char label, EventType eventType, LocalDateTime localDateTime, String votedata) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String ts = localDateTime.format(formatter);
        this.label = label;

        this.lines = new ArrayList<>();

        switch (eventType) {
            case WRITE_SUCCESS:
                this.lines.add("" + ts + ": Write operation success.");
                break;
            case WRITE_REQUEST_SUCCESS:
                this.lines.add("" + ts + ": Write request success.");
                break;
            case WRITE_FAIL_WITH_NACK:
                this.lines.add("" + ts + ": Write operation failed, since some other site is updating.");
                break;
            case WRITE_FAIL_NO_DISTINGUISH:
                this.lines.add("" + ts + ": Write operation failed, since node not in distinguished partition.");
                break;
            case WRITE_ABORT:
                this.lines.add("" + ts + ": Write operation aborted by coordinate node.");
                break;
        }

        lines.add("\t\t     " + votedata);
    }

    public void run() {
        try {
            Files.write(Paths.get(label + ".log"), this.lines,
                    StandardOpenOption.APPEND, StandardOpenOption.CREATE);
        } catch (IOException e) {
            System.err.println("\nError when add event to log file: " + e.getMessage());
        }
    }
}
