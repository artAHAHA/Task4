package com.cgvsu.math.vectors;

public class Vector3f extends AbstractVector<Vector3f> {

    public Vector3f(double... components) {
        super(components);
    }
    /**
     * Метод добавляет новое значение в конец текущего вектора.
     * Этот метод создаёт новый объект {@link Vector4f}, содержащий все компоненты
     * текущего вектора и добавленное значение. Исходный вектор остаётся неизменным.
     *
     * @param value значение, которое будет добавлено в конец вектора.
     * @return новый объект {@link Vector4f} с добавленным значением.
     */
    public Vector4f append(double value) {
        double[] newComponents = new double[4];
        System.arraycopy(components, 0, newComponents, 0, components.length);
        newComponents[3] = value;  // Добавляем значение в конец
        return new Vector4f(newComponents);  // Возвращаем новый Vector4f
    }
    /**
     * Метод вычисляет векторное произведение текущего трёхмерного вектора
     * с другим вектором и возвращает результат.
     * Векторное произведение определяется только для трёхмерных векторов и возвращает
     * новый вектор, перпендикулярный обоим входным векторам.
     *
     * @param other вектор {@link Vector3f}, с которым вычисляется векторное произведение.
     * @return новый объект {@link Vector3f}, представляющий результат векторного произведения.
     */
    public Vector3f cross(Vector3f other) {
        return new Vector3f(
                components[1] * other.get(2) - components[2] * other.get(1),
                components[2] * other.get(0) - components[0] * other.get(2),
                components[0] * other.get(1) - components[1] * other.get(0)
        );
    }

    @Override
    public Vector3f createInstance(double... components) {
        return new Vector3f(components);
    }
}
