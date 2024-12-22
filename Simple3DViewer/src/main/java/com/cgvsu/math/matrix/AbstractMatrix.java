package com.cgvsu.math.matrix;

import com.cgvsu.math.vectors.AbstractVector;

public abstract class AbstractMatrix<T extends AbstractMatrix<T>> {

    protected double[][] elements;

    /**
     * Конструктор, принимающий одномерный массив элементов.
     * Преобразует массив в матрицу.
     *
     * @param array Массив элементов для инициализации матрицы.
     * @throws IllegalArgumentException Если количество элементов не соответствует размеру матрицы.
     */
    public AbstractMatrix(double... array) {
        int size = this.getSize();
        if (array.length != size * size) {
            throw new IllegalArgumentException("Массив должен содержать ровно " + (size * size) + " элементов.");
        }
        this.elements = new double[size][size];
        int k = 0;
        // Заполняем элементы матрицы значениями из массива
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++, k++) {
                this.elements[i][j] = array[k];
            }
        }
    }

    /**
     * Конструктор для создания единичной матрицы.
     * Создает матрицу размерности getSize() с единицами на диагонали.
     *
     * @param one Параметр для создания единичной матрицы (не используется в реализации).
     */
    public AbstractMatrix(int one) {
        int size = this.getSize();
        this.elements = new double[size][size];
        // Заполняем диагональ единицами
        for (int i = 0; i < size; i++) {
            this.elements[i][i] = 1;
        }
    }

    /**
     * Конструктор по умолчанию.
     * Создает матрицу с пустыми элементами (по умолчанию - 0).
     */
    public AbstractMatrix() {
        int size = this.getSize();
        this.elements = new double[size][size];
    }

    /**
     * Конструктор, принимающий двумерный массив.
     * Проверяет, что размеры массива соответствуют размеру матрицы.
     *
     * @param array Двумерный массив для инициализации матрицы.
     * @throws IllegalArgumentException Если размеры массива не соответствуют размеру матрицы.
     */
    public AbstractMatrix(double[][] array) {
        int size = this.getSize();
        if (array.length != size || array[0].length != size) {
            throw new IllegalArgumentException("Массив должен содержать ровно " + (size * size) + " элементов.");
        }
        this.elements = array;
    }

    /**
     * Абстрактные методы, которые должны быть реализованы в наследниках.
     * Создает экземпляр матрицы с переданными значениями.
     *
     * @param elements Массив элементов для создания матрицы.
     * @return Новый экземпляр матрицы с заданными элементами.
     */
    protected abstract T createInstance(double[] elements);

    /**
     * Абстрактный метод для создания экземпляра матрицы с двумерным массивом элементов.
     *
     * @param elements Двумерный массив для создания матрицы.
     * @return Новый экземпляр матрицы с заданными элементами.
     */
    protected abstract T createInstance(double[][] elements);

    /**
     * Абстрактный метод для создания нового экземпляра матрицы.
     *
     * @return Новый экземпляр матрицы.
     */
    protected abstract T createInstance();

    /**
     * Абстрактный метод для получения размера матрицы.
     *
     * @return Размер матрицы.
     */
    protected abstract int getSize();

    /**
     * Метод для обнуления матрицы (устанавливает все элементы в 0).
     */
    public void setZero() {
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++) {
                elements[i][j] = 0;
            }
        }
    }

    /**
     * Метод для сложения текущей матрицы с другой матрицей.
     *
     * @param other Матрица, которую нужно добавить.
     * @return Результат сложения двух матриц.
     * @throws IllegalArgumentException Если размеры матриц не совпадают.
     */
    public T add(T other) {
        return addMatrix(other);
    }

    /**
     * Реализация сложения матриц.
     *
     * @param other Матрица для сложения.
     * @return Результат сложения матриц.
     * @throws IllegalArgumentException Если размеры матриц не совпадают.
     */
    private T addMatrix(T other) {
        if (other == null || other.getSize() != this.getSize()) {
            throw new IllegalArgumentException("Матрицы должны иметь одинаковый размер.");
        }
        double[] a = new double[this.getSize() * this.getSize()];
        int k = 0;
        // Сложение соответствующих элементов двух матриц
        for (int i = 0; i < this.getSize(); i++) {
            for (int j = 0; j < this.getSize(); j++, k++) {
                a[k] = this.elements[i][j] + other.getElement(i, j);
            }
        }
        return createInstance(a);
    }

    /**
     * Метод для умножения матрицы на другую матрицу.
     *
     * @param other Матрица для умножения.
     * @return Результат умножения двух матриц.
     * @throws IllegalArgumentException Если размеры матриц не совпадают.
     */
    public T multiply(T other) {
        if (other == null || other.getSize() != this.getSize()) {
            throw new IllegalArgumentException("Матрицы должны иметь одинаковый размер.");
        }
        T result = createInstance();
        // Алгоритм умножения матриц (стандартное умножение)
        for (int i = 0; i < getSize(); i++) {
            for (int k = 0; k < getSize(); k++) {
                double res = 0;
                for (int j = 0; j < getSize(); j++) {
                    res += this.elements[i][j] * other.elements[j][k];
                }
                result.elements[i][k] = res;
            }
        }
        return result;
    }

    /**
     * Метод для умножения матрицы на вектор.
     *
     * @param vector Вектор, на который нужно умножить матрицу.
     * @return Результат умножения матрицы на вектор.
     * @throws IllegalArgumentException Если размерность вектора не совпадает с размером матрицы.
     */
    public AbstractVector multiplyingMatrixByVector(AbstractVector vector) {
        // Проверка совместимости размерности матрицы и вектора
        if (vector.getDimension() != getSize()) {
            throw new IllegalArgumentException("Размер вектора должен совпадать с размером матрицы.");
        }

        // Создание нового массива для результата
        double[] result = new double[getSize()];

        // Алгоритм умножения матрицы на вектор
        for (int i = 0; i < getSize(); i++) {
            result[i] = 0;
            for (int j = 0; j < getSize(); j++) {
                result[i] += this.elements[i][j] * vector.get(j);
            }
        }
        // Возвращаем новый вектор с результатом умножения
        return vector.createInstance(result);
    }

    /**
     * Метод для транспонирования матрицы.
     * Транспонирует матрицу, меняя местами строки и столбцы.
     *
     * @return Транспонированная матрица.
     */
    public T transposition() {
        T result = createInstance(new double[getSize()][getSize()]);
        // Транспонирование матрицы (перестановка строк и столбцов)
        for (int i = 0; i < getSize(); i++) {
            for (int j = i + 1; j < getSize(); j++) {
                double temp = elements[i][j];
                result.elements[i][j] = elements[j][i];
                result.elements[j][i] = temp;
            }
        }
        return result;
    }

    /**
     * Абстрактный метод для получения элемента матрицы по индексу.
     *
     * @param row Индекс строки.
     * @param col Индекс столбца.
     * @return Значение элемента матрицы.
     */
    public abstract double getElement(int row, int col);

    /**
     * Абстрактный метод для установки элемента матрицы по индексу.
     *
     * @param row Индекс строки.
     * @param col Индекс столбца.
     * @param value Значение элемента.
     */
    public abstract void setElement(int row, int col, float value);

    /**
     * Возвращает строковое представление матрицы.
     *
     * @return Строка, представляющая матрицу.
     */
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
