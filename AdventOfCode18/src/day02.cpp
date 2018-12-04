#include <iostream>
#include <fstream>
#include <vector>
#include <unordered_map>
#include <string>

using namespace std;

int calculateDifferentChars(string s1, string s2);
string calculateStringInCommun(string s1, string s2);

int main() {
	ifstream inputFile;
	string line, similarString;
	unordered_map<char,int> charCount;
	unordered_map<char,int>::iterator it;
	vector<string> lines;
	int doubleCounter = 0, tripleCounter = 0;
	bool doubleFlag, tripleFlag;
	bool stringFound = false;

	inputFile.open("res/day02_input.txt");


	// already reads the numbers sign (negative or positive)
	while (!inputFile.eof()) {
		tripleFlag = false;
		doubleFlag = false;
		getline(inputFile, line);
		lines.push_back(line); // for part 2
		// Processes line
		for (size_t i = 0; i < line.size(); i++) {
			it = charCount.find(line[i]);
			if (it != charCount.end())
				it->second++;
			else
				charCount.insert(pair<char,int>(line[i], 1));
		}
		// checks if there are any chars repeated two and three times
		for (it = charCount.begin(); it != charCount.end(); it++) {
			if (it->second == 2)
				doubleFlag = true;
			else if (it->second == 3)
				tripleFlag = true;
			if (tripleFlag && doubleFlag)
				break;
		}
		/* HARDCORE DEBUGGING :´)
		for (it = charCount.begin(); it != charCount.end(); it++) {
			cout << it->first << " - " << it->second << endl;
		}
		*/
		charCount.clear();
		if (doubleFlag) doubleCounter++;
		if (tripleFlag) tripleCounter++;
	}

	for (size_t i = 0; i < lines.size(); i++) {
		for (size_t j = i + 1; j < lines.size(); j++) {
			// Assuming its only 1 char difference cause im a lazy fuck
			if (calculateDifferentChars(lines[i], lines[j]) == 1) {
				similarString = calculateStringInCommun(lines[i], lines[j]);
				stringFound = true;
			}
		}
		if (stringFound) break;
	}

	cout << "Part 1 - Result: " << (doubleCounter * tripleCounter) << endl;
	cout << "Part 2 - Similar String: " << similarString << endl;
	return 0;
}

int calculateDifferentChars(string s1, string s2) {
	int differentChars = 0;

	for (size_t i = 0; i < s1.size() && i < s2.size(); i++)
		if (s1[i] != s2[i])
			differentChars++;

	return differentChars++;
}

string calculateStringInCommun(string s1, string s2) {
	string similarString;

	for (size_t i = 0; i < s1.size() && i < s2.size(); i++)
		if (s1[i] == s2[i])
			similarString.push_back(s1[i]);

	return similarString;
}
