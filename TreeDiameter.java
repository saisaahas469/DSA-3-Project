
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TreeDiameter {

    static int bfs(List<List<Integer>> adj, int start, int[] dist, int[] parent) {
        Arrays.fill(dist, -1);
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        dist[start] = 0;
        int farthest = start;

        while (!queue.isEmpty()) {
            int u = queue.poll();
            if (dist[u] > dist[farthest]) {
                farthest = u;
            }

            for (int v : adj.get(u)) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        return farthest;
    }

    static boolean isValidTree(List<List<Integer>> adj, int n) {
        boolean[] visited = new boolean[n];
        int[] parent = new int[n];
        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        visited[0] = true;

        int visitedCount = 0;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            visitedCount++;

            for (int v : adj.get(u)) {
                if (v == u) {
                    return false;
                }
                if (parent[u] != v && visited[v]) {
                    if (parent[v] != u) {
                        return false;
                    }
                }
                if (!visited[v]) {
                    visited[v] = true;
                    parent[v] = u;
                    queue.add(v);
                }
            }
        }

        return visitedCount == n;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("A tree must have at least 1 node.");
            sc.close();
            return;
        }

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            adj.add(new ArrayList<>());
        }

        if (n == 1) {
            System.out.println("\nEndpoints : 0 and 0");
            System.out.println("Diameter  : 0 edges");
            System.out.println("Path      : [0]");
            sc.close();
            return;
        }

        System.out.println("Enter " + (n - 1) + " edges (u v), nodes numbered 0 to " + (n - 1) + ":");
        for (int i = 0; i < n - 1; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();

            if (u < 0 || u >= n || v < 0 || v >= n) {
                System.out.println("Invalid node index. Please use numbers between 0 and " + (n - 1) + ".");
                sc.close();
                return;
            }

            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        int[] dist = new int[n];
        int[] parent = new int[n];
        int a = bfs(adj, 0, dist, parent);
        int b = bfs(adj, a, dist, parent);

        int diameter = dist[b];
        List<Integer> path = new ArrayList<>();
        for (int cur = b; cur != -1; cur = parent[cur]) {
            path.add(cur);
        }
        Collections.reverse(path);

        System.out.println("\nEndpoints : " + a + " and " + b);
        System.out.println("Diameter  : " + diameter + " edges");
        System.out.println("Path      : " + path);
        sc.close();
    }
}