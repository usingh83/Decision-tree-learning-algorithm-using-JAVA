# Decision-tree-learning-algorithm-using-JAVA

Implement the decision tree learning algorithm. The main step in decision tree learning is choosing the next attribute to split on. Implement the following two heuristics for selecting the next attribute.
1. Information gain heuristic 
2. Variance impurity heuristic described below.
Let K denote the number of examples in the training set. Let K0 denote the number of training examples that have class = 0 and K1 denote the number of training examples that have class = 1.
The variance impurity of the training set S is defined as:
𝑉𝐼(𝑆)= 𝐾0*𝐾1/𝐾*𝐾
Notice that the impurity is 0 when the data is pure. The gain for this impurity is defined as usual. 𝐺𝑎𝑖𝑛(𝑆:𝑋)=𝑉𝐼(𝑆) − ΣPr(𝑥)𝑉𝐼(𝑆𝑥𝑥 𝜖 𝑉𝑎𝑙𝑢𝑒𝑠(𝑋))
where X is an attribute, 𝑆𝑥 denotes the set of training examples that have X = x and Pr(x) is the fraction of the training examples that have X = x (i.e., the number of training examples that have X = x divided by the number of training examples in S).
_ Implement the post pruning algorithm given below as Algorithm 1 (See also Mitchell, Chapter 3).
_ Implement a function to print the decision tree to standard output. We will use the following format.
wesley = 0 :
| honor = 0 :
| | barclay = 0 : 1
| | barclay = 1 : 0
| honor = 1 :
| | tea = 0 : 0
| | tea = 1 : 1
wesley = 1 : 0

According to this tree, if wesley = 0 and honor = 0 and barclay = 0, then the class value of the corresponding instance should be 1. In other words, the value appearing before a colon is an attribute value, and the value appearing after a colon is a class value.
Once we compile your code, we should be able to run it from the command line. Your program should take as input the following six arguments:
.\program <L> <K> <training-set> <validation-set> <test-set> <to-print>
L: integer (used in the post-pruning algorithm)
K: integer (used in the post-pruning algorithm)
to-print:{yes,no}
It should output the accuracies on the test set for decision trees constructed using the two heuristics as well as the accuracies for their post-pruned versions for the given values of L and K. If to-print equals yes, it should print the decision tree in the format described above to the standard output.


Steps to compile and run the java project


Compilation:
javac Node.java

javac Tree.java

javac Assignment1.java


Running the program:

with the tree
java Assignment1 10 15 training_set.csv validation_set.csv test_set.csv yes

without the tree (just the accuracies)
java Assignment1 10 15 training_set.csv validation_set.csv test_set.csv no


