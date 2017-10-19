package HashTableByDoubleHashingMethod;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* Class HashTable */
public class HashTable
{
    private int TABLE_SIZE;
    public static int size;
    private HashEntry[] table;
    private int primeSize;

    public static ArrayList<Integer> values = new ArrayList<Integer>();
    public static ArrayList<String> instructions = new ArrayList<String>();

    public static HashTable hashInstance = new HashTable();

    //Parses input and saves to parallel value and instruction arrayLists.
    @SuppressWarnings("Duplicates")
    public static void parseInput(String fileName)
    {


        // This will reference one line at a time
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            line = bufferedReader.readLine();
            System.out.println(line);

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
            // Or we could just do this:
            // ex.printStackTrace();
        }

        String[] lineArray = line.split(" ");
        String[] charArray = line.split("");

        for(int i=0;i<lineArray.length;i++)
        {

            for(int k=0;k<lineArray[i].length()-1;k++) {


                if(lineArray[i].length() < 3)
                {
                    String append = "";
                    int insert = 0;
                    StringBuilder sb = new StringBuilder();
                    while(Character.isDigit(lineArray[i].charAt(k)))
                    {
                        sb.append(lineArray[i].charAt(k));
                        k++;

                    }
                    append = (sb.toString());
                    insert = Integer.parseInt(append);
                    values.add(insert);
                    instructions.add("");
                    i++;

                }
                if(lineArray[i].equals("del"))
                {
                    values.add(0);
                    instructions.add(lineArray[i]);
                    i++;
                }
                else if (Character.isDigit(lineArray[i].charAt(k)))
                {
                    String append = "";
                    int insert = 0;
                    StringBuilder sb = new StringBuilder();
                    while(Character.isDigit(lineArray[i].charAt(k)))
                    {
                        sb.append(lineArray[i].charAt(k));
                        k++;

                    }
                    append = (sb.toString());
                    insert = Integer.parseInt(append);
                    values.add(insert);

                }
                else if(Character.isAlphabetic(lineArray[i].charAt(k)))
                {
                    String append = "";
                    StringBuilder sb = new StringBuilder();
                    while(k<lineArray[i].length() &&  Character.isAlphabetic(lineArray[i].charAt(k)) )
                    {
                        sb.append(lineArray[i].charAt(k));
                        k++;

                    }
                    append = (sb.toString());
                    instructions.add(append);
                }
            }
        }



    }


    /* Constructor */
    public void HashTable()
    {
        size = 0;
        int ts = values.size();
        TABLE_SIZE = ts;
        table = new HashEntry[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++)
            table[i] = null;
        primeSize = getPrime();
    }
    /* Function to get prime number less than table size for myhash2 function */
    public int getPrime()
    {
        for (int i = TABLE_SIZE - 1; i >= 1; i--)
        {
            int fact = 0;
            for (int j = 2; j <= (int) Math.sqrt(i); j++)
                if (i % j == 0)
                    fact++;
            if (fact == 0)
                return i;
        }
        /* Return a prime number */
        return 3;
    }
    /* Function to get number of key-value pairs */
    public int getSize()
    {
        return size;
    }
    public boolean isEmpty()
    {
        return size == 0;
    }
    /* Function to clear hash table */
    public void makeEmpty()
    {
        size = 0;
        for (int i = 0; i < TABLE_SIZE; i++)
            table[i] = null;
    }
    /* Function to get value of a key */
    public int get(String key)
    {
        int hash1 = myhash1( key );
        int hash2 = myhash2( key );

        while (table[hash1] != null && !table[hash1].key.equals(key))
        {
            hash1 += hash2;
            hash1 %= TABLE_SIZE;
        }
        return table[hash1].value;
    }
    /* Function to insert a key value pair */
    public void insert(String key, int value)
    {
        if (size == TABLE_SIZE)
        {
            System.out.println("Table full");
            return;
        }
        int hash1 = myhash1( key );
        int hash2 = myhash2( key );
        while (table[hash1] != null)
        {
            hash1 += hash2;
            hash1 %= TABLE_SIZE;
        }
        table[hash1] = new HashEntry(key, value);
        size++;
    }
    /* Function to remove a key */
    public void remove(String key)
    {
        int hash1 = myhash1( key );
        int hash2 = myhash2( key );
        while (table[hash1] != null && !table[hash1].key.equals(key))
        {
            hash1 += hash2;
            hash1 %= TABLE_SIZE;
        }
        table[hash1] = null;
        size--;
    }
    /* Function myhash which gives a hash value for a given string */
    private int myhash1(String x )
    {
        int hashVal = x.hashCode( );
        hashVal %= TABLE_SIZE;
        if (hashVal < 0)
            hashVal += TABLE_SIZE;
        return hashVal;
    }
    /* Function myhash function for double hashing */
    private int myhash2(String x )
    {
        int hashVal = x.hashCode( );
        hashVal %= TABLE_SIZE;
        if (hashVal < 0)
            hashVal += TABLE_SIZE;
        return primeSize - hashVal % primeSize;
    }
    /* Function to print hash table */
    public void printHashTable()
    {
        System.out.println("\nHash Table");
        for (int i = 0; i < TABLE_SIZE; i++)
            if (table[i] != null)
                System.out.println(table[i].key +" "+table[i].value);
    }


    public void runInstructions()
    {
        HashTable h = HashTable.hashInstance;
        HashTable();
        for(int i=0;i<values.size();i++)
        {
            if(instructions.get(i).equals("in"))
            {
                h.insert(createKey(values.get(i)),values.get(i));
            }
            else if(instructions.get(i).equals("del"))
            {
                h.remove(createKey(values.get(i)));
            }
            else if(instructions.get(i).equals("sch"))
            {
                h.search(values.get(i));
            }


        }

    }

    public void search(int value)
    {
        int verify = 0;
        for(int i =0; i<=getSize();i++)
        {
            if(table[i].value == value)
            {
                verify++;
            }
            //System.out.println(table[i].value);
        }


        if(verify > 0)
        {
            System.out.println("Found");
        }
        else
        {
            System.out.println("Not Found");
        }
    }
    public String createKey(int value)
    {
        String key = "";
        if(value <= values.size())
        {
            key = Integer.toString(value);
        }
        else{
            key = Integer.toString(value - (values.size() +1));
        }
        return key;
    }
/* Class DoubleHashingHashTableTest */


    public static void main(String[] args)
    {

        HashTable q = new HashTable();
        System.out.println("Enter your input file name: ");

        Scanner scan = new Scanner(System.in);
        String input = scan.next();

        System.out.println();
        System.out.println("Input: ");
        q.parseInput(input);

        System.out.println();
        System.out.println("Output: ");
        q.runInstructions();
        q.printHashTable();
       /* Scanner scan = new Scanner(System.in);
        System.out.println("Hash Table Test\n\n");
        System.out.println("Enter size");
        /* Make object of HashTable */
        /*HashTable ht = new HashTable(scan.nextInt() );

        char ch;
        /*  Perform HashTable operations  */
        /*do
        {
            System.out.println("\nHash Table Operations\n");
            System.out.println("1. insert ");
            System.out.println("2. remove");
            System.out.println("3. get");
            System.out.println("4. check empty");
            System.out.println("5. clear");
            System.out.println("6. size");

            int choice = scan.nextInt();
            switch (choice)
            {
                case 1 :
                    System.out.println("Enter key and value");
                    ht.insert(scan.next(), scan.nextInt() );
                    break;
                case 2 :
                    System.out.println("Enter key");
                    ht.remove( scan.next() );
                    break;
                case 3 :
                    System.out.println("Enter key");
                    System.out.println("Value = "+ ht.get( scan.next() ));
                    break;
                case 4 :
                    System.out.println("Empty Status " +ht.isEmpty());
                    break;
                case 5 :
                    ht.makeEmpty();
                    System.out.println("Hash Table Cleared\n");
                    break;
                case 6 :
                    System.out.println("Size = "+ ht.getSize() );
                    break;
                default :
                    System.out.println("Wrong Entry \n ");
                    break;
            }
            /* Display hash table */
          /*  ht.printHashTable();

            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);
        } while (ch == 'Y'|| ch == 'y');*/
    }
}
