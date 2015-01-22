package vo;

import java.io.Serializable;
import java.util.HashMap;

public class PropertyBag implements Serializable {
	private final static long serialVersionUID = 2;
	public HashMap<String, String> properties;

    public PropertyBag() {
        properties = new HashMap<String, String>();
    }

    public String get(String key) {
   		return properties.get(key);
   	}

    public void put(String key, String value) {
        properties.put(key, value);
     }

	public long getLong(String key) {
		return Long.valueOf(properties.get(key));
	}

    public void putLong(String key, long value) {
        properties.put(key, ""+value);
    }
}
