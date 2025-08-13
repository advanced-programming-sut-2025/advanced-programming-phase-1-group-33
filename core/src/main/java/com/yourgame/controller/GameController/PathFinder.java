package com.yourgame.controller.GameController;

import com.badlogic.gdx.math.Vector2;
import com.yourgame.model.Map.Map;
import com.yourgame.model.Map.Tile;

import java.util.*;

public class PathFinder {
    private static class Node implements Comparable<Node> {
        int x, y;
        float gCost; // Cost from start to this node
        float hCost; // Heuristic cost from this node to end
        Node parent;

        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        float fCost() {
            return gCost + hCost;
        }

        @Override
        public int compareTo(Node other) {
            return Float.compare(this.fCost(), other.fCost());
        }
    }

    public static List<Vector2> findPath(Map map, Vector2 startPos, Vector2 endPos) {
        int startX = (int) (startPos.x / Tile.TILE_SIZE);
        int startY = (int) (startPos.y / Tile.TILE_SIZE);
        int endX = (int) (endPos.x / Tile.TILE_SIZE);
        int endY = (int) (endPos.y / Tile.TILE_SIZE);

        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<String> closedList = new HashSet<>();
        Node[][] grid = new Node[map.getMapWidth()][map.getMapHeight()];

        for (int x = 0; x < map.getMapWidth(); x++) {
            for (int y = 0; y < map.getMapHeight(); y++) {
                grid[x][y] = new Node(x, y);
            }
        }

        Node startNode = grid[startX][startY];
        Node endNode = grid[endX][endY];
        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedList.add(currentNode.x + "," + currentNode.y);

            if (currentNode == endNode) {
                return retracePath(startNode, endNode);
            }

            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x == 0 && y == 0) continue; // Skip the node itself

                    int checkX = currentNode.x + x;
                    int checkY = currentNode.y + y;

                    if (checkX >= 0 && checkX < map.getMapWidth() && checkY >= 0 && checkY < map.getMapHeight()) {
                        Node neighbor = grid[checkX][checkY];
                        if (map.getTile(checkX, checkY).isWalkable() && !closedList.contains(checkX + "," + checkY)) {
                            float newMovementCost = currentNode.gCost + getDistance(currentNode, neighbor);
                            if (newMovementCost < neighbor.gCost || !openList.contains(neighbor)) {
                                neighbor.gCost = newMovementCost;
                                neighbor.hCost = getDistance(neighbor, endNode);
                                neighbor.parent = currentNode;

                                if (!openList.contains(neighbor)) {
                                    openList.add(neighbor);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // No path found
    }

    private static List<Vector2> retracePath(Node startNode, Node endNode) {
        List<Vector2> path = new ArrayList<>();
        Node currentNode = endNode;
        while (currentNode != startNode) {
            path.add(new Vector2(currentNode.x * Tile.TILE_SIZE, currentNode.y * Tile.TILE_SIZE));
            currentNode = currentNode.parent;
        }
        Collections.reverse(path);
        return path;
    }

    private static float getDistance(Node a, Node b) {
        int dstX = Math.abs(a.x - b.x);
        int dstY = Math.abs(a.y - b.y);
        return (dstX > dstY) ? 14 * dstY + 10 * (dstX - dstY) : 14 * dstX + 10 * (dstY - dstX);
    }
}
