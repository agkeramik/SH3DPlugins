package VectorUtils;

public class Vec3 {
	public float x, y, z;

	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vec3(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public Vec3(double x, double y) {
		this.x = (float) x;
		this.y = (float) y;
	}

	public Vec3(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;
	}

	public float norm() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public static Vec3 crossProduct(Vec3 u, Vec3 v) {
		return new Vec3(u.y * v.z - v.y * u.z, u.z * v.x - v.z * u.x, u.x * v.y
				- u.y * v.x);
	}

	public static float scalarProduct(Vec3 u, Vec3 v) {
		return u.x * v.x + u.y * v.y + u.z * v.z;
	}
	
	public Vec3 rotateVector2D(float angle){
		return new Vec3(Math.cos(angle)*x-Math.sin(angle)*y,Math.sin(angle)*x+Math.cos(angle)*y);
	}
}
