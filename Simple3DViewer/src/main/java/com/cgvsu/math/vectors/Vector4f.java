package com.cgvsu.math.vectors;

public class Vector4f extends AbstractVector<Vector4f> {

    public Vector4f(double... components) {
        super(components);
    }

    /**
     * Метод усекает текущий вектор, удаляя последний компонент.
     * Этот метод создаёт новый объект {@link Vector3f}, содержащий только первые три компонента
     * текущего вектора. Используется для преобразования вектора с размерностью больше 3
     * в трёхмерный вектор.
     *
     * @return новый объект {@link Vector3f}, состоящий из первых трёх компонентов текущего вектора.
     */
    public Vector3f truncate() {
        return new Vector3f(components[0], components[1], components[2]);
    }

    @Override
    public Vector4f createInstance(double... components) {
        return new Vector4f(components);
    }
}
