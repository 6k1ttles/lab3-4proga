package model;

/**
 * Запись координат точки в двумерном пространстве.
 * @param x Координата X
 * @param y Координата Y
 */
public record Point(double x, double y) {
    /**
     * Вычисляет расстояние до другой точки.
     * @param other Целевая точка
     * @return Расстояние
     */
    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }
    public static final Point ZERO = new Point(0, 0);
}