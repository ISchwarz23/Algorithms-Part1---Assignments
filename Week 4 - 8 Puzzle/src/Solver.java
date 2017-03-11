import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


/**
 * Class that is able to find the fastest solution for a given {@link Board}.
 *
 * @author ISchwarz
 */
public class Solver {

    private List<Board> solutionBoards = new ArrayList<>();
    private boolean solved;


    public Solver(Board initial) {          // find a solution to the initial board (using the A* algorithm)
        MinPQ<SolverStep> priorizedSteps = new MinPQ<>(new SolverStepComparator());
        priorizedSteps.insert(new SolverStep(initial, 0, null));

        MinPQ<SolverStep> priorizedStepsTwin = new MinPQ<>(new SolverStepComparator());
        priorizedStepsTwin.insert(new SolverStep(initial.twin(), 0, null));

        SolverStep step;
        while (!priorizedSteps.min().getBoard().isGoal() && !priorizedStepsTwin.min().getBoard().isGoal()) {
            step = priorizedSteps.delMin();
            for (Board neighbor : step.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(step, neighbor)) {
                    priorizedSteps.insert(new SolverStep(neighbor, step.getMoves() + 1, step));
                }
            }

            SolverStep stepTwin = priorizedStepsTwin.delMin();
            for (Board neighbor : stepTwin.getBoard().neighbors()) {
                if (!isAlreadyInSolutionPath(stepTwin, neighbor)) {
                    priorizedStepsTwin.insert(new SolverStep(neighbor, stepTwin.getMoves() + 1, stepTwin));
                }
            }
        }
        step = priorizedSteps.delMin();
        solved = step.getBoard().isGoal();

        solutionBoards.add(step.getBoard());
        while ((step = step.getPreviousStep()) != null) {
            solutionBoards.add(0, step.getBoard());
        }
    }

    private boolean isAlreadyInSolutionPath(SolverStep lastStep, Board board) {
        SolverStep previousStep = lastStep;
        while ((previousStep = previousStep.getPreviousStep()) != null) {
            if (previousStep.getBoard().equals(board)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSolvable() {           // is the initial board solvable?
        return solved;
    }

    public int moves() {                    // min number of moves to solve initial board; -1 if unsolvable
        int moves;
        if (isSolvable()) {
            moves = solutionBoards.size() - 1;
        } else {
            moves = -1;
        }
        return moves;
    }

    public Iterable<Board> solution() {     // sequence of boards in a shortest solution; null if unsolvable
        Iterable<Board> iterable;
        if (isSolvable()) {
            iterable = new Iterable<Board>() {
                @Override
                public Iterator<Board> iterator() {
                    return new SolutionIterator();
                }
            };
        } else {
            iterable = null;
        }
        return iterable;
    }


    private static class SolverStep {

        private int moves;
        private Board board;
        private SolverStep previousStep;

        private SolverStep(Board board, int moves, SolverStep previousStep) {
            this.board = board;
            this.moves = moves;
            this.previousStep = previousStep;
        }

        public int getMoves() {
            return moves;
        }

        public int getPriority() {
            return board.manhattan() + moves;
        }

        public Board getBoard() {
            return board;
        }

        public SolverStep getPreviousStep() {
            return previousStep;
        }
    }

    private class SolutionIterator implements Iterator<Board> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < solutionBoards.size();
        }

        @Override
        public Board next() {
            return solutionBoards.get(index++);
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("It is not supported to remove a board from the solution.");
        }
    }

    private static class SolverStepComparator implements Comparator<SolverStep> {

        @Override
        public int compare(SolverStep step1, SolverStep step2) {
            return step1.getPriority() - step2.getPriority();
        }
    }

}
