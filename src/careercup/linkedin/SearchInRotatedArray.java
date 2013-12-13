package careercup.linkedin;

/**
 * Created by Sobercheg on 12/11/13.
 * http://www.careercup.com/question?id=15489754
 * There is rotated sorted array. Write the program to find any element in that array
 * Original Array A={1,2,3,5,6,7,8}
 * Rotated Array B={5,6,7,8,1,2,3}
 * <p/>
 * Write the program to find any element in array B
 */
public class SearchInRotatedArray {

    public int findElement(int[] array, int value) {
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            if (array[low] <= array[mid] && array[mid] <= array[high]) { // normal BS
                if (array[mid] < value) {
                    low = mid + 1;
                } else if (array[mid] > value) {
                    high = mid - 1;
                } else return mid;
            } else if (array[low] >= array[mid] && array[mid] <= array[high]) { // sequence stitch in the left half: 7,8,1,2,3,4
                if (array[high] < value) {
                    high = mid - 1;
                } else if (array[high] > value) {
                    low = mid + 1;
                } else return high;
            } else if (array[low] <= array[mid] && array[mid] >= array[high]) { // sequence stitch in the right half: 6,7,8,9,1,2
                if (array[low] > value) {
                    low = mid + 1;
                } else if (array[low] < value) {
                    high = mid - 1;
                } else return low;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        SearchInRotatedArray searchInRotatedArray = new SearchInRotatedArray();
        System.out.println(searchInRotatedArray.findElement(new int[]{1, 2, 3, 4, 5, 6, 7}, 6));
        System.out.println(searchInRotatedArray.findElement(new int[]{1, 2, 3, 4, 5, 6, 7}, 5));
        System.out.println(searchInRotatedArray.findElement(new int[]{1, 2, 3, 4, 5, 6, 7}, 1));

        System.out.println(searchInRotatedArray.findElement(new int[]{6, 7, 1, 2, 3, 4, 5}, 6));
        System.out.println(searchInRotatedArray.findElement(new int[]{6, 7, 1, 2, 3, 4, 5}, 5));
        System.out.println(searchInRotatedArray.findElement(new int[]{6, 7, 1, 2, 3, 4, 5}, 1));

        System.out.println(searchInRotatedArray.findElement(new int[]{3, 4, 5, 6, 7, 1, 2}, 6));
        System.out.println(searchInRotatedArray.findElement(new int[]{3, 4, 5, 6, 7, 1, 2}, 5));
        System.out.println(searchInRotatedArray.findElement(new int[]{3, 4, 5, 6, 7, 1, 2}, 1));

    }
}
