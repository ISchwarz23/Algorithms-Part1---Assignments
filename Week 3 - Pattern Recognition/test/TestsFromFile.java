import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Ingo on 23.09.2015.
 */
public class TestsFromFile {

    @Test
    public void input8_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("input8.txt"));
        assertEquals(2, cut.numberOfSegments());
    }

    @Test
    public void input8_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("input8.txt"));
        assertEquals(2, cut.numberOfSegments());
    }

    @Test
    @Ignore("Line segment bigger then 4 points")
    public void input9_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("input9.txt"));
        assertEquals(1, cut.numberOfSegments());
    }

    @Test
    public void input9_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("input9.txt"));
        assertEquals(1, cut.numberOfSegments());
    }

    @Test
    public void equidistant_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("equidistant.txt"));
        assertEquals(4, cut.numberOfSegments());
    }

    @Test
    public void equidistant_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("equidistant.txt"));
        assertEquals(4, cut.numberOfSegments());
    }

    @Test
    public void input40_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("input40.txt"));
        assertEquals(4, cut.numberOfSegments());
    }

    @Test
    public void input40_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("input40.txt"));
        assertEquals(4, cut.numberOfSegments());
    }

    @Test
    public void input48_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("input48.txt"));
        assertEquals(6, cut.numberOfSegments());
    }

    @Test
    public void input48_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("input48.txt"));
        assertEquals(6, cut.numberOfSegments());
    }

    @Test
    @Ignore("To slow")
    public void input299_brute() {
        BruteCollinearPoints cut = new BruteCollinearPoints(SampleClient.getPointsFromTestFile("input299.txt"));
        assertEquals(6, cut.numberOfSegments());
    }

    @Test
    public void input299_fast() {
        FastCollinearPoints cut = new FastCollinearPoints(SampleClient.getPointsFromTestFile("input299.txt"));
        assertEquals(6, cut.numberOfSegments());
    }

}
