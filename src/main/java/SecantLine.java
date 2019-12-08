import java.util.ArrayList;

public class SecantLine {
    private Function function;

    public SecantLine(Function function) {
        this.function = function;
    }

    public ArrayList<double[]> solver(double xl, double xu, double ea, double e, double M, double xn, double x_n1) {

        ArrayList<double[]> solver = new ArrayList<>();

        double[] solution = new double[4];
        solution[0] = xl;
        solution[1] = xu;
        solution[2] = calculateXr(xn, x_n1, e, M);
        solution[3] = 100;
        solver.add(solution);
        while (solver.get(solver.size() - 1)[3] > ea) {
            solution = calculateBoundaries(solution, e, M);
            solution[2] = calculateXr(solution[2], solver.get(solver.size() - 1)[2], e, M);
            solution[3] = calculateEa(solution[2], solver.get(solver.size() - 1)[2]);
            solver.add(solution);
        }
        return solver;

    }

    private double calculateXr(double xn, double x_n1, double e, double M) {
        double xR = 0;
        xR = xn - (function.function(e, xn, M) * (x_n1 - xn)) / (function.function(e, x_n1, M) - function.function(e, xn, M));
        return xR;
    }

    private double[] calculateBoundaries(double[] solve, double e, double M) {

        if (function.function(e, solve[0], M) * function.function(e, solve[2], M) < 0) {
            double boundaries[] = new double[solve.length];
            boundaries[0] = solve[0];
            boundaries[1] = solve[2];
            return boundaries;
        } else {
            double boundaries[] = new double[solve.length];
            boundaries[0] = solve[2];
            boundaries[1] = solve[1];
            return boundaries;
        }
    }

    private double calculateEa(double prsntApprx, double prvsApprx) {
        return Math.abs(((prsntApprx - prvsApprx) / prsntApprx) * 100);
    }

}