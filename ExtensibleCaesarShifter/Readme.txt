Note on filepaths:

	The abstract filepathing in the main method needs to know what folder you put CaesarShifter in. On my machine it's in src/, 
but you may need to change src to whatever your particular parent directory is.

Operation Instructions:

1. Run the program.
2. When prompted for a command,

exit: terminates the program.

brute force chars: Runs 25 Caesar shifts on the text in assets/source.txt and prints the results to printFile.txt

xor "key": Runs an XOR cipher on the text in assets/source.txt and prints the result on standard out. The required text in the command
	is "xor ", and the rest must be an arbitrarily long sequence of 0 and 1 digits.
	Of note, this assumes a four-digit mapping of characters.