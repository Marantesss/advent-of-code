#include <iostream>
#include <fstream>
#include <vector>
#include <unordered_set>

using namespace std;

int main() {
	ifstream inputFile;
	int sum = 0;
	int number;
	vector<int> frequencys;

	inputFile.open("res/day1_input.txt");
	frequencys.push_back(0);

	// already reads the numbers sign (negative or positive)
	while (!inputFile.eof()) {
		inputFile >> number;
		sum += number;
		frequencys.push_back(sum);
	}

	unordered_set<int> tab;

	int lastSum = frequencys[frequencys.size() - 1];

	for (size_t i = 0; i < frequencys.size(); i++) {
		frequencys.push_back(frequencys[i + 1] + lastSum);
		pair<unordered_set<int>::iterator, bool> res = tab.insert(frequencys[i]);
		if (res.second == false) {
			cout << *res.first << endl;
			break;
		}
	}

	cout << "Result: " << sum << endl;
	return 0;
}
