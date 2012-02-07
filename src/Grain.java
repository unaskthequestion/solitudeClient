
public class Grain {
	float x;		// time
	float y;		// position in soundfile
	float thickness = 100; // size of grain

	
	public Grain(float px, float py){
		x = px;
		y = py;
	}
	
	public Grain(float px, float py, float pth){
		x = px;
		y = py;
		thickness = pth;
	}
	
	public void setThickness(float t){
		thickness = t;
	}
}
