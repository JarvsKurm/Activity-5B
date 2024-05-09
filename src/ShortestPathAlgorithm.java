import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class ShortestPathAlgorithm {
    LinkedList<LinkedList<Double>> graph = new LinkedList<>();
    double minDist = -1;
    LinkedList<Integer> minPath = new LinkedList<>();
    LinkedList<Integer> nodeIndex = new LinkedList<>();

    void shortestPath(HashMap<Integer, Node> nodes, int start, int end, LinkedList<Road> roads) {
        if (start == -1 || end == -1) return;

        // reset vars
        minDist = -1;
        nodeIndex.clear();
        graph.clear();
        minPath.clear();

        // compiles the distances of nodes per node on a 2D array
        int jCount = -1;
//        System.out.println(nodes.size());
        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) nodeIndex.add(entry.getKey());
        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) {
            jCount++;
            Node j = entry.getValue();
            graph.add(new LinkedList<>());
            if (entry.getKey() == start) start = jCount;
            if (entry.getKey() == end) end = jCount;
            int connection = 0;
            for (int i = 0; i < nodes.size(); i++) {
                if (connection < j.connections.size() && Objects.equals(nodeIndex.get(i), j.connections.get(connection))) {
                    Node n2 = nodes.get(j.connections.get(connection));
                    double distance = Math.sqrt(Math.pow(j.x - n2.x, 2) + Math.pow(j.y - n2.y, 2));
                    graph.getLast().add(distance);
                    connection++;
                }
                else graph.getLast().add(-1.0);
            }
            graph.get(jCount).set(jCount, 0.0);
        }
        for (LinkedList<Double> ll : graph) System.out.println(ll);

        // shortest path computation
        LinkedList<Integer> path = new LinkedList<>();
        path.add(start);
        System.out.println(start + " " + end);
        if (start == end) {
            minPath = path;
            minDist = 0;
        }
        else shortestPathRecursion(path, end);
        System.out.println(nodeIndex);
        for (HashMap.Entry<Integer, Node> entry : nodes.entrySet()) entry.getValue().shineOff();
        for (Road r : roads) r.shineOff();
        if (minDist != -1) {
            System.out.println(minPath);
            System.out.println(minDist);
            for (int i = 0; i < minPath.size(); i++) {
                nodes.get(nodeIndex.get(minPath.get(i))).shineOn();
                for (Road road : roads)
                    if (i > 0 && (
                            nodeIndex.get(minPath.get(i)) == road.nodeA && nodeIndex.get(minPath.get(i - 1)) == road.nodeB ||
                            nodeIndex.get(minPath.get(i)) == road.nodeB && nodeIndex.get(minPath.get(i - 1)) == road.nodeA))
                        road.shineOn();
            }
        }
        else System.out.println("There is no way to your destination :(");
    }

    void shortestPathRecursion(LinkedList<Integer> path, int end) {
        LinkedList<Integer> newPath = new LinkedList<>(path);
//        System.out.println(newPath);
        for (int i = 0; i < graph.getFirst().size(); i++) if (!newPath.contains(i)) {
            if (graph.get(newPath.getLast()).get(i) != -1) {
                newPath.add(i);
                if (i == end) {
                    double distance = 0;
                    for (int j = 0; j < newPath.size() - 1; j++) distance += graph.get(newPath.get(j)).get(newPath.get(j + 1));
                    if (minDist == -1) {
                        minDist = distance;
                        minPath.clear();
                        minPath.addAll(newPath);
//                        System.out.println("minPath " + minPath + " " + minDist);
                    }
                    else if (minDist > distance) {
                        minDist = distance;
                        minPath.clear();
                        minPath.addAll(newPath);
//                        System.out.println("minPath " + minPath + " " + minDist);
                    }
                }
                shortestPathRecursion(newPath, end);
                newPath.removeLast();
            }
        }
    }
}