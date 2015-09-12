package de.ingo.percolation;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


/**
 * Tests for the {@link Percolation} class.
 * @author ISchwarz
 */
public class PercolationTest {

    private static final int GRID_SIZE = 5;
    private Percolation percolation;


    @Before
    public void setUp() {
        percolation = new Percolation(GRID_SIZE);
    }

    @Test
    public void shouldOpenAGrid() {
        // given
        assertFalse(percolation.isOpen(1, 1));

        // when
        percolation.open(1, 1);

        // then
        assertTrue(percolation.isOpen(1, 1));
    }

    @Test
    public void shouldPercolate() {
        // when
        percolation.open(1, 2);
        percolation.open(2, 2);
        percolation.open(3, 2);
        percolation.open(4, 2);
        percolation.open(5, 2);

        // then
        assertTrue(percolation.percolates());
    }

    @Test
    public void shouldNotPercolate() {
        // when
        percolation.open(1, 1);
        percolation.open(1, 2);
        percolation.open(1, 4);
        percolation.open(1, 5);

        // then
        assertFalse(percolation.percolates());
    }

    @Test
    public void shouldBeFull() {
        // when
        percolation.open(1, 1);
        percolation.open(1, 2);

        // then
        assertTrue(percolation.isFull(1, 2));
    }

    @Test
    public void shouldNotBeFull() {
        // when
        percolation.open(3, 2);

        // then
        assertFalse(percolation.isFull(3, 2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfGridSizeIsZero() {
        new Percolation(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentExceptionIfGridSizeIsNegative() {
        new Percolation(-5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfOpenIsCalledWithZero() {
        percolation.open(0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfOpenIsCalledWithFieldOutsideTheGrid() {
        percolation.open(GRID_SIZE+1, GRID_SIZE+1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsOpenIsCalledWithZero() {
        percolation.isOpen(0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsOpenIsCalledWithFieldOutsideTheGrid() {
        percolation.isOpen(GRID_SIZE + 1, GRID_SIZE + 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsFullIsCalledWithZero() {
        percolation.isFull(0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void shouldThrowIndexOutOfBoundsExceptionIfIsFullIsCalledWithFieldOutsideTheGrid() {
        percolation.isFull(GRID_SIZE + 1, GRID_SIZE + 1);
    }

}
