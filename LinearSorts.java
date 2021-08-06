public class LinearSorts {

   private int numSwaps = 0;
   
  public int getNumSwaps(){ return numSwaps;}
   
  public void resetNumSwaps(){ numSwaps = 0;}
 
   public void selectionSort(int[]numbers) {
      int numbersSize = numbers.length;
      int j = 0;
      int indexSmallest = 0;
      int temp = 0;  
      for (int i = 0; i < numbersSize - 1; i++) { 
      // Find index of smallest remaining element
         indexSmallest = i;
         for (j = i + 1; j < numbersSize; ++j) { 
            if ( numbers[j] < numbers[indexSmallest] ) {
               indexSmallest = j;
            }
         }// end inner for   
         numSwaps++;
         temp = numbers[i];
         numbers[i] = numbers[indexSmallest];
         numbers[indexSmallest] = temp;
      }// end outer for
   }
   
   public void insertionSort(int[] numbers) {
      int numbersSize = numbers.length;
      int j = 0;
      int temp = 0; 
      for (int i = 1; i < numbersSize; i++) {
         j = i;
         while (j > 0 && numbers[j] < numbers[j - 1]) {
            numSwaps++;
            temp = numbers[j];
            numbers[j] = numbers[j - 1];
            numbers[j - 1] = temp;
            j--;
         }// end while
      }// end outer for
   }
  
   public void shellSort(int[] numbers, int[] gapValues) {
      int numbersSize = numbers.length;
      for (int gapValue : gapValues) {
         for (int i = 0; i < gapValue; i++) {
            insertionSortInterleaved(numbers, i, gapValue);
         }
      }
   }
   
   public void insertionSortInterleaved(int[] numbers,int  startIndex, int gap) {
      int numbersSize = numbers.length;
      int j = 0;
      int temp = 0;
      for (int i = startIndex + gap; i < numbersSize; i = i + gap) {
         j = i;
         while (j - gap >= startIndex && numbers[j] < numbers[j - gap]) {
            numSwaps++;
            temp = numbers[j];
            numbers[j] = numbers[j - gap];
            numbers[j - gap] = temp;
            j = j - gap;
         }
      }
   }
   
   public void printReport(int[] arr, int steps){
      printArray(arr);
      System.out.println(" ");
      System.out.println("Swaps: "+steps);
   }
      
   public void printArray(int[] arr){
      for (int i = 0; i < arr.length; i++) 
         System.out.print(arr[i] + " ");
   }

   public static void main(String[] args) {
      LinearSorts sorter = new LinearSorts();
     // int numbers[] = {32,15,6,3,20};
      int numbers[] = {56, 42, 93, 12, 77, 82, 75, 91, 36};
     // int[] gapNums = {3, 1};
     // int numbers[] = {23, 65, 35, 89, 98, 84, 94, 68, 54, 67, 83, 46, 91, 72, 39};
     // int[] gapNums = {5, 3, 1};
   
      System.out.println("UNSORTED: ");
      sorter.printArray(numbers);
      System.out.println(" ");
   
      sorter.selectionSort(numbers);
     // sorter.insertionSort(numbers);
     // sorter.shellSort(numbers, gapNums);
         
      System.out.println("SORTED: ");
      sorter.printReport(numbers, sorter.getNumSwaps());
      System.out.println(" ");  
   }
}