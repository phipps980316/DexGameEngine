package Beta;

import java.util.Objects;

public class Vertex {
    private final Point3D position;
    private final Point2D texture;
    private final Point3D normal;


    public Vertex(Point3D position, Point2D texture, Point3D normal){
        this.position = position;
        this.texture = texture;
        this.normal = normal;
    }

    public Point3D getPosition() {
        return position;
    }

    public Point2D getTexture() {
        return texture;
    }

    public Point3D getNormal() {
        return normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return position.equals(vertex.position) &&
                texture.equals(vertex.texture) &&
                normal.equals(vertex.normal);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, texture, normal);
    }
}
