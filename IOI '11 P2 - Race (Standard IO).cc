#include <bits/stdc++.h>

using namespace std;

const int MAX = 200000;  
const long long INF = LLONG_MAX;  
int n, k, center;  
long long ans = INF;  
vector<pair<int, int>> adj[MAX];  
map<int, int> cnt[MAX];  
bool block[MAX];  // track blocked centroids
int sizes[MAX];  // store the subtree sizes

void calculateSubtreeSize(int v, int p) {
    sizes[v] = 1;  
    for (auto& pair : adj[v]) {
        int u = pair.first;
        if (u == p || block[u]) continue;  
        calculateSubtreeSize(u, v);
        sizes[v] += sizes[u];  // new subtree size
    }
}

int findCentroid(int v, int p, int total) {
    for (auto& pair : adj[v]) {
        int u = pair.first;
        if (!block[u] && u != p && sizes[u] * 2 > total)
            return findCentroid(u, v, total); 
    }
    return v;  // the centroid
}

// find pairs with given sum using DFS
void dfsFindPairs(int u, int p, int lvl, int w) {
    if (k >= w && cnt[center].count(k - w)) {
        ans = min(ans, (long long) lvl + cnt[center][k - w]);
    }
    for (auto& pair : adj[u]) {
        int v = pair.first, wt = pair.second;
        if (v == p || block[v]) continue;  // skip parent/blocked nodes
        dfsFindPairs(v, u, lvl + 1, w + wt);
    }
}

// store distances/levels from centroid
void dfsStorePairs(int u, int p, int lvl, int w) {
    auto it = cnt[center].find(w);
    if (it == cnt[center].end()) {
        cnt[center][w] = lvl;  // new dist
    } else {
        cnt[center][w] = min(it->second, lvl);  // update w min level
    }
    for (auto& pair : adj[u]) {
        int v = pair.first, wt = pair.second;
        if (v == p || block[v]) continue;  // skip parent/blocked nodes
        dfsStorePairs(v, u, lvl + 1, w + wt);
    }
}

// solve for minimum path length
void solve(int u) {
    calculateSubtreeSize(u, -1);
    int p = findCentroid(u, -1, sizes[u]);
    center = p;
    block[p] = true;
    cnt[center].clear();
    cnt[center][0] = 0;
    for (auto& pair : adj[p]) {
        int v = pair.first, wt = pair.second;
        if (block[v]) continue;
        dfsFindPairs(v, p, 1, wt);
        dfsStorePairs(v, p, 1, wt);
    }
    for (auto& pair : adj[p]) {
        int v = pair.first;
        if (block[v]) continue;
        solve(v);
    }
}

int main() {
    cin >> n >> k;
    for (int i = 0; i < n - 1; i++) {
        int x, y, w;
        cin >> x >> y >> w;
        adj[x].emplace_back(y, w);
        adj[y].emplace_back(x, w);
    }
    solve(0);
    cout << (ans == INF ? -1 : ans) << endl;
    return 0;
}
