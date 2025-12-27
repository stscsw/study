package cn.csw.skipList;

/**
 * @author Lier
 * @since 2025/12/18 16:15
 */
public class SkipNode<T> {


    int key;
    T value;
    SkipNode<T> right, down;

    public SkipNode(int key, T value) {
        this.key = key;
        this.value = value;
    }


}
