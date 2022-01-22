public class ArrayDeque<T> {
    int size;
    int nextFirst;
    T[] items;
    int nextLast;
    final double USAGE_FACTOR = 0.25;

    public ArrayDeque() {
        size = 0;
        nextFirst = 0;
        items = (T[]) new Object[8];
        nextLast = 1;
    }

    public void addFirst(T item) {
        items[nextFirst] = item;

        if (nextFirst != 0) {
            nextFirst -= 1;
        } else {
            nextFirst = items.length - 1;
        }

        size += 1;
        resize();

    }

    public T removeFirst() {
        int index = nextFirst + 1;
        if (index > items.length - 1) {
            index = 0;
        }
        T temp = items[index];
        if (nextFirst == items.length - 1) {
            nextFirst = 0;
        } else {
            nextFirst += 1;
        }
        size -= 1;
        resize();
        return temp;
    }

    public void addLast(T item) {
        items[nextLast] = item;
        if (nextLast != items.length - 1) {
            nextLast += 1;
        } else {
            nextLast = 0;
        }
        size += 1;
        resize();
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void printDeque() {
        int i = nextFirst + 1;
        int count = size;

        while (count > 0) {
            if (i > items.length - 1) {
                i = 0;
            }

            System.out.print(" " + items[i]);
            i += 1;
            count -= 1;
        }

    }

    public T removeLast() {
        int index = nextLast - 1;
        if (index == 0) {
            index = items.length - 1;
        }
        T temp = items[index];
        nextLast -= 1;
        size -= 1;
        resize();
        return temp;
    }

    public T get(int index) {
        return items[index];
    }

    public boolean needExtend() {
        return size + 2 == items.length;
        //size+2 refer to the data and two pointer
    }

    public boolean needReduce() {
        return size + 2 < items.length * USAGE_FACTOR;
    }

    public T[] arrayExtend(T[] items) {
        int newLength = items.length * 2;
        T[] newArr = (T[]) new Object[newLength];

        if (nextLast < nextFirst) {
            for (int i = 0; i < nextLast; i++) {
                newArr[i] = items[i];
            }

            int newNextFirst = nextFirst + items.length;
            int newArrIndex = newNextFirst + 1;
            int itemsIndex = nextFirst + 1;
            while (newArrIndex < newArr.length) {
                newArr[newArrIndex] = items[itemsIndex];
                newArrIndex += 1;
                itemsIndex += 1;
            }
            nextFirst = newNextFirst;
        }

        if (nextFirst < nextLast) {
            //NF and NL are initialized as 0 and 1 respectively
            //So when nextFirst<nextLast, items[0] always is nextFirst
            //the next 3 lines can be deleted
            for (int i = 0; i < nextFirst; i++) {
                newArr[i] = items[i];
            }

            int newArrIndex = nextFirst + 1;
            int itemsIndex = nextFirst + 1;
            while (newArrIndex < nextLast) {
                newArr[newArrIndex] = items[itemsIndex];
                newArrIndex += 1;
                itemsIndex += 1;
            }
            //nextFirst and nextLast needn't be change
        }

        return newArr;
    }

    public T[] arrayReduce(T[] items) {
        int newLength = items.length / 2;
        T[] newArr = (T[]) new Object[newLength];

        if (nextLast < nextFirst) {
            for (int i = 0; i < nextLast; i++) {
                newArr[i] = items[i];
            }

            int newNextFirst = nextFirst - items.length / 2;
            int newArrIndex = newNextFirst + 1;
            int itemsIndex = nextFirst + 1;
            while (newArrIndex < newArr.length) {
                newArr[newArrIndex] = items[itemsIndex];
                newArrIndex += 1;
                itemsIndex += 1;
            }
        }

        if (nextFirst < nextLast) {
            int newNextFirst = 0;
            //I do this for convenient,
            //otherwise the position of newNextFirst will be complex

            int newNextLast = newNextFirst + size + 1;
            int newArrIndex = newNextFirst + 1;
            int itemsIndex = nextFirst + 1;

            while (newArrIndex < newNextLast) {
                newArr[newArrIndex] = items[itemsIndex];
                newArrIndex += 1;
                itemsIndex += 1;
            }
        }
        return newArr;
    }

    public void resize() {
        if (items.length >= 16) {

            if (needExtend()) {
                items = arrayExtend(items);
            }
            if (needReduce()) {
                items = arrayReduce(items);
            }

        } else {
            if (needExtend()) {
                items = arrayExtend(items);
            }
        }

    }

}