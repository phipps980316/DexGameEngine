package Beta;

import java.util.Objects;

public class Point2D {
    public float x;
    public float y;

    public Point2D(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2D point2D = (Point2D) o;
        return Float.compare(point2D.x, x) == 0 &&
                Float.compare(point2D.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
