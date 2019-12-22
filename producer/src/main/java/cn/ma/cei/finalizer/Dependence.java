package cn.ma.cei.finalizer;

import cn.ma.cei.exception.CEIException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Dependence<T extends IDependenceNode> {

    private final Map<String, Node<T>> nodeMap = new HashMap<>();
    private final List<Node<T>> nodeList = new LinkedList<>();

    public void addNode(T node, T dependOn) {
        Node<T> currentNode = getOrCreateNode(node);
        if (dependOn != null) {
            currentNode.dependenceList.add(getOrCreateNode(dependOn));
        }
    }

    public List<T> decision() {
        List<T> res = new LinkedList<>();
        T node;
        while ((node = select()) != null) {
            res.add(node);
        }
        return res;
    }

    private T select() {
        if (nodeMap.isEmpty()) {
            return null;
        }
        for (Map.Entry<String, Node<T>> node : nodeMap.entrySet()) {
            if (node.getValue().node == null) {
                throw new CEIException("Dependence in Error case");
            }
            if (node.getValue().dependenceList.isEmpty()) {
                T res = node.getValue().node;
                node.getValue().node = null;
                nodeMap.remove(node.getKey());
                return res;
            } else {
                boolean ignore = false;
                for (Node<T> dependOn : node.getValue().dependenceList) {
                    if (dependOn.node != null) {
                        ignore = true;
                        break;
                    }
                }
                if (!ignore) {
                    T res = node.getValue().node;
                    node.getValue().node = null;
                    nodeMap.remove(node.getKey());
                    return res;
                }
            }
        }
        throw new CEIException("Dependence has a loop");
    }

    private Node<T> getOrCreateNode(T node) {
        if (!nodeMap.containsKey(node.getIdentifier())) {
            Node<T> newNode = new Node<>();
            newNode.node = node;
            newNode.dependenceList = new LinkedList<>();
            nodeMap.put(node.getIdentifier(), newNode);
            nodeList.add(newNode);
        }
        return nodeMap.get(node.getIdentifier());
    }

    class Node<T> {
        T node = null;
        List<Node<T>> dependenceList = null;
    }
}
