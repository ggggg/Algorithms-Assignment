import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class Main {
    // debug method
    public static void main(String[] args) {
        try {
            // read the file name.
            String[] names = readFile("names.txt");
            // print out the array
            System.out.println("Names array: " + Arrays.toString(names));
            // make copies of the array to test sorting.
            String[] names2 = names.clone();
            String[] namesSearch = names.clone();
            // write all the chars of all the names to a file
            writeLettersToFile(names);
            // write a reversed version of names to a file
            reverseNames(names);
            // sort the names array using selection sort
            selectionSort(names);
            System.out.println("selection sorted names: " +Arrays.toString(names));
            // sort the names array using bubble sort
            bubbleSort(names2);
            System.out.println("bubble sorted names: " +Arrays.toString(names2));
            // linear search test
            System.out.println("Index of \"Test\" in an unsorted array - linear search: "+ linear(namesSearch, "Test"));
            // binary search test
            System.out.println("Index of \"Test\" in a sorted array - binary search: "+ binary(namesSearch, "Test"));
        } catch (IOException e) {
            // in case of error.
            System.out.println("An error has occurred.");
            // exit the program with error code.
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Write all the letters from a names array into file.
     *
     * @param array the names array.
     * @throws IOException
     */
    public static void writeLettersToFile(String[] array) throws IOException {
        // the file to output to.
        final String fileName = "namesChars.txt";
        FileWriter fw = new FileWriter(fileName);
        // for each name split it to chars.
        for (String name : array) {
            // for each char append it to the file.
            for (char c : name.toCharArray()) {
                fw.append(String.valueOf(c)).append("\n");
            }
        }
        fw.close();
    }

    /**
     * Recursive method that will write an array in reverse into a file.
     *
     * @param array the array to be reversed
     * @param index the index of the array.
     * @param fw    the file writer that will be used to write to a file.
     * @param <T>   the type of the array.
     * @throws IOException
     */
    private static <T> void reverse(T[] array, int index, FileWriter fw) throws IOException {
        // if it is the last item don't add a new line at the end of the file and return.
        if (index == 0) {
            fw.append(array[index].toString());
            return;
        }
        // add the name into the file.
        fw.append(array[index].toString()).append("\n");
        // call the function on the next index in the array.
        reverse(array, --index, fw);
    }

    /**
     * Call a recursive method that will write an array in reverse into a file.
     *
     * @param array the names array.
     * @param <T>   the type of the array.
     * @throws IOException
     */
    public static <T> void reverseNames(T[] array) throws IOException {
        // the output file.
        FileWriter fw = new FileWriter("reversedNames.txt");
        // call the recursive method
        reverse(array, array.length - 1, fw);
        fw.close();
    }

    /**
     * Read a file.
     *
     * @param fileName the name of the file to be read
     * @return an array with all the lines of the file.
     * @throws IOException
     */
    public static String[] readFile(String fileName) throws IOException {
        // the file to be read.
        File file = new File(fileName);
        // start the scanner object
        Scanner scanner = new Scanner(file);
        // new array list to store all the lines.
        List<String> lines = new ArrayList<>();
        // add all the lines
        while (scanner.hasNext()) {
            lines.add(scanner.nextLine());
        }
        scanner.close();
        // convert the list to array and return it.
        return lines.toArray(new String[0]);
    }

    /**
     * Sort an array and preform binary search on it to find an item
     *
     * @param arr        array to be searched for an item.
     * @param searchItem the item to be found.
     * @param <T>        the type of the array (works with anything that is Comparable, eg. integer, string...)
     * @return the index of the item to be found or -1 in case the item is not found.
     */
    public static <T extends Comparable<T>> int binary(T[] arr, T searchItem) {
        // sort the array
        bubbleSort(arr);
        // preform the binary search
        return binarySorted(arr, searchItem);
    }

    /**
     * Preform binary search on an already sorted array to find an item
     *
     * @param arr        array to be searched for an item.
     * @param searchItem the item to be found.
     * @param <T>        the type of the array (works with anything that is Comparable, eg. integer, string...)
     * @return the index of the item to be found or -1 in case the item is not found.
     */
    public static <T extends Comparable<T>> int binarySorted(T[] arr, T searchItem) {
        // the lowest index that
        int low = 0;
        int high = arr.length - 1;
        // loop through while there is a range (would stop if nothing is found)
        while (low <= high) {
            // the mid point of the array
            int mid = (low + high) / 2;
            // if the item was found, return its index.
            if (arr[mid].compareTo(searchItem) == 0)
                return mid;
                // if the item has a lower value then the search item
            else if (arr[mid].compareTo(searchItem) > 0)
                high = mid - 1;
                // if the item has a lower value then the search item
            else
                low = mid + 1;
        }
        //return -1 if not found
        return -1;
    }

    /**
     * Preform linear search on array to find an item
     *
     * @param arr        array to be searched for an item.
     * @param searchItem the item to be found.
     * @param <T>        the type of the array (works with anything that is Comparable, eg. integer, string...)
     * @return the index of the item to be found or -1 in case the item is not found.
     */
    public static <T extends Comparable<T>> int linear(T[] arr, T searchItem) {
        // for each item in the array.
        for (int i = 0; i < arr.length; i++) {
            // check if the item is found
            if (arr[i].compareTo(searchItem) == 0) {
                return i;
            }
        }
        // return -1 if not found.
        return -1;
    }

    /**
     * Sort items in array using bubble sort.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of the array (works with anything that is Comparable, eg. integer, string...)
     */
    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        // for each item in the array
        for (int i = 0; i < array.length - 1; i++) {
            // go up the array (only where its unsorted)
            for (int j = 0; j < array.length - i - 1; j++) {
                // swap items if one is bigger then the other.
                if (array[j].compareTo(array[j + 1]) > 0) {
                    swap(array, j + 1, j);
                }
            }
        }
    }

    /**
     * Sort items in array using selection sort.
     *
     * @param array the array to be sorted.
     * @param <T>   the type of the array (works with anything that is Comparable, eg. integer, string...)
     */
    public static <T extends Comparable<T>> void selectionSort(T[] array) { // selection sort
        // for each item in the array
        for (int i = 0; i < array.length; i++) {

            int smallestIndex = i;
            // find the smallest number in remaining part of the array
            for (int j = i + 1; j < array.length; j++) {
                if (array[j].compareTo(array[smallestIndex]) < 0) {
                    smallestIndex = j;
                }
            }
            // swap the numbers
            swap(array, i, smallestIndex);
        }
    }

    /**
     * Swap two items in array.
     *
     * @param array    the parent array for the swap.
     * @param indexOne the index of the 1st item to swap.
     * @param indexTwo the index of the 2nd item to swap.
     * @param <T>      the type of the array (works with any type).
     */
    private static <T> void swap(T[] array, int indexOne, int indexTwo) {
        // temp value to hold the value of index one
        T temp = array[indexOne];
        // swap the values
        array[indexOne] = array[indexTwo];
        array[indexTwo] = temp;
    }
}