package Server;

import java.util.ArrayList;

public class RequestParser {
	private int type, length, blockNum;
	private byte[] fileData;
	private String filename;
	private ArrayList<Integer> positionOf0;
	public RequestParser() {}
	
	public void parseRequest(byte[] data) {
		length = data.length;
		type = data[1];
		split(data);
	}
	
	private void split(byte[] data) {
		for(int i = 0; i < length; i++) {
			if(data[i] == 0) {
				positionOf0.add(i);
			}
		}
		if(type == 0 || type == 1) {
			filename = parseFilename(data, positionOf0.get(1) - 1);
		}else if(type == 3 || type == 4) {
			blockNum = parseBlockNum(data);
			if(type == 4) {
				parseFileData(data);
			}
		}else {
			// error handling
		}
	}
	
	private String parseFilename(byte[] data, int endFlag) {
		return new String(data, 2, endFlag);
	}
	
	private int parseBlockNum(byte[] data) {
		int left = data[2];
		int right = data[3];
		if(left < 0) left += 256;
		if(right < 0) right += 256;
		return left * 256 + right;
	}
	
	private byte[] parseFileData(byte[] data) {
		fileData = new byte[data.length - 4];
		for(int i = 4; i < data.length; i++) {
			fileData[i - 4] = data[i];
		}
		return fileData;
	}
	
	public int getType() {
		return type;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public int getBlockNum() {
		return blockNum;
	}
	
	public byte[] getFileData() {
		return fileData;
	}
	
}
