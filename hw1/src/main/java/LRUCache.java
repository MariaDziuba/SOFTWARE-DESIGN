import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class LRUCache<K, V> {
    private final Map<K, Node> data = new HashMap<>();
    protected final int capacity;
    protected Node head = null;
    protected Node tail = null;

    protected class Node {
        @NotNull
        K key;
        @NotNull
        V value;

        @Nullable
        Node prev, next;

        public Node(@NotNull K key, @NotNull V value) {
            Objects.requireNonNull(key);
            Objects.requireNonNull(value);
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        assert capacity > 0;
        this.capacity = capacity;
    }

    public void put(@NotNull K key, @NotNull V value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        int oldSize = size();
        Node node = data.get(key);
        if (node != null) {
            remove(node);
        }
        add(new Node(key, value));
        assert size() >= oldSize;
        Objects.requireNonNull(head);
        Objects.requireNonNull(tail);
        assert head.value == value;
    }

    @NotNull
    public Optional<V> get(@NotNull K key) {
        Objects.requireNonNull(key);
        int oldSize = size();
        Node node = data.get(key);
        if (node == null) {
            return Optional.empty();
        }
        remove(node);
        add(new Node(node.key, node.value));
        Optional<V> res = Optional.of(node.value);
        assert size() == oldSize;
        assert head.value == res.get();
        return res;
    }

    public int size() {
        int result = 0;
        Node cur = head;
        while (cur != null) {
            result++;
            cur = cur.prev;
        }
        assert result < capacity || result > 0;
        return result;
    }

    private void add(@NotNull Node newNode) {
        Objects.requireNonNull(newNode);
        if (size() == capacity) {
            if (capacity == 0) {
                return;
            }
            remove(tail);
        }
        if (head != null) {
            head.next = newNode;
            newNode.prev = head;
        }
        head = newNode;
        if (tail == null) {
            tail = newNode;
        }
        data.put(newNode.key, newNode);
    }

    private void remove(@NotNull Node node) {
        Objects.requireNonNull(node);
        data.remove(node.key);
        if (node.prev == null) {
            tail = node.next;
        } else {
            node.prev.next = node.next;
        }
        if (node.next == null) {
            head = node.prev;
        } else {
            node.next.prev = node.prev;
        }
    }
}