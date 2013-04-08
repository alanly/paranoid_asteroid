package game.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Loader {
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T load(Class<T> type, String path) {
		File file = new File(path);
		T obj = null;
		
		if (file.isFile() && file.canRead()) {
			try {
				FileInputStream fileIn = new FileInputStream(file);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				
				obj = (T) objectIn.readObject();
				
				objectIn.close();
				fileIn.close();
			} catch (Exception e) {
				// Something went wrong
			}
		}
		
		return obj;
	}
	
	public static <T extends Serializable> boolean unload(T obj, String path) {
		File file = new File(path);
		
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			
			objectOut.writeObject(obj);
			
			objectOut.close();
			fileOut.close();
			
			return true;
		} catch (Exception e) {
			// Something went wrong
		}
		
		return false;
	}
}
