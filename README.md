# GraphCutAlgorithm Design and analysis of Algorithms Project

sr21814@umbc.edu, Pooja Gopu
achada1@umbc.edu, Akshith Reddy Chada

We have done Image synthesis for the hut and mountain image. We have resized the image for memory purposes. A larger 
image means more nodes and a larger adjacency matrix. Taking a very large adjacency matrix is causing a No memory exception.
If there is any such exception while running the program please change the -Xmx1024m
For compiling and running the code run the following commands
Change file name in line 11
$ cd GraphCut/src
$ javac GraphCut.java
$ java GraphCut

Interpreting the output
The code dumps the following data to stdout
* Adjacency matrix
* Cuts between vertexes
* Max flow of the graph

There is a log.txt file which is the output of my program. This is just to show that the code is working. A screenshot of 
the output is saved as screenshot.png