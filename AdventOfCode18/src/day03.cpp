#include <iostream>
#include <fstream>
#include <vector>
#include <sstream>
#include <string>

using namespace std;

void readLineContent(string line, int* id, int* leftInches, int* topInches, int* width, int* height);

int main() {
	ifstream inputFile;
	int overlappedInches = 0;
	vector<vector<int>> fabric;
	vector<int> auxFabric;
	vector <string> claims;
	int id, leftInches, topInches, width, height;
	string line;
	int intactFabricId;
	bool validFabric;

	inputFile.open("res/day03_input.txt");

	// Filling the fabric with 0s
	for (int i = 0; i < 1000; i++) {
		for (int j = 0; j < 1000; j++)
			auxFabric.push_back(0);
		fabric.push_back(auxFabric);
	}

	// Filling the fabric with the claims
	while (!inputFile.eof()) {
		getline(inputFile, line);
		claims.push_back(line);
		readLineContent(line, &id, &leftInches, &topInches, &width, &height);
		for (int i = topInches; i < (topInches + height); i++) {
			for (int j = leftInches; j < (leftInches + width); j++) {
				if (fabric[i][j] == 0)
					fabric[i][j] = id;
				else {
					fabric[i][j] = -1;
				}
			}
		}
	}


	// calculating area - Part 1
	for (int i = 0; i < 1000; i++)
		for (int j = 0; j < 1000; j++)
			if (fabric[i][j] == -1)
				overlappedInches++;

	// calculating not overlapped claim - Part 2
	for (size_t i = 0; i < claims.size(); i++) {
		validFabric = true;
		readLineContent(claims[i], &id, &leftInches, &topInches, &width, &height);
		for (int i = topInches; i < (topInches + height); i++) {
			for (int j = leftInches; j < (leftInches + width); j++) {
				if (fabric[i][j] != id)
					validFabric = false;
			}
		}
		if (validFabric) { // found the fabric
			intactFabricId = id;
			break;
		}
	}

	cout << "Part 1 - Claimed Area: " << overlappedInches << endl;
	cout << "Part 2 - Intact claim ID: " << intactFabricId << endl;
	return 0;
}

void readLineContent(string line, int* id, int* leftInches, int* topInches, int* width, int* height) {
	stringstream ss;
	ss << line;
	char garbage;

	ss >> garbage;
	ss >> *id;
	ss >> garbage;
	ss >> *leftInches;
	ss >> garbage;
	ss >> *topInches;
	ss >> garbage;
	ss >> *width;
	ss >> garbage;
	ss >> *height;
}
