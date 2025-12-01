# Gram-Schmidt Orthonormalization

A Java implementation of the Gram-Schmidt process for orthonormalizing a basis of vectors in R^n.

## Overview

The Gram-Schmidt process is a method for orthonormalizing a set of vectors in an inner product space. This implementation provides a clean, object-oriented approach to:

- Convert a linearly independent set of vectors into an orthogonal basis
- Normalize the orthogonal basis to create an orthonormal basis
- Validate linear independence of input vectors
- Verify orthogonality and orthonormality of resulting bases

## Features

- **Generic implementation**: Works for any dimension from R² to R¹⁰
- **Vector class**: Clean abstraction for vector operations
- **Input validation**: Checks for linear independence before processing
- **Verification**: Confirms orthogonality and orthonormality of results
- **User-friendly interface**: Clear prompts and formatted output
- **Error handling**: Validates all user inputs
- **Single file**: All code contained in `Main.java` for easy distribution

## Mathematical Background

### Gram-Schmidt Process

Given a basis {v₁, v₂, ..., vₙ}, the process creates an orthogonal basis {w₁, w₂, ..., wₙ}:

1. w₁ = v₁
2. w₂ = v₂ - proj\_{w₁}(v₂)
3. w₃ = v₃ - proj*{w₁}(v₃) - proj*{w₂}(v₃)
4. Continue for all vectors

To create an orthonormal basis {u₁, u₂, ..., uₙ}:

- uᵢ = wᵢ / ||wᵢ||

## File Structure

```
Main.java    # Single file containing all classes
```

The file contains three classes:

- `Main` (public): Main program with user interface
- `Vector`: Vector class with mathematical operations
- `GramSchmidt`: Gram-Schmidt algorithm implementation

## Classes

### Vector

Represents a vector in R^n with operations:

- `dot(Vector other)` - Inner product
- `norm()` - Vector magnitude
- `multiply(double scalar)` - Scalar multiplication
- `subtract(Vector other)` - Vector subtraction
- `projectOnto(Vector other)` - Vector projection
- `normalize()` - Unit vector normalization

### GramSchmidt

Static methods for orthonormalization:

- `orthogonalize(List<Vector> basis)` - Creates orthogonal basis
- `orthonormalize(List<Vector> basis)` - Creates orthonormal basis
- `areLinearlyIndependent(List<Vector> vectors)` - Validates independence
- `isOrthogonal(List<Vector> basis)` - Verifies orthogonality
- `isOrthonormal(List<Vector> basis)` - Verifies orthonormality

## Usage

### Compilation

```bash
javac Main.java
```

### Execution

```bash
java Main
```

### Example Session

```
============================================================
GRAM-SCHMIDT ORTHONORMALIZATION PROCESS
============================================================

Enter the dimension of the space (2-10): 3

Enter 3 vectors for R^3:

Vector 1:
  Component 1: 1
  Component 2: 1
  Component 3: 0

Vector 2:
  Component 1: 1
  Component 2: 0
  Component 3: 1

Vector 3:
  Component 1: 0
  Component 2: 1
  Component 3: 1

------------------------------------------------------------
ORIGINAL BASIS:
------------------------------------------------------------
v1 = (1.0000, 1.0000, 0.0000)
v2 = (1.0000, 0.0000, 1.0000)
v3 = (0.0000, 1.0000, 1.0000)

------------------------------------------------------------
ORTHOGONAL BASIS:
------------------------------------------------------------
w1 = (1.0000, 1.0000, 0.0000)
w2 = (0.5000, -0.5000, 1.0000)
w3 = (-0.3333, 0.3333, 0.6667)
Verification: Is orthogonal? YES ✓

------------------------------------------------------------
ORTHONORMAL BASIS:
------------------------------------------------------------
u1 = (0.7071, 0.7071, 0.0000)
u2 = (0.4082, -0.4082, 0.8165)
u3 = (-0.5774, 0.5774, 0.5774)
Verification: Is orthonormal? YES ✓

============================================================
Process completed successfully!
============================================================
```

## Input Validation

The program validates:

- Dimension must be between 2 and 10
- All components must be valid numbers
- Vectors must be linearly independent
- Handles invalid input gracefully with error messages

## Mathematical Concepts

### Orthogonal Basis

Vectors are orthogonal if their dot product is zero:

- wᵢ · wⱼ = 0 for all i ≠ j

### Orthonormal Basis

An orthonormal basis is orthogonal with unit vectors:

- uᵢ · uⱼ = 0 for i ≠ j
- ||uᵢ|| = 1 for all i

### Linear Independence

Vectors are linearly independent if no vector can be expressed as a linear combination of the others.

## Improvements Over Original Implementation

1. **Modularity**: Separated into Vector, GramSchmidt, and Main classes
2. **Generalization**: Works for any dimension, not just R³
3. **Validation**: Checks linear independence before processing
4. **Readability**: Clear variable names and method signatures
5. **Reusability**: Vector and GramSchmidt classes can be used in other projects
6. **Error handling**: Comprehensive input validation
7. **Verification**: Confirms correctness of results
8. **Single file**: Easy to share and submit

## Limitations

- Maximum dimension is set to 10 (can be adjusted in code)
- Uses floating-point arithmetic (may have small rounding errors)
- Tolerance for zero comparison is 1e-10

## Requirements

- Java 8 or higher
- No external dependencies

## Future Enhancements

- [ ] Support for complex vector spaces
- [ ] Matrix representation of basis transformations
- [ ] Export results to file
- [ ] Graphical visualization for 2D and 3D cases
- [ ] Modified Gram-Schmidt for better numerical stability
- [ ] Support for arbitrary precision arithmetic

## License

Free to use and modify.

## Author

Felipe Pereira
