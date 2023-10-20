package Helper;

public class Item {
	private int key; 
	private String value;
	
	public Item(int id, String value) {
		this.key = id;
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
