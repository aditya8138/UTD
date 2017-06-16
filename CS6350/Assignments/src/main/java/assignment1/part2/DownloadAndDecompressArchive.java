package assignment1.part2;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.util.Progressable;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by hanlin on 6/10/17.
 */
public class DownloadAndDecompressArchive {
    public static void main(String[] args) {
        String pdst = args[1];

        InputStream in = null;
        ZipInputStream zin = null;

        try {
            Configuration conf = new Configuration();

            URL url = new URL(args[0]);

//            URLConnection conn = url.openConnection();
//            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
//            in = conn.getInputStream();
            in = new BufferedInputStream(url.openStream());

            zin = new ZipInputStream(in);

            ZipEntry entry;
            while((entry = zin.getNextEntry()) != null) {

                String dst = pdst + entry.getName();

                FileSystem fs = FileSystem.get(URI.create(dst), conf);

                Path dstPath = new Path(dst);
                OutputStream out = fs.create(dstPath, new Progressable() {
                    public void progress() {
                        System.out.print(".");
                    }
                });

                IOUtils.copyBytes(zin, out, 4096, false);

                IOUtils.closeStream(out);

                fs.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeStream(zin);
            IOUtils.closeStream(in);
        }
    }
}
