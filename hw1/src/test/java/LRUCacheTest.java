import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LRUCacheTest {

    @Test
    void testEmpty() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(1);

        assertThat(lruCache.size()).isEqualTo(0);
        assertThat(lruCache.get("")).isEmpty();
    }

    @Test
    void testPut() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);

        lruCache.put("1", 1);
        lruCache.put("2", 2);
        lruCache.put("3", 3);

        assertThat(lruCache.get("1")).isEmpty();
        assertThat(lruCache.get("2")).contains(2);
        assertThat(lruCache.get("3")).contains(3);
    }

    @Test
    void testPutExisting() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);

        lruCache.put("1", 1);
        lruCache.put("1", 0);

        assertThat(lruCache.get("1")).contains(0);
    }

    @Test
    void testGet() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);

        lruCache.put("1", 1);
        lruCache.put("2", 2);

        assertThat(lruCache.get("1")).contains(1);
        assertThat(lruCache.get("2")).contains(2);
    }

    @Test
    void testPutAndGet() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);

        lruCache.put("1", 1);
        lruCache.put("2", 2);
        Optional<Integer> value = lruCache.get("1");
        lruCache.put("3", 3);

        assertThat(value).contains(1);
        assertThat(lruCache.get("1")).contains(1);
        assertThat(lruCache.get("2")).isEmpty();
        assertThat(lruCache.get("3")).contains(3);
    }

    @Test
    void testSize() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(2);

        lruCache.put("1", 1);
        assertThat(lruCache.size()).isEqualTo(1);
        lruCache.put("1", 0);
        assertThat(lruCache.size()).isEqualTo(1);
        lruCache.put("2", 2);
        assertThat(lruCache.size()).isEqualTo(2);
        lruCache.put("3", 3);
        assertThat(lruCache.size()).isEqualTo(2);
    }

    @Test
    void testNonPositiveCapacity() {
        assertThrows(AssertionError.class, () -> new LRUCache<String, Integer>(0));
        assertThrows(AssertionError.class,() -> new LRUCache<String, Integer>(-1));
    }

    @Test
    void testPutNullKeyAndValue() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(1);
        assertThrows(IllegalArgumentException.class, () -> lruCache.put(null, 1));
        assertThrows(IllegalArgumentException.class, () -> lruCache.put("1", null));
    }

    @Test
    void testNullGet() {
        LRUCache<String, Integer> lruCache = new LRUCache<>(1);
        assertThrows(IllegalArgumentException.class, () -> lruCache.get(null));
    }
}
