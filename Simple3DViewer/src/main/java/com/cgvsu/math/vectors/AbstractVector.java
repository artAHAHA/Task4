package com.cgvsu.math.vectors;

import java.util.Arrays;

public abstract class AbstractVector<T extends AbstractVector<T>> {
    protected final double[] components;
    public static final double EPS = 1e-7;
    private double x;
    private double y;
    private double z;
    private double w;

    /**
     * Конструктор для создания вектора с заданными компонентами.
     *
     * @param components Компоненты вектора.
     */
    public AbstractVector(double... components) {
        this.components = Arrays.copyOf(components, components.length);
    }

    /**
     * Получает размерность вектора (количество компонентов).
     *
     * @return Размерность вектора.
     */
    public int getDimension() {
        return components.length;
    }

    /**
     * Получает компонент вектора по индексу.
     *
     * @param index Индекс компоненты.
     * @return Значение компоненты.
     */
    public double get(int index) {
        return components[index];
    }

    /**
     * Получает первую компоненту вектора (x).
     *
     * @return Значение компоненты x.
     */
    public double getX() {
        return components[0];
    }

    /**
     * Получает вторую компоненту вектора (y).
     *
     * @return Значение компоненты y.
     */
    public double getY() {
        return components[1];
    }

    /**
     * Получает третью компоненту вектора (z).
     *
     * @return Значение компоненты z.
     */
    public double getZ() {
        return components[2];
    }

    /**
     * Получает четвертую компоненту вектора (w).
     *
     * @return Значение компоненты w.
     */
    public double getW() {
        return components[3];
    }

    /**
     * Устанавливает значение для первой компоненты вектора (x).
     *
     * @param x Значение компоненты x.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Устанавливает значение для второй компоненты вектора (y).
     *
     * @param y Значение компоненты y.
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Устанавливает значение для третьей компоненты вектора (z).
     *
     * @param z Значение компоненты z.
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Устанавливает значение для четвертой компоненты вектора (w).
     *
     * @param w Значение компоненты w.
     */
    public void setW(double w) {
        this.w = w;
    }

    /**
     * Получает все компоненты вектора.
     *
     * @return Массив всех компонент вектора.
     */
    public double[] getComponents() {
        return components.clone();
    }

    /**
     * Устанавливает значение компоненты вектора по индексу.
     *
     * @param index Индекс компоненты.
     * @param value Значение компоненты.
     */
    public void set(int index, double value) {
        components[index] = value;
    }

    /**
     * Проверяет, равен ли текущий вектор другому вектору.
     * Вектора считаются равными, если их компоненты совпадают с точностью до заданной погрешности (EPS).
     *
     * @param other Вектор для сравнения.
     * @return true, если вектора равны, иначе false.
     */
    public boolean isEqual(T other) {
        if (getDimension() != other.getDimension()) {
            return false;
        }
        for (int i = 0; i < components.length; i++) {
            if (Math.abs(components[i] - other.get(i)) >= EPS) {
                return false;
            }
        }
        return true;
    }

    /**
     * Метод для сложения текущего вектора с другим вектором.
     *
     * @param other Вектор для сложения.
     * @return Результат сложения двух векторов.
     * @throws IllegalArgumentException Если размерности векторов не совпадают.
     */
    public T add(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] + other.get(i);
        }
        return createInstance(result);
    }

    /**
     * Метод для сложения текущего вектора с другим вектором (изменяет текущий вектор).
     *
     * @param other Вектор для сложения.
     * @throws IllegalArgumentException Если размерности векторов не совпадают.
     */
    public void addV(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] += other.get(i);
        }
    }

    /**
     * Метод для вычитания другого вектора из текущего.
     *
     * @param other Вектор для вычитания.
     * @return Результат вычитания.
     * @throws IllegalArgumentException Если размерности векторов не совпадают.
     */
    public T subtract(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double[] result = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            result[i] = components[i] - other.get(i);
        }
        return createInstance(result);
    }

    /**
     * Метод для вычитания другого вектора из текущего (изменяет текущий вектор).
     *
     * @param other Вектор для вычитания.
     * @throws IllegalArgumentException Если размерности векторов не совпадают.
     */
    public void subtractV(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] -= other.get(i);
        }
    }

    /**
     * Метод для умножения вектора на скаляр.
     *
     * @param scalar Скаляр для умножения.
     */
    public void multiplyByScalar(double scalar) {
        for (int i = 0; i < getDimension(); i++) {
            components[i] *= scalar;
        }
    }

    /**
     * Метод для деления вектора на скаляр.
     * Если скаляр равен нулю, выбрасывается исключение.
     *
     * @param scalar Скаляр для деления.
     * @throws ArithmeticException Если попытка деления на ноль.
     */
    public void divideByScalar(double scalar) {
        if (Math.abs(scalar) < EPS) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] /= scalar;
        }
    }

    /**
     * Метод для вычисления длины вектора.
     *
     * @return Длина вектора.
     */
    public double getLength() {
        double sum = 0;
        for (double component : components) {
            sum += component * component;
        }
        return Math.sqrt(sum);
    }

    /**
     * Метод для нормализации вектора (делает его длину равной 1).
     * Если длина вектора равна нулю, выбрасывается исключение.
     *
     * @throws ArithmeticException Если попытка нормализации нулевого вектора.
     */
    public void normalize() {
        double length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        for (int i = 0; i < getDimension(); i++) {
            components[i] /= length;
        }
    }

    /**
     * Метод для получения нормализованной версии вектора.
     * Создает новый вектор с длиной 1.
     *
     * @return Нормализованный вектор.
     * @throws ArithmeticException Если попытка нормализации нулевого вектора.
     */
    public T getNormalized() {
        double length = getLength();
        if (Math.abs(length) < EPS) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        double[] normalizedComponents = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            normalizedComponents[i] = components[i] / length;
        }
        return createInstance(normalizedComponents);
    }

    /**
     * Метод для вычисления скалярного произведения двух векторов.
     *
     * @param other Вектор для вычисления скалярного произведения.
     * @return Результат скалярного произведения.
     * @throws IllegalArgumentException Если размерности векторов не совпадают.
     */
    public double dot(T other) {
        if (getDimension() != other.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double result = 0;
        for (int i = 0; i < getDimension(); i++) {
            result += components[i] * other.get(i);
        }
        return result;
    }

    /**
     * Абстрактный метод для создания нового экземпляра вектора с заданными компонентами.
     *
     * @param components Компоненты для создания нового вектора.
     * @return Новый вектор с заданными компонентами.
     */
    public abstract T createInstance(double... components);

    /**
     * Возвращает строковое представление вектора.
     *
     * @return Строка, представляющая вектор.
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName() + Arrays.toString(components);
    }
}
