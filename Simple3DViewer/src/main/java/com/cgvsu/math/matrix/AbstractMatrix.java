package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.AbstractVector;



public abstract class AbstractMatrix<T extends AbstractMatrix<T>> {

    protected double[][] elements;

    // Конструкторы
    public AbstractMatrix(double... array) {
        int size = this.getSize();
        if (array.length != size * size) {
            throw new IllegalArgumentException("Массив должен содержать ровно " + (size * size) + " элементов.");
        }
        this.elements = new double[size][size];
        int k = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++, k++) {
                this.elements[i][j] = array[k];
            }
        }
    }

    public AbstractMatrix(int one) {
        int size = this.getSize();
        this.elements = new double[size][size];
        for (int i = 0; i < size; i++) {
            this.elements[i][i] = 1;
        }
    }

    public AbstractMatrix() {
        int size = this.getSize();
        this.elements = new double[size][size];
    }

    public AbstractMatrix(double[][] array) {
        int size = this.getSize();
        if (array.length != size || array[0].length != size) {
            throw new IllegalArgumentException("Массив должен содержать ровно " + (size * size) + " элементов.");
        }
        this.elements = array;
    }

    // Абстрактные методы
    protected abstract T createInstance(double[] elements);
    protected abstract T createInstance(double[][] elements);
    protected abstract T createInstance();
    protected abstract int getSize();


    public void setZero() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                elements[i][j] = 0;
            }
        }
    }

    // Операции с матрицами
    public T add(T other) {
        return addMatrix(other);
    }

    private T addMatrix(T other) {
        if (other == null || other.getSize() != this.getSize()) {
            throw new IllegalArgumentException("Матрицы должны иметь одинаковый размер.");
        }
        double[] a = new double[this.getSize() * this.getSize()];
        int k = 0;
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++, k++) {
                a[k] = this.elements[i][j] + other.getElement(i, j);
            }
        }
        return createInstance(a);
    }

    // Умножение матрицы на другую матрицу
    public T multiply(T other) {
        if (other == null || other.getSize() != this.getSize()) {
            throw new IllegalArgumentException("Матрицы должны иметь одинаковый размер.");
        }
        T result = createInstance();
        for (int i = 0; i < getSize(); i++) {
            for (int k = 0; k < getSize(); k++) {
                float res = 0;
                for (int j = 0; j < getSize(); j++) {
                    res += this.elements[i][j] * other.elements[j][k];
                }
                result.elements[i][k] = res;
            }
        }
        return result;
    }

    public AbstractVector multiplyingMatrixByVector(AbstractVector vector) {
        // Проверяем размерность вектора и матрицы на совпадение
        if (vector.getDimension() != getSize()) {
            throw new IllegalArgumentException("Размер вектора должен совпадать с размером матрицы.");
        }

        // Создаем новый массив для результата
        double[] result = new double[getSize()];

        // Умножение матрицы на вектор
        for (int i = 0; i < getSize(); i++) {
            result[i] = 0;
            for (int j = 0; j < getSize(); j++) {
                result[i] += (float) (this.elements[i][j] * vector.get(j));  // Умножаем элементы
            }
        }
        // Возвращаем новый вектор с результатом
        return vector.createInstance(result);
    }


    // Транспонирование
    public T transposition() {
        T result = createInstance(elements);
        for (int i = 0; i < getSize(); i++) {
            for (int j = i + 1; j < getSize(); j++) {
                double temp = elements[i][j];
                result.elements[i][j] = elements[j][i];
                result.elements[j][i] = temp;
            }
        }
        return result;
    }



    // Прочие методы

    public abstract double getElement(int row, int col);
    public abstract void setElement(int row, int col, float value);

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                sb.append(String.format("%.2f\t", elements[i][j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
