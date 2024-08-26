#include <bits/stdc++.h>

using namespace std;

const int MAX = 100005 + 10;
vector<pair<int, int>> graph[MAX]; // adj list
vector<int> centers; // center of tree
bool processed[MAX]; 
int wholeDiameter = 0; 
int furthestDistance[MAX]; 

pair<int, int> bfsFurthestNode(int X) {
    queue<tuple<int, int, int>> bfs;
    bfs.push({0, X, -1});
    int maxDist = INT_MIN, furthestNode = -1;
    while (!bfs.empty()) {
        auto [dist, v, p] = bfs.front();
        bfs.pop();
        processed[v] = true;
        if (dist > maxDist) {
            maxDist = dist;
            furthestNode = v;
        }
        furthestDistance[v] = max(furthestDistance[v], dist);
        for (auto neighbor : graph[v]) {
            int u = neighbor.first, w = neighbor.second;
            if (u == p) continue;
            bfs.push({dist + w, u, v});
        }
    }
    return {furthestNode, maxDist};
}

int findDiameter(int X) {
    auto [farthest1, _] = bfsFurthestNode(X);
    auto [farthest2, dist] = bfsFurthestNode(farthest1);
    wholeDiameter = max(wholeDiameter, dist);
    return farthest2;
}

// find the radius from x
int findRadius(int X) {
    queue<tuple<int, int, int>> bfs;
    bfs.push({0, X, -1});
    int minDist = INT_MAX, radiusNode = -1;
    while (!bfs.empty()) {
        auto [dist, v, p] = bfs.front();
        bfs.pop();
        if (furthestDistance[v] < minDist) {
            minDist = furthestDistance[v];
            radiusNode = v;
        }
        for (auto neighbor : graph[v]) {
            int u = neighbor.first, w = neighbor.second;
            if (u == p) continue;
            bfs.push({dist + w, u, v});
        }
    }
    return minDist;
}

int processTree(int v) {
    int node1 = findDiameter(v);
    int node2 = findDiameter(node1);
    int node3 = findDiameter(node2);
    return findRadius(node3);
}

int longestTravelTime(int N, int M, int L, vector<vector<int>>& trails) {
    for (int i = 0; i < MAX; i++) {
        graph[i].clear();
    }
    fill(begin(processed), end(processed), false); // clear if processed
    fill(begin(furthestDistance), end(furthestDistance), 0); 

    for (auto& trail : trails) {
        int u = trail[0], v = trail[1], w = trail[2];
        graph[u].push_back({v, w});
        graph[v].push_back({u, w});
    }

    centers.clear(); // clear previous centers
    wholeDiameter = 0; 

    for (int i = 0; i < N; i++) {
        if (processed[i]) continue;
        centers.push_back(processTree(i));
    }

    sort(centers.begin(), centers.end(), greater<int>());

    // calc max diameter
    if (centers.size() >= 2)
        wholeDiameter = max(wholeDiameter, centers[0] + centers[1] + L);
    if (centers.size() >= 3)
        wholeDiameter = max(wholeDiameter, centers[1] + centers[2] + 2 * L);

    return wholeDiameter;
}

int main() {
    int N, M, L;
    cin >> N >> M >> L;
    vector<vector<int>> trails(M, vector<int>(3));
    for (int i = 0; i < M; i++) {
        cin >> trails[i][0] >> trails[i][1] >> trails[i][2];
    }

    cout << longestTravelTime(N, M, L, trails) << endl;
    return 0;
}
