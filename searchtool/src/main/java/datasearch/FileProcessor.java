/*
 * Copyright © 2020 Mirada Medical Ltd.
 * All Rights Reserved.
 */
package datasearch;

/**
 * A handler to register on tools that need
 * to feedback a file reference to process
 * the file.
 * 
 * @author Dez Wright
 *
 */
public interface FileProcessor {
	
	void processFile(String filePath);

}
