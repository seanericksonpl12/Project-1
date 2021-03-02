import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {
  private int capacity;
  private int size = size();
  private LinkedList<LinkedNode<KeyType, ValueType>>[] keyValuePairs;
  
  
  public HashTableMap(int capacity) {
    this.capacity = capacity;
    keyValuePairs = (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[capacity];
  }
  
  public HashTableMap() {
    this.capacity = 10;
    keyValuePairs = (LinkedList<LinkedNode<KeyType, ValueType>>[]) new LinkedList[10];
  }
  
  @Override
  public boolean put(KeyType key, ValueType value) {
   LinkedNode<KeyType, ValueType> pair = new LinkedNode<KeyType, ValueType>(key, value);
   LinkedList<LinkedNode<KeyType, ValueType>> chain = new LinkedList();
   if ((this.size / this.capacity) >= 0.85) {
     rehash();
   }
   for(int i = 0; i < chain.size(); i++) {
   if (key == null || keyValuePairs[Math.abs(key.hashCode())% capacity].get(i).equals(pair)) {
     return false;
   }
   }
   if (keyValuePairs[Math.abs(key.hashCode()) % capacity] == null) {
     chain.add(pair);
     keyValuePairs[Math.abs(key.hashCode())% capacity] = chain;
     return true;
   }
   else {
     chain = keyValuePairs[Math.abs(key.hashCode())% capacity];
     chain.add(pair);
     keyValuePairs[Math.abs(key.hashCode())% capacity] = chain;
     return true;
     
   }
   
    
  }
  public  LinkedList<LinkedNode<KeyType, ValueType>>[] rehash() {
    this.capacity = this.capacity * 2;
    LinkedList<LinkedNode<KeyType, ValueType>>[] rehashArray;
    //LinkedList<LinkedNode<KeyType, ValueType>> newChain = new LinkedList();
    rehashArray = new LinkedList[this.capacity];
    LinkedNode<KeyType, ValueType> temp;
    KeyType newHash;
    for (int i = 0; i < this.capacity; i++) {
      LinkedList<LinkedNode<KeyType, ValueType>> newChain = new LinkedList();
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
        temp = keyValuePairs[i].remove(j);
        newHash = temp.getKey();
        if (rehashArray[Math.abs(newHash.hashCode()) % this.capacity] == null) {
          newChain.add(temp);
          rehashArray[Math.abs(newHash.hashCode()) % this.capacity] = newChain;
        } else {
          newChain = rehashArray[Math.abs(newHash.hashCode()) % this.capacity];
          newChain.add(temp);
          rehashArray[Math.abs(newHash.hashCode()) % this.capacity] = newChain;
        }
        
      }
      }
    
    
    return rehashArray;
    
    
  }

  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    ValueType searchedValue = null;
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (keyValuePairs[i] != null) {
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
        if (keyValuePairs[i].get(j).getKey() == key) {
          searchedValue = keyValuePairs[i].get(j).getValue();
        }
        else {
          throw new NoSuchElementException("Not in HashTable");
        }
      }
      }
    }
    return searchedValue;
  }

  public void increaseSize() {
	  this.size++;
  }
  @Override
  public int size() {
    int counter = 0;
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (keyValuePairs[i] != null) {
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
        keyValuePairs[i].get(j);
        counter++;
      }
    }
    }
    return counter;
  }

  @Override
  public boolean containsKey(KeyType key) {
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (keyValuePairs[i] != null) {
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
       if (keyValuePairs[i].get(j).getKey() == key) {
         return true;
       }
      }
      }
      }
    return false;
  }

  @Override
  public ValueType remove(KeyType key) {
    ValueType toReturn = null;
    
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (keyValuePairs[i] != null) {
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
       if (keyValuePairs[i].get(j).getKey() == key) {
         toReturn = keyValuePairs[i].get(j).getValue();
         keyValuePairs[i].remove(j);
         break;
       } else {
         return null;
       }
      }
      }
    }
    return toReturn;
  }

  @Override
  public void clear() {
    for (int i = 0; i < keyValuePairs.length; i++) {
      if (keyValuePairs[i] != null) {
      for (int j = 0; j < keyValuePairs[i].size(); j++) {
        keyValuePairs[i].remove(j);
        
      }
      }
    }
    
  }

  
  
  
  
  
}