#include <bits/stdc++.h>

using namespace std;

// handles circular indexing
int circular(int i, int shift, int N) {
    int total = i + shift;
    int positive = total + N;
    int wrapped = positive % N;
    return wrapped;
}

// solve the problem for one test case, later use for loop when printing output
vector<int> chika(int N, const vector<int>& a) {
    if (N == 4) {
        return vector<int>(N);
    }

    vector<int> solution(N), need, vis(N);
    vector<pair<int, int>> have;

    // finds init solution/possible candidates
    for (int i = 0; i < N; i++) {
        int prev = a[circular(i, -1, N)];
        int next = a[circular(i, 1, N)];
        int nextnext = a[circular(i, 3, N)];
        int prevprev = a[circular(i, -3, N)];

        if (prev == next) {
            solution[i] = prev;
        } else if (prev <= next && next < nextnext) {
            solution[i] = next;
        } else if (next <= prev && prev < prevprev) {
            solution[i] = prev;
        } else {
            solution[i] = 0;
        }

        if (!solution[i]) {
            have.push_back({max(prev, next), i});
        } else {
            vis[solution[i] - 1] = 1;
        }
    }

    // find all unvisited numbers
    for (int i = 0; i < N; ++i) {
        if (!vis[i]) {
            need.push_back(i + 1);
        }
    }

    // sort candidates and needs 
    sort(have.begin(), have.end());
    sort(need.begin(), need.end());

    // sets unvisited to correct positions
    for (int i = 0, r = 0, M = have.size(), count; i < M; ++i) {
        count = 0;
        while (r < M && have[r].first <= need[i]){
            r++;
            count++;
        }
        if (r == i + 1 && count == 1) {
            solution[have[i].second] = need[i];
        }
    }

    return solution;
}

int main() {
    // reads inputs
    int T, N;
    cin >> T;

    while (T--) {
        cin >> N;
        vector<int> a(N);
        for (int& value : a) {
            cin >> value;
        }

        vector<int> result = chika(N, a);
        for (int value : result) {
            cout << value << " ";
        }
        cout << endl;
    }
    return 0;
}
