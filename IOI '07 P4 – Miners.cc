#include <bits/stdc++.h>
using namespace std;

// state to index
unordered_map<string, int> state_index = {
    {"", 0}, {"M", 1}, {"F", 2}, {"B", 3},
    {"MM", 4}, {"MF", 5}, {"MB", 6},
    {"FM", 7}, {"FF", 8}, {"FB", 9},
    {"BM", 10}, {"BF", 11}, {"BB", 12}
};

// reverse mapping
unordered_map<int, string> index_state;

// store the next state and coal value 
vector<vector<pair<int, int>>> transitions(13, vector<pair<int, int>>(3));

// shipment to index
int shipment_index(char c) {
    return c == 'M' ? 0 : (c == 'F' ? 1 : 2);
}

// coal based on curr and shipment
int coal_value(const string& state, char new_shipment) {
    if (state.empty()) return 1;
    unordered_set<char> types(state.begin(), state.end());
    types.insert(new_shipment);
    return types.size();
}

// state based on the curr state and new shipment
string update_state(const string& state, char new_shipment) {
    if (state.size() < 2) return state + new_shipment;
    return string(1, state[1]) + new_shipment;
}

// computes all possible state transitions/coal values
void precompute_transitions() {
    for (auto& p : state_index) {
        string state = p.first;
        int state_idx = p.second;
        for (char shipment : {'M', 'F', 'B'}) {
            string new_state = update_state(state, shipment);
            int coal = coal_value(state, shipment);
            transitions[state_idx][shipment_index(shipment)] = {state_index[new_state], coal};
        }
    }
}

int main() {
    for (const auto& p : state_index) {
        index_state[p.second] = p.first;
    }

    precompute_transitions(); 

    int N; 
    cin >> N;
    string shipments;
    cin >> shipments;

    // init dp
    vector<vector<int>> dp(13, vector<int>(13, 0));
    vector<vector<int>> dp_next(13, vector<int>(13, 0));

    // shipment last ot first
    for (int n = N - 1; n >= 0; --n) {
        char shipment = shipments[n];
        int ship_idx = shipment_index(shipment);
        for (int i = 0; i < 13; ++i) {
            for (int j = 0; j < 13; ++j) {
                auto& [new_i, coal_i] = transitions[i][ship_idx];
                auto& [new_j, coal_j] = transitions[j][ship_idx];
                
                int coal1 = coal_i + dp[new_i][j];
                int coal2 = coal_j + dp[i][new_j];

                dp_next[i][j] = max(coal1, coal2);
            }
        }
        dp.swap(dp_next); 
    }

    cout << dp[0][0] << endl; 

    return 0;
}
