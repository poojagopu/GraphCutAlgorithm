# GraphCutAlgorithm Design and analysis of Algorithms Project

sr21814@umbc.edu, Pooja Gopu
achada1@umbc.edu, Akshith Reddy Chada

We have done Image synthesis for the hut and mountain image. We have resized the image for memory purposes. A larger 
image means more nodes and a larger adjacency matrix. Taking a very large adjacency matrix is causing a No memory exception.
If there is any such exception while running the program please change the -Xmx1024m. JDK version used is 1.8.0_281.

First I have read the pixel value of the src, mask and target and stored in an array list. Iterating over the mask List to
get all the pixel numbers of the source and target nodes. In the next step we have made an adjacency matrix for each pixel.
Each pixel has atleast 2 neighbours and max of 4 neighbours left, right, top and bottom. We are checking for border conditions
and making an adjacency matrix for a densely connected graph. We also added 2 additional node for source and sink. All the 
pixels from the source are connected to the source nodes and the same goes for target nodes. We are now running the 
Edmonds Karp algorithm for min cut. This returns the max flow but also gives us a list of vertices where the cuts are made.
We are using this list to draw a red line along the pixel numbers in the image which shows us the seam. In the final step 
we are using the seam to differentiate the source and target pixels and writing these pixels to an output buffer image and
creating an output image from the buffer image. The program also dumps following intermediate outputs to stdout. source and 
sink nodes, adjacency list for the matrix we constructed, the cuts between vertexes and the max flow.

For compiling and running the code run make the following changes and the following commands 
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