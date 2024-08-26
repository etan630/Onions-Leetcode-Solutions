#include <bits/stdc++.h>

using namespace std;

int N, M, K;
vector<vector<int>> roads;
vector<int> solution;

struct Road {
    int source;
    int destination;
    int weight;
    int index;
    
    Road() : source(0), destination(0), weight(0), index(0) {}
    Road(int s, int d, int w, int i) : source(s), destination(d), weight(w), index(i) {}
    
    // sort by weight
    bool operator<(const Road& other) const {
        return weight < other.weight;
    }
};

// join 2 subsets
void unionJoin(int scenario, int root1, int root2) {
    roads[scenario][root1] += roads[scenario][root2];
    roads[scenario][root2] = root1;
}

// find w path compression
int unionFind(int scenario, int node) {
    int root = node;
    while (roads[scenario][root] >= 0) {
        root = roads[scenario][root];
    }
    
    while (node != root) {
        int parent = roads[scenario][node];
        roads[scenario][node] = root;
        node = parent;
    }
    return root;
}

void processRoads() {
    cin >> N >> M >> K;
    
    roads.assign(K + 1, vector<int>(N + 1, -1)); // init disjoint set forest
    solution.assign(M + 1, 0);
    
    vector<Road> r(M + 1);
    for (int i = 1; i <= M; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        r[i] = Road(u, v, w, i);
    }
    sort(r.begin() + 1, r.end()); // roads by weight
    
    for (int i = 1; i <= M; i++) {
        int left = 1, right = K, ind = INT_MAX;
        while (left <= right) {
            int mid = (left + right) / 2;
            int a = unionFind(mid, r[i].source);
            int b = unionFind(mid, r[i].destination);
            if (a == b) {
                left = mid + 1; // road forms a cycle, pass go and go next
            } else {
                ind = mid; // road can be added
                right = mid - 1;
            }
        }
        if (ind == INT_MAX) {
            solution[r[i].index] = -1; // doesnt fit -- -1
        } else {
            solution[r[i].index] = ind;
            unionJoin(ind, unionFind(ind, r[i].source), unionFind(ind, r[i].destination));
        }
    }
    
    for (int i = 1; i <= M; i++) {
        cout << solution[i] << endl;
    }
}

int main() {
    processRoads();
    return 0;
}
