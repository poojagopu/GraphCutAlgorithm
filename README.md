# GraphCutAlgorithm Design and analysis of Algorithms Project

sr21814@umbc.edu, Pooja Gopu
achada1@umbc.edu, Akshith Reddy Chada

We have done Image synthesis for the hut and mountain image. We have resized the image for memory purposes. A larger 
image means more nodes and a larger adjacency matrix. Taking a very large adjacency matrix is causing a No memory exception.

For compiling and running the code run the following commands
$ cd GraphCut/src
$ javac GraphCut.java
$ java GraphCut

Interpreting the output
The code dumps the following data to stdout
* Adjacency matrix
* Cuts between vertexes
* Max flow of the graph

