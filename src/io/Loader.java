package io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
/**
 * The Loader loads and unloads serializable type from memory files
 *
 *
 */
public class Loader {
	/**
	 * Loads an object from a memory file and casts it to a Serializable type
	 * @param type type to which the object will be cast
	 * @param path path of the memory file
	 * @return The loaded and casted object
	 */
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
	/**
	 * Unloads a Serializable type object to a memory file and indicatees if the unloading was successful
	 * @param obj Object to unloaded
	 * @param path path of the memory file
	 * @return <tt>true</tt> if successful, <tt>false</tt> otherwise
	 */
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
