package Beta;

public class Vertex {
    private Point3D position;
    private Point2D texture;
    private Point3D normal;

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
}
