package cs6350.assignment1.part1;

import org.apache.commons.io.FilenameUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.CompressionCodec;
import org.apache.hadoop.io.compress.CompressionCodecFactory;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by hanlin on 6/9/17.
 */
public class DownloadAndDecompress {
    public static void main(String[] args) {
        String source = args[0], dst = args[1];
        if (Files.notExists(Paths.get(source)))
            return;
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get(source), StandardCharsets.UTF_8);
            if (lines.size() == 0)
                return;
            for (String line : lines) {
                ddd(line, dst);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ddd (String uri, String dst) {
        URL url = null;
        InputStream in = null;
        OutputStream out = null;
        try {
            url = new URL(uri);
            dst = dst + "/" + FilenameUtils.getName(url.getPath());

//            URLConnection conn = url.openConnection();
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            in = conn.getInputStream();
            in = new BufferedInputStream(url.openStream());

            Configuration conf = new Configuration();

            FileSystem fs = FileSystem.get(URI.create(dst), conf);

            Path dstPath = new Path(dst);
            out = fs.create(dstPath, new Progressable() {
                public void progress() {
                    System.out.print(".");
                }
            });

            IOUtils.copyBytes(in, out, 4096, true);

            CompressionCodecFactory factory = new CompressionCodecFactory(conf);
            CompressionCodec codec = factory.getCodec(dstPath);
            if (codec == null) {
                System.err.println("No codec found for " + uri);
                System.exit(1);
            }

            String outputUri =
                    CompressionCodecFactory.removeSuffix(dst, codec.getDefaultExtension());

            in = codec.createInputStream(fs.open(dstPath));
            out = fs.create(new Path(outputUri));
            IOUtils.copyBytes(in, out, conf);
            fs.delete(dstPath, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(in);
            IOUtils.closeStream(out);
        }

    }
}
