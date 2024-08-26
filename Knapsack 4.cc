#include "bits/stdc++.h"
using namespace std;

using ll = long long;
using pii = pair<int, int>;

// constants
const int MW = 2001 * (1 << 7), D = MW / 2;
const ll LLINF = 0x3f3f3f3f3f3f3f3f;

// global variables
int N, M;
ll dp[2][MW];

// Read input data for a single test case
void readInput(vector<pii>& items) {
    cin >> N >> M;
    int w, v;

    // pos items
    for (int i = 0; i < N; i++) {
        cin >> w >> v;
        items.emplace_back(w, v);
    }

    // neg items
    for (int i = 0; i < M; i++) {
        cin >> w >> v;
        items.emplace_back(-w, v);
    }

    // shuffle
    random_device rd;
    mt19937 gen(rd());
    shuffle(items.begin(), items.end(), gen);
}

// init the dp table with negative inf
void initializeDP() {
    memset(dp, -0x3f, sizeof dp);
    dp[0][D] = 0; // middle as starting point
}

// knapsack
void performKnapsack(const vector<pii>& items) {
    int c = 0, n = 1;
    for (const auto& item : items) {
        memset(dp[n], -0x3f, sizeof dp[n]);
        for (int i = 0; i < MW; i++) {
            if (0 <= i + item.first && i + item.first < MW)
                dp[n][i + item.first] = max(dp[n][i + item.first], dp[c][i] + item.second);
            dp[n][i] = max(dp[n][i], dp[c][i]);
        }
        swap(c, n);
    }

    cout << dp[c][D] << '\n';
}

void solve() {
    vector<pii> items;

    readInput(items);
    initializeDP();

    performKnapsack(items);
}

int main() {
    int T;
    cin >> T;
    while (T--) {
        solve();
    }

    return 0;
}
