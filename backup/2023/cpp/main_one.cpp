#include <cctype>
#include <fstream>
#include <iostream>
#include <map>
#include <vector>

std::map<std::string, int> number_map = {
    {"one", 1}, {"two", 2},   {"three", 3}, {"four", 4}, {"five", 5},
    {"six", 6}, {"seven", 7}, {"eight", 8}, {"nine", 9},
};

std::vector<std::string> read_in_file(std::string filename) {
  std::vector<std::string> lines{};
  std::string line;
  std::ifstream f_in(filename);
  if (!f_in.is_open()) {
    std::cerr << "Couldn't open " << filename << " for reading";
  }

  while (std::getline(f_in, line)) {
    lines.push_back(line);
  }

  f_in.close();
  return lines;
}

int get_first_digit(std::string s) {
  for (int i = 0; i < s.length(); ++i) {
    if (std::isdigit(static_cast<unsigned char>(s[i]))) {
      return int(s[i] - '0');
    }
  }
  return -1;
}

int get_first_digit_inc_text(std::string s) {
  for (int i = 0; i < s.length(); ++i) {
    if (std::isdigit(static_cast<unsigned char>(s[i]))) {
      return int(s[i] - '0');
    }
    for (auto &entry : number_map) {

      std::string word = entry.first;
      int word_len = word.length();

      if (word_len <= (s.length() - i)) {
        if (s.compare(i, word_len, word) == 0) {
          return entry.second;
        }
      }
    }
  }
  return -1;
}

int get_last_digit(std::string s) {
  for (int i = s.length(); i >= 0; --i) {
    if (std::isdigit(static_cast<unsigned char>(s[i]))) {
      return int(s[i] - '0');
    }
  }
  return -1;
}

int get_last_digit_inc_text(std::string s) {
  for (int i = s.length(); i >= 0; --i) {
    if (std::isdigit(static_cast<unsigned char>(s[i]))) {
      return int(s[i] - '0');
    }
    for (auto &entry : number_map) {

      std::string word = entry.first;
      int word_len = word.length();

      if (word_len <= (s.length() - i)) {
        if (s.compare(i, word_len, word) == 0) {
          return entry.second;
        }
      }
    }
  }
  return -1;
}

int get_calibration_value(std::string s, bool inc_text = false) {
  int f = inc_text ? get_first_digit_inc_text(s) : get_first_digit(s);
  int l = inc_text ? get_last_digit_inc_text(s) : get_last_digit(s);
  std::string val_s = std::to_string(f) + std::to_string(l);
  int val_i = std::stoi(val_s);
  return val_i;
}

std::vector<int> get_calibration_values(std::vector<std::string> lines,
                                        bool inc_text = false) {
  std::vector<int> values{};
  for (auto &line : lines) {
    values.push_back(get_calibration_value(line, inc_text));
  }
  return values;
}

int main(void) {
  std::vector<std::string> lines = read_in_file("../input.txt");
  std::vector<int> cal_values = get_calibration_values(lines, true);

  int sum{0};
  for (auto &value : cal_values) {
    sum += value;
  }

  std::cout << sum << std::endl;
}
