package nl.ing.awesome;

import nl.ing.awesome.domain.Vehicle;
import org.junit.Test;

import java.util.ArrayList;

/**
 * Created on 1-3-18.
 */
public class FileReaderWriterTest {

    @Test
    public void readFile() throws Exception {
        FileReaderWriter.readFile("/tmp/hc/a_example.in");
    }

    @Test
    public void writeFile() throws Exception {
        FileReaderWriter.writeFile("/tmp/hc/a_example.out", new ArrayList<>());
    }

}