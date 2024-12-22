#include <fstream>
#include <iostream>
#include <map>
#include <string>
#include <vector>

enum COLOR { BLUE, RED, GREEN };

struct handful_t {
  std::map<COLOR, int> color_counts;
};

struct game_t {
  int id;
  std::vector<handful_t> hands;
};

game_t parse_game(std::string s) {
  game_t game{};
  std::string game_id_sig("Game");
  size_t game_id_loc = s.find(game_id_sig);
  if (game_id_loc == std::string::npos) {
    return game;
  }
  game.id = std::stoi(std::string(game_id_loc + 5, s.find_first_of(':')));
}

std::vector<game_t> read_in_data(std::string f_name) {
  std::string line;
  std::ifstream f_in(f_name);
  if (!f_in.is_open()) {
    std::cerr << "Couldn't open " << f_name << " for reading";
  }

  while (std::getline(f_in, line)) {
  }

  f_in.close();
}

int main(void) { return 0; }
