// Copy paste this Java Template and save it as "Labor.java"
import java.util.*;
import java.io.*;

// write your matric number here:
// write your name here:
// write list of collaborators here:
// year 2015 hash code: JESg5svjYpIsmHmIjabX (do NOT delete this line)

class Labor {
  private int V; // number of vertices in the graph (number of junctions in Singapore map)
  private int Q; // number of queries
  private Vector < Vector < IntegerPair > > AdjList; // the weighted graph (the Singapore map), the length of each edge (road) is stored here too, as the weight of edge

  // if needed, declare a private data structure here that
  // is accessible to all methods in this class
  // --------------------------------------------

  int[][] timeFromSource;

  public Labor() {
    // Write necessary code during construction
    //
    // write your answer here
    timeFromSource = new int[10][V];

  }

  void PreProcess() {
    int MAXIMUM_SOURCE_VERTEX = Math.min(9, V-1);

    for (int i = 0; i <= MAXIMUM_SOURCE_VERTEX; i++) {
      timeFromSource[i] = dijkstra(i);
    }
  }

  int[] dijkstra(int source) {
    int dist[] = new int[V];

    TreeMap<IntegerPair, Boolean> Q = new TreeMap<IntegerPair, Boolean>();
    Vector<IntegerPair> nodes = new Vector<IntegerPair>();

    dist[source] = 0;

    for (int v = 0; v < V; v++) {
      if (v != source) {
        dist[v] = Integer.MAX_VALUE;
      }
      IntegerPair p = new IntegerPair(dist[v], v);
      nodes.add(p);
    }

    Q.put(nodes.get(source), true);

    while (Q.size() > 0) {
      Map.Entry<IntegerPair, Boolean> pair = Q.pollFirstEntry();
      int u = pair.getKey()._second;

      Vector <IntegerPair> neighboursOfU = AdjList.get(u);
      Iterator itr = neighboursOfU.iterator();

      while (itr.hasNext()) {
        IntegerPair v = (IntegerPair)itr.next();
        int neighbour = v._first;

        int effort = dist[u] + v._second;
        if (effort < dist[neighbour]) {
          dist[neighbour] = effort;
          IntegerPair p = (IntegerPair)nodes.get(neighbour);
          Q.remove(p);
          p._first = effort;
          Q.put(p, true);
        }
      }
    }

    return dist;
  }

  int Query(int s, int t, int k) {
    return timeFromSource[s][t] == Integer.MAX_VALUE ? -1 : timeFromSource[s][t];
  }

  // You can add extra function if needed
  // --------------------------------------------



  // --------------------------------------------

  void run() throws Exception {
    // you can alter this method if you need to do so
    IntegerScanner sc = new IntegerScanner(System.in);
    PrintWriter pr = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));

    int TC = sc.nextInt(); // there will be several test cases
    while (TC-- > 0) {
      V = sc.nextInt();

      // clear the graph and read in a new graph as Adjacency List
      AdjList = new Vector < Vector < IntegerPair > >();
      for (int i = 0; i < V; i++) {
        AdjList.add(new Vector < IntegerPair >());

        int k = sc.nextInt();
        while (k-- > 0) {
          int j = sc.nextInt(), w = sc.nextInt();
          AdjList.get(i).add(new IntegerPair(j, w)); // edge (road) weight (in minutes) is stored here
        }
      }

      PreProcess(); // optional

      Q = sc.nextInt();
      while (Q-- > 0)
        pr.println(Query(sc.nextInt(), sc.nextInt(), sc.nextInt()));

      if (TC > 0)
        pr.println();
    }

    pr.close();
  }

  public static void main(String[] args) throws Exception {
    // do not alter this method
    Labor ps5 = new Labor();
    ps5.run();
  }
}



class IntegerScanner { // coded by Ian Leow, using any other I/O method is not recommended
  BufferedInputStream bis;
  IntegerScanner(InputStream is) {
    bis = new BufferedInputStream(is, 1000000);
  }

  public int nextInt() {
    int result = 0;
    try {
      int cur = bis.read();
      if (cur == -1)
        return -1;

      while ((cur < 48 || cur > 57) && cur != 45) {
        cur = bis.read();
      }

      boolean negate = false;
      if (cur == 45) {
        negate = true;
        cur = bis.read();
      }

      while (cur >= 48 && cur <= 57) {
        result = result * 10 + (cur - 48);
        cur = bis.read();
      }

      if (negate) {
        return -result;
      }
      return result;
    }
    catch (IOException ioe) {
      return -1;
    }
  }
}

class IntegerPair implements Comparable < IntegerPair > {
  Integer _first, _second;

  public IntegerPair(Integer f, Integer s) {
    _first = f;
    _second = s;
  }

  public int compareTo(IntegerPair o) {
    if (!this.first().equals(o.first()))
      return this.first() - o.first();
    else
      return this.second() - o.second();
  }

  Integer first() { return _first; }
  Integer second() { return _second; }
}
