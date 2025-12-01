import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("=".repeat(60));
            System.out.println("GRAM-SCHMIDT ORTHONORMALIZATION PROCESS");
            System.out.println("=".repeat(60));
            System.out.println();

            // Get dimension
            int dimension = getDimension(scanner);
            System.out.println();

            // Get basis vectors
            List<Vector> originalBasis = getBasisVectors(scanner, dimension);
            System.out.println();

            // Validate linear independence
            if (!GramSchmidt.areLinearlyIndependent(originalBasis)) {
                System.out.println("ERROR: The vectors are linearly dependent!");
                System.out.println("Please provide linearly independent vectors.");
                return;
            }

            // Display original basis
            System.out.println("-".repeat(60));
            System.out.println("ORIGINAL BASIS:");
            System.out.println("-".repeat(60));
            printBasis(originalBasis, "v");
            System.out.println();

            // Perform orthogonalization
            List<Vector> orthogonalBasis = GramSchmidt.orthogonalize(originalBasis);
            System.out.println("-".repeat(60));
            System.out.println("ORTHOGONAL BASIS:");
            System.out.println("-".repeat(60));
            printBasis(orthogonalBasis, "w");
            System.out.println("Verification: Is orthogonal? " + 
                             (GramSchmidt.isOrthogonal(orthogonalBasis) ? "YES ✓" : "NO ✗"));
            System.out.println();

            // Perform orthonormalization
            List<Vector> orthonormalBasis = GramSchmidt.orthonormalize(originalBasis);
            System.out.println("-".repeat(60));
            System.out.println("ORTHONORMAL BASIS:");
            System.out.println("-".repeat(60));
            printBasis(orthonormalBasis, "u");
            System.out.println("Verification: Is orthonormal? " + 
                             (GramSchmidt.isOrthonormal(orthonormalBasis) ? "YES ✓" : "NO ✗"));
            System.out.println();

            System.out.println("=".repeat(60));
            System.out.println("Process completed successfully!");
            System.out.println("=".repeat(60));
        }
    }

    private static int getDimension(Scanner scanner) {
        int dimension;
        while (true) {
            System.out.print("Enter the dimension of the space (2-10): ");
            if (scanner.hasNextInt()) {
                dimension = scanner.nextInt();
                if (dimension >= 2 && dimension <= 10) {
                    break;
                } else {
                    System.out.println("Please enter a number between 2 and 10.");
                }
            } else {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
        }
        return dimension;
    }

    private static List<Vector> getBasisVectors(Scanner scanner, int dimension) {
        List<Vector> basis = new ArrayList<>();
        
        System.out.println("Enter " + dimension + " vectors for R^" + dimension + ":");
        System.out.println();

        for (int i = 0; i < dimension; i++) {
            System.out.println("Vector " + (i + 1) + ":");
            double[] components = new double[dimension];
            
            for (int j = 0; j < dimension; j++) {
                while (true) {
                    System.out.print("  Component " + (j + 1) + ": ");
                    if (scanner.hasNextDouble()) {
                        components[j] = scanner.nextDouble();
                        break;
                    } else {
                        System.out.println("  Invalid input. Please enter a number.");
                        scanner.next();
                    }
                }
            }
            
            basis.add(new Vector(components));
            System.out.println();
        }

        return basis;
    }

    private static void printBasis(List<Vector> basis, String prefix) {
        for (int i = 0; i < basis.size(); i++) {
            System.out.println(prefix + (i + 1) + " = " + basis.get(i));
        }
    }
}

// Vector class
class Vector {
    private final double[] components;
    private final int dimension;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.components = new double[dimension];
    }

    public Vector(double[] components) {
        this.dimension = components.length;
        this.components = components.clone();
    }

    public double get(int index) {
        return components[index];
    }

    public void set(int index, double value) {
        components[index] = value;
    }

    public int getDimension() {
        return dimension;
    }

    public double[] getComponents() {
        return components.clone();
    }

    // Dot product (inner product)
    public double dot(Vector other) {
        if (this.dimension != other.dimension) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }

        double result = 0;
        for (int i = 0; i < dimension; i++) {
            result += this.components[i] * other.components[i];
        }
        return result;
    }

    // Vector norm (magnitude)
    public double norm() {
        return Math.sqrt(this.dot(this));
    }

    // Scalar multiplication
    public Vector multiply(double scalar) {
        Vector result = new Vector(dimension);
        for (int i = 0; i < dimension; i++) {
            result.components[i] = this.components[i] * scalar;
        }
        return result;
    }

    // Vector subtraction
    public Vector subtract(Vector other) {
        if (this.dimension != other.dimension) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }

        Vector result = new Vector(dimension);
        for (int i = 0; i < dimension; i++) {
            result.components[i] = this.components[i] - other.components[i];
        }
        return result;
    }

    // Projection of this vector onto another vector
    public Vector projectOnto(Vector other) {
        double scalar = this.dot(other) / other.dot(other);
        return other.multiply(scalar);
    }

    // Normalize the vector
    public Vector normalize() {
        double magnitude = this.norm();
        if (magnitude == 0) {
            throw new ArithmeticException("Cannot normalize zero vector");
        }
        return this.multiply(1.0 / magnitude);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < dimension; i++) {
            sb.append(String.format("%.4f", components[i]));
            if (i < dimension - 1) {
                sb.append(", ");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}

// Gram-Schmidt class
class GramSchmidt {

    // Gram-Schmidt orthogonalization process
    public static List<Vector> orthogonalize(List<Vector> basis) {
        if (basis.isEmpty()) {
            throw new IllegalArgumentException("Basis cannot be empty");
        }

        List<Vector> orthogonalBasis = new ArrayList<>();

        // First vector remains the same
        orthogonalBasis.add(basis.get(0));

        // Process remaining vectors
        for (int i = 1; i < basis.size(); i++) {
            Vector currentVector = basis.get(i);
            
            // Subtract all projections onto previous orthogonal vectors
            for (int j = 0; j < i; j++) {
                Vector projection = currentVector.projectOnto(orthogonalBasis.get(j));
                currentVector = currentVector.subtract(projection);
            }
            
            orthogonalBasis.add(currentVector);
        }

        return orthogonalBasis;
    }

    // Normalize orthogonal basis to get orthonormal basis
    public static List<Vector> orthonormalize(List<Vector> basis) {
        List<Vector> orthogonalBasis = orthogonalize(basis);
        List<Vector> orthonormalBasis = new ArrayList<>();

        for (Vector vector : orthogonalBasis) {
            orthonormalBasis.add(vector.normalize());
        }

        return orthonormalBasis;
    }

    // Check if vectors are linearly independent
    public static boolean areLinearlyIndependent(List<Vector> vectors) {
        List<Vector> orthogonalBasis = orthogonalize(vectors);
        
        // If any vector becomes zero during orthogonalization, they're dependent
        for (Vector vector : orthogonalBasis) {
            if (vector.norm() < 1e-10) {
                return false;
            }
        }
        return true;
    }

    // Verify orthogonality of basis
    public static boolean isOrthogonal(List<Vector> basis) {
        for (int i = 0; i < basis.size(); i++) {
            for (int j = i + 1; j < basis.size(); j++) {
                if (Math.abs(basis.get(i).dot(basis.get(j))) > 1e-10) {
                    return false;
                }
            }
        }
        return true;
    }

    // Verify orthonormality of basis
    public static boolean isOrthonormal(List<Vector> basis) {
        if (!isOrthogonal(basis)) {
            return false;
        }

        for (Vector vector : basis) {
            if (Math.abs(vector.norm() - 1.0) > 1e-10) {
                return false;
            }
        }
        return true;
    }
}