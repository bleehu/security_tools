import java.io.File;
import java.nio.file.Paths;
import java.util.Scanner;

public class CaesarShifter {
	
	private String cipherText;
	
	private FileRW printer;
	
	public CaesarShifter(FileRW cipherRW, FileRW printer) {
		this.cipherText = cipherRW.readFile();
		this.printer = printer;
		commandsBlock();
	}
	
	public static void main(String[] args) {
		/*String filename;
		if (args.length == 0) {
			System.out.println("Please enter a .txt file name in the assets folder.");
			Scanner scan = new Scanner(System.in);
			String token = scan.next();
			token = token.trim();
			scan.close();
			filename = token;
		}
		else {
			filename = args[0];
		}*/
		FileRW cipherRW = new FileRW(new File(Paths.get("src/assets/source.txt").toString()));
		FileRW printer = new FileRW(new File(Paths.get("src/assets/printFile.txt").toString()));
		
		new CaesarShifter(cipherRW, printer);
	}
	
	public void commandsBlock() {
		Scanner scan = new Scanner(System.in);
		scan.useDelimiter(System.lineSeparator());
		String key;
		String token = scan.next();
		String command = token.trim();
		while (! command.equals("exit")) {
			System.out.println("Enter command.");
			//Format: brute force chars
			if (command.equals("brute force chars")) {
				bruteCharShift();
			//Format: xor 00100001
			} else if (command.length() > 3 && command.substring(0, 3).equals("xor")) {
				key = command.substring(4, command.length());
				xorShift(key);
			} else {
				System.out.println("Unrecognized Command");
			}
			token = scan.next();
			command = token.trim();
		}
		scan.close();
	}
	
	public void bruteCharShift() {
		String[] force = new String[26];
		force[0] = cipherText;
		String workspace;
		StringBuilder shifty = new StringBuilder();
		int workLen;
		for (int i = 1; i < 26; i++) {
			workspace = force[i-1];
			workLen = workspace.length();
			for (int j = 0; j < workLen; j++) {
				char c = workspace.charAt(j);
				if ('a' <= c && c < 'z' || 'A' <= c && c < 'Z') {
					c = (char)(((int) c) + 1);
				} else if (c == 'z') {
					c = 'a';
				} else if (c == 'Z') {
					c = 'A';
				}
				shifty.append(c);
			}
			force[i] = shifty.toString();
			shifty.delete(0, shifty.length());
		}
		for (int k = 0; k < 26; k++) {
			shifty.append(force[k]);
			shifty.append(System.lineSeparator());
		}
		printer.writeToFile(shifty.toString());
		System.out.println("26 Simple Caesar Shifts written to printFile.txt");
	}
	
	public void xorShift(String key) {
		String numericText = textToNumber(cipherText);
		int keylen = key.length();
		int numtlen = numericText.length();
		int index = 0;
		int end;
		String workString;
		Integer[] kDigit = new Integer[keylen];
		Integer cDigit;
		for (int i = 0; i < keylen; i++) {
			kDigit[i] = Integer.parseInt(key.substring(i, i+1));
		}
		StringBuilder xorey = new StringBuilder();
		while (index < numtlen) {
			end = Math.min(index + keylen, numtlen);
			workString = numericText.substring(index, end);
			for (int i = 0; i < keylen && index + i < end; i++) {
				cDigit = Integer.parseInt(workString.substring(i, i+1));
				xorey.append(cDigit ^ kDigit[i]);
			}
			index += keylen;
		}
		String xorText = numberToText(xorey.toString());
		System.out.println("XOR operation with key " + key + " complete.");
		System.out.println("Result is: " + xorText);
	}
	
	private String textToNumber(String plainText) {
		StringBuilder numericText = new StringBuilder();
		Integer numChar;
		for (int i = 0; i < plainText.length(); i++) {
			numChar = new Integer((int) plainText.charAt(i));
			if (numChar >= 0 && numChar < 10) {//One-digit numChar
				numericText.append("000");
				numericText.append(numChar.toString());
			} else if (numChar >= 10 && numChar < 100) {//Two-digit numChar
				numericText.append("00");
				numericText.append(numChar.toString());
			} else if (numChar >= 100 && numChar < 1000) {//Three-digit numChar
				numericText.append("0");
				numericText.append(numChar.toString());
			} else if (numChar >= 1000) {//Four digit behavior
				numericText.append(numChar.toString());
			} else if (numChar >= 10000) {//Five+ digit behavior
				//TODO Error Handling and rewrite code to handle 5-digit numChars
			} else {//negative numChar is anomalous
				//TODO Error Handling
			}
		}
		return numericText.toString();
	}
	
	private String numberToText(String numericText) {
		int len = numericText.length();
		StringBuilder plainText = new StringBuilder();
		int index = 0;
		String padUnit;
		while (index < len) {
			padUnit = numericText.substring(index, index + 4);
			plainText.append((char) (Integer.parseInt(padUnit)));
			index += 4;
		}
		return plainText.toString();
	}
}
