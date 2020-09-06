package DataStructures;

import org.lwjgl.util.vector.Matrix4f;

import java.nio.FloatBuffer;

public class Matrix4D {
    public float[][] values = new float[4][4];

    public Matrix4D(){
        setIdentity();
    }

    public void setIdentity() {
        this.values[0][0] = 1.0f;
        this.values[0][1] = 0.0f;
        this.values[0][2] = 0.0f;
        this.values[0][3] = 0.0f;
        this.values[1][0] = 0.0f;
        this.values[1][1] = 1.0f;
        this.values[1][2] = 0.0f;
        this.values[1][3] = 0.0f;
        this.values[2][0] = 0.0f;
        this.values[2][1] = 0.0f;
        this.values[2][2] = 1.0f;
        this.values[2][3] = 0.0f;
        this.values[3][0] = 0.0f;
        this.values[3][1] = 0.0f;
        this.values[3][2] = 0.0f;
        this.values[3][3] = 1.0f;
    }

    public void translate(Vector3D vector){
        values[3][0] += values[0][0] * vector.x + values[1][0] * vector.y + values[2][0] * vector.z;
        values[3][1] += values[0][1] * vector.x + values[1][1] * vector.y + values[2][1] * vector.z;
        values[3][2] += values[0][2] * vector.x + values[1][2] * vector.y + values[2][2] * vector.z;
        values[3][3] += values[0][3] * vector.x + values[1][3] * vector.y + values[2][3] * vector.z;
    }

    public void rotate(float angle, Vector3D axis) {
        float cos = (float) Math.cos(angle);
        float sin = (float) Math.sin(angle);
        float oneMinusCos = 1.0f - cos;
        float xy = axis.x * axis.y;
        float yz = axis.y * axis.z;
        float xz = axis.x * axis.z;
        float xs = axis.x * sin;
        float ys = axis.y * sin;
        float zs = axis.z * sin;

        float f[][] = {{axis.x * axis.x * oneMinusCos + cos, xy * oneMinusCos + zs, xz * oneMinusCos - ys},
                       {xy * oneMinusCos - zs, axis.y * axis.y * oneMinusCos + cos, yz * oneMinusCos + xs},
                       {xz * oneMinusCos + ys, yz * oneMinusCos - xs, axis.z * axis.z * oneMinusCos + cos}};

        float t[][] = {{values[0][0] * f[0][0] + values[1][0] * f[0][1] + values[2][0] * f[0][2], values[0][1] * f[0][0] + values[1][1] * f[0][1] + values[2][1] * f[0][2], values[0][2] * f[0][0] + values[1][2] * f[0][1] + values[2][2] * f[0][2], values[0][3] * f[0][0] + values[1][3] * f[0][1] + values[2][3] * f[0][2]},
                       {values[0][0] * f[1][0] + values[1][0] * f[1][1] + values[2][0] * f[1][2], values[0][1] * f[1][0] + values[1][1] * f[1][1] + values[2][1] * f[1][2], values[0][2] * f[1][0] + values[1][2] * f[1][1] + values[2][2] * f[1][2], values[0][3] * f[1][0] + values[1][3] * f[1][1] + values[2][3] * f[1][2]}};

        values[2][0] = values[0][0] * f[2][0] + values[1][0] * f[2][1] + values[2][0] * f[2][2];
        values[2][1] = values[0][1] * f[2][0] + values[1][1] * f[2][1] + values[2][1] * f[2][2];
        values[2][2] = values[0][2] * f[2][0] + values[1][2] * f[2][1] + values[2][2] * f[2][2];
        values[2][3] = values[0][3] * f[2][0] + values[1][3] * f[2][1] + values[2][3] * f[2][2];
        values[0][0] = t[0][0];
        values[0][1] = t[0][1];
        values[0][2] = t[0][2];
        values[0][3] = t[0][3];
        values[1][0] = t[1][0];
        values[1][1] = t[1][1];
        values[1][2] = t[1][2];
        values[1][3] = t[1][3];
    }

    public void scale(Vector3D vector) {
        values[0][0] = values[0][0] * vector.x;
        values[0][1] = values[0][1] * vector.x;
        values[0][2] = values[0][2] * vector.x;
        values[0][3] = values[0][3] * vector.x;
        values[1][0] = values[1][0] * vector.y;
        values[1][1] = values[1][1] * vector.y;
        values[1][2] = values[1][2] * vector.y;
        values[1][3] = values[1][3] * vector.y;
        values[2][0] = values[2][0] * vector.z;
        values[2][1] = values[2][1] * vector.z;
        values[2][2] = values[2][2] * vector.z;
        values[2][3] = values[2][3] * vector.z;
    }

    public Matrix4D store(FloatBuffer buffer) {
        buffer.put(this.values[0][0]);
        buffer.put(this.values[0][1]);
        buffer.put(this.values[0][2]);
        buffer.put(this.values[0][3]);
        buffer.put(this.values[1][0]);
        buffer.put(this.values[1][1]);
        buffer.put(this.values[1][2]);
        buffer.put(this.values[1][3]);
        buffer.put(this.values[2][0]);
        buffer.put(this.values[2][1]);
        buffer.put(this.values[2][2]);
        buffer.put(this.values[2][3]);
        buffer.put(this.values[3][0]);
        buffer.put(this.values[3][1]);
        buffer.put(this.values[3][2]);
        buffer.put(this.values[3][3]);
        return this;
    }


}
